package edu.uic.cs342.poker.cards;

import java.util.*;

public class Deck {
    private final List<Card> cards = new ArrayList<>();

    public Deck(){
        reset();
    }

    public final void reset(){
        cards.clear();
        for(Suit suit : Suit.values()){
            for(Rank rank : Rank.values()){
                cards.add(new Card(suit, rank));
            }
        }
    }

    public void shuffle(){
        Collections.shuffle(cards);
    }

    public void shuffle(long seed){
        Collections.shuffle(cards, new Random(seed));
    }


    public List<Card> getCards() {
        return cards;
    }
}
