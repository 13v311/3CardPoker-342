package edu.uic.cs342.poker.cards;


//Maybe can be a record

public record Card(Suit suit, Rank rank) {

    public int getValue() {
        return rank.ordinal() + 2;
    }

    //Equal function ?? maybe
}
