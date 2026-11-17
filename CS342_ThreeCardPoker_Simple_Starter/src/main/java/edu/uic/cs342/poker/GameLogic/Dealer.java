package edu.uic.cs342.poker.GameLogic;

import edu.uic.cs342.poker.cards.Card;
import edu.uic.cs342.poker.cards.Deck;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class Dealer implements DealerInterface {
    private final Deck deck;
    private final List<Card> hand;

    public Dealer(Deck deck){
        this.deck = deck;
        hand = new ArrayList<>();
    }

    public List<Card> getHand() {
        return hand;
    }

    public void reset(){
        hand.clear();
        deck.reset();
    }

    public void shuffle(){
        deck.shuffle();
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
        ThreeCardLogic.HandRank handRank = ThreeCardLogic.evalHand(hand);

        if(handRank == ThreeCardLogic.HandRank.HIGH_CARD){
            int highest = 0;
            for(Card card : hand){
                if(card.getValue() > highest){
                    highest = card.getValue();
                }
            }
            return highest >= 12;
        }

        return true;
    }

    
}
