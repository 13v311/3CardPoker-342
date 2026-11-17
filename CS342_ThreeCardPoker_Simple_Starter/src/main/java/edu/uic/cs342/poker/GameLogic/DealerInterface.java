package edu.uic.cs342.poker.GameLogic;


import edu.uic.cs342.poker.cards.Card;
import java.util.List;

public interface DealerInterface {

    void reset();

    void shuffle();

    void dealsPlayer(Player player1, Player player2);

    void dealsDealer();

    List<Card> getHand();

    boolean qualify();
}