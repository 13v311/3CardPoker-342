package edu.uic.cs342.poker.Service;

import edu.uic.cs342.poker.cards.Card;
import java.util.List;

public interface GameRules {
    int compareHands(List<Card> dealerHand, List<Card> playerHand);

    int evalPPWinnings(List<Card> playerHand, int pairPlusWager);
}