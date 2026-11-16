package edu.uic.cs342.poker.Service;

import edu.uic.cs342.poker.GameLogic.Dealer;
import edu.uic.cs342.poker.GameLogic.Player;
import edu.uic.cs342.poker.GameLogic.ThreeCardLogic;
import edu.uic.cs342.poker.cards.Deck;

public class GameService {

    public enum GameStates{
        /// need to add more states to be proper maybe
        WAITING,
        WAGERING,
        DECIDING,
        SHOWDOWN,
        RESULTS
    }

    private Player player1;
    private Player player2;
    private Dealer dealer;
    private GameStates state;


    public GameService(Player player1, Player player2, Dealer dealer){
        this.player1 = player1;
        this.player2 = player2;
        this.dealer = dealer;
        this.state = GameStates.WAITING;
    }

    public void startGame(){
        if(state != GameStates.WAITING && state != GameStates.RESULTS){
            throw new IllegalStateException("A round has already started");
        }
        player1.clearHand();
        player2.clearHand();
        dealer.reset();

        state = GameStates.WAGERING;

    }

    public void wagers(int ante1, int ppWager1, int ante2, int ppWager2){
        if(state != GameStates.WAGERING){
            throw new IllegalStateException("Not accepting wagers");
        }
        player1.bets(ante1, ppWager1);
        player2.bets(ante2, ppWager2);

        state = GameStates.DECIDING;


        deals();
    }

    private void deals(){
        dealer.dealsPlayer(player1, player2);
    }

    public void play(Player player){
        if(state != GameStates.DECIDING){
            throw new IllegalStateException("In deciding state");
        }
        if(player.getDecided()){
            throw new IllegalArgumentException("Player has already played");
        }
        player.plays();

        if(player1.getDecided() && player2.getDecided()){
            //proceed to showdown
            state = GameStates.SHOWDOWN;
            showdown(); //Should only be called once
        }

    }

    public void fold(Player player){
        if(state != GameStates.DECIDING){
            throw new IllegalStateException("In deciding state");
        }
        if(player.getDecided()){
            throw new IllegalArgumentException("Player has already folded");
        }
        player.folds();

        if(player1.getDecided() && player2.getDecided()){
            //proceed to showdown
            state = GameStates.SHOWDOWN;
            showdown(); //Should only be called once
        }
    }

    private void showdown(){
        if(state != GameStates.SHOWDOWN){
            throw new IllegalStateException("Showdown is happening");
        }
        dealer.dealsDealer();

        int player1DidWin = ThreeCardLogic.compareHands(dealer.getHand(), player1.getHand());
        int player2DidWin = ThreeCardLogic.compareHands(dealer.getHand(), player2.getHand());

        int player1Won = playerPayouts(player1, player1DidWin);
        int player2Won = playerPayouts(player2, player2DidWin);

        state = GameStates.RESULTS;
        payouts(player1Won, player2Won);
    }


    private void payouts(int player1Won, int player2Won){
        if(state != GameStates.RESULTS){
            throw new IllegalStateException("Round finished and showing results");
        }

        player1.outcome(player1Won);
        player2.outcome(player2Won);
    }

    public void continueNextRound(){
        startGame();
    }

    public void menu(){
        state = GameStates.WAITING;
    }


    private int playerPayouts(Player player, int didWin){
        int winnings = 0;
        int ppWinnings = ThreeCardLogic.evalPPWinnings(player.getHand(), player.getPairPlusWager());

        if(dealer.qualify()){
            if(didWin == 1){
                winnings = player.getAnte() + player.getPlayWager()+ ppWinnings;
            }
            else if(didWin == -1){
                //Multiply by negative 1 for losing and easier to get when shown results
                //But maybe there's a better way than actually changing this stuff
                player.setAnte(player.getAnte() * -1);
                player.setPlayWager(player.getPairPlusWager() * -1);

                winnings = player.getAnte() + player.getPlayWager()+ ppWinnings;
            }
            else{
                winnings = player.getAnte() + ppWinnings;
            }
        }
        else{
            if(didWin == 1){
                winnings = player.getAnte() + ppWinnings;
            }
            else if(didWin == -1){
                player.setAnte(player.getAnte() * -1);

                winnings = player.getAnte() + ppWinnings;
            }
            else{
                winnings = player.getAnte() + ppWinnings;
            }
        }

        return winnings;

    }


}
