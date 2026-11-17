package edu.uic.cs342.poker.Service;

import edu.uic.cs342.poker.GameLogic.Dealer;
import edu.uic.cs342.poker.GameLogic.DealerInterface;
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
    private DealerInterface dealer;
    private GameStates state;
    private GameRules rules;
    private int player1DidWin;
    private int player2DidWin;


    public GameService(Player player1, Player player2, DealerInterface dealer, GameRules rules){
        this.player1 = player1;
        this.player2 = player2;
        this.dealer = dealer;
        this.state = GameStates.WAITING;
        this.rules = rules;
    }

    public void startGame(){
        if(state != GameStates.WAITING && state != GameStates.RESULTS){
            throw new IllegalStateException("A round has already started");
        }
        player1.clearHand();
        player2.clearHand();
        dealer.reset();
        dealer.shuffle();

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

        player1DidWin = rules.compareHands(dealer.getHand(), player1.getHand());
        player2DidWin = rules.compareHands(dealer.getHand(), player2.getHand());
        player1.setDidWin(player1DidWin);
        player2.setDidWin(player2DidWin);

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


    public GameStates getState(){ return state; }

    public int playerPayouts(Player player, int didWin){
        int winnings = 0;
        int ppWinnings = rules.evalPPWinnings(player.getHand(), player.getPairPlusWager());

        player.setPairPlusWager(ppWinnings);

        boolean dealerQualifies = dealer.qualify();

        if (dealerQualifies) {
            if (didWin == 1) {
                player.setAnte(player.getAnte());
                player.setPlayWager(player.getAnte());

                winnings += player.getAnte() + player.getPlayWager();
            }
            else if (didWin == -1) {
                player.setAnte(player.getAnte() * -1);
                player.setPlayWager(-player.getAnte() * -1);

                winnings += player.getAnte() + player.getPlayWager();
            }
        }
        else {
            if (didWin == 1) {
                player.setPlayWager(0);
                winnings += player.getAnte();
            }
            else if (didWin == -1){
                player.setAnte(player.getAnte() * -1);
                player.setPlayWager(0);
                winnings += player.getAnte();
            }
        }

        winnings += ppWinnings;

        return winnings;
    }


}
