package edu.uic.cs342.poker.cards;


public record Card(Suit suit, Rank rank) {

    public int getValue() {
        return rank.ordinal() + 2;
    }

}
