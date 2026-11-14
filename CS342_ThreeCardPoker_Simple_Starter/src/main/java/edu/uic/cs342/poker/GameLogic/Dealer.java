package edu.uic.cs342.poker.GameLogic;

import edu.uic.cs342.poker.cards.Card;
import edu.uic.cs342.poker.cards.Deck;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class Dealer {
    private final Deck deck;
    private final List<Card> hand;
    private boolean qualifies = false;

    public Dealer(Deck deck){
        this.deck = deck;
        hand = new ArrayList<>();
    }

    private Card deal(){
        if(deck.getCards().isEmpty()){
            throw new NoSuchElementException("Deck is empty, cannot deal");
        }

        return deck.getCards().removeLast();

    }

    public void dealsPlayer(Player player1, Player player2){
        for(int i = 0; i < 3; i++){
            player1.addCard(deal());
            player2.addCard(deal());
        }
    }

    public void dealsDealer(){
        for(int i = 0; i < 3; i++){
            hand.add(deal());
        }

    }

    public boolean qualify(){
        qualifies = true;
        return qualifies;
    }

    
}
