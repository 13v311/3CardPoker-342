package cards;


//Maybe can be a record

public class Card {
    private final Suit suit;
    private final Rank rank;

    public Card(Suit suit, Rank rank){
        this.suit = suit;
        this.rank = rank;
    }

    public Suit getSuit(){ return suit; }

    public Rank getRank(){ return rank; }

    public int getValue(){ return rank.ordinal() + 2; }

    //Equal function ?? maybe
}
