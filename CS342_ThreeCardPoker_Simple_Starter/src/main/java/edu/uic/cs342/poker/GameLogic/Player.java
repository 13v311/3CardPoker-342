package edu.uic.cs342.poker.GameLogic;


import edu.uic.cs342.poker.cards.Card;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private int amount;
    private int ante;
    private int pairPlusWager;
    private int winnings;
    private final List<Card> hand;
    private boolean folded;

    public Player(int amount){
        this.amount = amount;
        ante = 0;
        pairPlusWager = 0;
        winnings = 0;
        hand = new ArrayList<>();
        folded = false;
    }

    public int getAmount(){ return amount; }
    public int getAnte(){ return ante;}
    public int getPairPlusWager(){ return pairPlusWager;}
    public List<Card> getHand(){ return hand;}

    //add card function
    public void addCard(Card card){
        hand.add(card);
    }


    public void bets(int ante, int pairPlusWager){
        if(ante > amount){
            throw new IllegalArgumentException("Ante bet is more than amount available.");
        }
        else{
            this.ante = ante;
        }

        if(pairPlusWager > amount){
            throw new IllegalArgumentException("Pair Plus bet is more than amount available.");
        }
        else{
            this.pairPlusWager = pairPlusWager;
        }

    }

    public void folds(){
        folded = true;
    }

    public void clearHand(){
        hand.clear();
        ante = 0;
        pairPlusWager = 0;
    }

    public void outcome(int won){
        amount+= won;
        winnings += won;
    }
}
