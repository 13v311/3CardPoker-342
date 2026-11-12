package cards;

import java.util.*;

public class Deck {
    private final List<Card> cards = new ArrayList<>();
    //private ArrayList<Card> pulledCards;

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

    public void shuffleSeed(){
        Collections.shuffle(cards, new Random(1));
    }

    public Card deal(){
        if(cards.isEmpty()){
            throw new NoSuchElementException("Deck is empty, cannot deal");
        }
        else{
            return cards.remove(cards.size() - 1);
        }
    }

}
