package edu.uic.cs342.poker.Service;

import edu.uic.cs342.poker.GameLogic.Dealer;
import edu.uic.cs342.poker.GameLogic.Player;
import edu.uic.cs342.poker.cards.Deck;

public class GameService {

    public enum GameStates{
        /// need to add more states to be proper
        WAITING,
        WAGERING,
        DECIDING,
        SHOWDOWN,
        RESULTS
    }

    private Deck deck;
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
        if(state != GameStates.DECIDING && state != GameStates.RESULTS){
            throw new IllegalStateException("A round has already started");
        }
        player1.clearHand();
        player2.clearHand();

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

        player.folds();
        if(player1.getDecided() && player2.getDecided()){
            //proceed to showdown
            state = GameStates.SHOWDOWN;
            showdown(); //Should only be called once
        }
    }

    public void showdown(){
        if(state != GameStates.SHOWDOWN){
            throw new IllegalStateException("Showdown is happening");
        }
        dealer.dealsDealer();

    }

    public void payouts(){

    }



}
