package edu.uic.cs342.poker.Service;

import edu.uic.cs342.poker.GameLogic.ThreeCardLogic;
import edu.uic.cs342.poker.cards.Card;
import java.util.List;

public class DefaultGameRules implements GameRules {

    @Override
    public int compareHands(List<Card> dealerHand, List<Card> playerHand) {
        return ThreeCardLogic.compareHands(dealerHand, playerHand);
    }

    @Override
    public int evalPPWinnings(List<Card> playerHand, int pairPlusWager) {
        return ThreeCardLogic.evalPPWinnings(playerHand, pairPlusWager);
    }
}
