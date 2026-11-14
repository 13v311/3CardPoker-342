package edu.uic.cs342.poker;

import edu.uic.cs342.poker.GameLogic.ThreeCardLogic;
import edu.uic.cs342.poker.cards.Card;
import edu.uic.cs342.poker.cards.Rank;
import edu.uic.cs342.poker.cards.Suit;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ThreeCardLogicTests {

    //Evaluating Hands Test

    @Test
    void handHighCardTest(){
        List<Card> hand = new ArrayList<>();
        hand.add(new Card(Suit.CLUBS, Rank.ACE));
        hand.add(new Card(Suit.SPADES, Rank.TWO));
        hand.add(new Card(Suit.HEARTS, Rank.EIGHT));


        assertEquals(ThreeCardLogic.HandRank.HIGH_CARD, ThreeCardLogic.evalHand(hand));
    }

    @Test
    void handPairTest(){
        List<Card> hand = new ArrayList<>();
        hand.add(new Card(Suit.CLUBS, Rank.ACE));
        hand.add(new Card(Suit.SPADES, Rank.ACE));
        hand.add(new Card(Suit.HEARTS, Rank.EIGHT));

        assertEquals(ThreeCardLogic.HandRank.PAIR, ThreeCardLogic.evalHand(hand));
    }

    @Test
    void handFlushTest(){
        List<Card> hand = new ArrayList<>();
        hand.add(new Card(Suit.CLUBS, Rank.ACE));
        hand.add(new Card(Suit.CLUBS, Rank.THREE));
        hand.add(new Card(Suit.CLUBS, Rank.EIGHT));


        assertEquals(ThreeCardLogic.HandRank.FLUSH, ThreeCardLogic.evalHand(hand));
    }

    @Test
    void handStraightTest(){
        List<Card> hand = new ArrayList<>();
        hand.add(new Card(Suit.CLUBS, Rank.QUEEN));
        hand.add(new Card(Suit.SPADES, Rank.KING));
        hand.add(new Card(Suit.HEARTS, Rank.ACE));

        assertEquals(ThreeCardLogic.HandRank.STRAIGHT, ThreeCardLogic.evalHand(hand));
    }

    @Test
    void handThreeOfKindTest(){
        List<Card> hand = new ArrayList<>();
        hand.add(new Card(Suit.CLUBS, Rank.ACE));
        hand.add(new Card(Suit.SPADES, Rank.ACE));
        hand.add(new Card(Suit.HEARTS, Rank.ACE));


        assertEquals(ThreeCardLogic.HandRank.THREE_OF_KIND, ThreeCardLogic.evalHand(hand));
    }

    @Test
    void handStraightFlushTest(){
        List<Card> hand = new ArrayList<>();
        hand.add(new Card(Suit.HEARTS, Rank.QUEEN));
        hand.add(new Card(Suit.HEARTS, Rank.KING));
        hand.add(new Card(Suit.HEARTS, Rank.ACE));

        assertEquals(ThreeCardLogic.HandRank.STRAIGHT_FLUSH, ThreeCardLogic.evalHand(hand));
    }

    //Comparing Hands Test section

    @Test
    void dealerWinTest(){
        List<Card> dealerHand = new ArrayList<>();
        dealerHand.add(new Card(Suit.HEARTS, Rank.QUEEN));
        dealerHand.add(new Card(Suit.HEARTS, Rank.KING));
        dealerHand.add(new Card(Suit.HEARTS, Rank.ACE));

        List<Card> playerHand = new ArrayList<>();
        playerHand.add(new Card(Suit.CLUBS, Rank.ACE));
        playerHand.add(new Card(Suit.SPADES, Rank.TWO));
        playerHand.add(new Card(Suit.HEARTS, Rank.EIGHT));

        assertEquals(-1, ThreeCardLogic.compareHands(dealerHand, playerHand));
    }

    @Test
    void playerWinsTest(){
        List<Card> playerHand = new ArrayList<>();
        playerHand.add(new Card(Suit.HEARTS, Rank.QUEEN));
        playerHand.add(new Card(Suit.HEARTS, Rank.KING));
        playerHand.add(new Card(Suit.HEARTS, Rank.ACE));

        List<Card> dealerHand = new ArrayList<>();
        dealerHand.add(new Card(Suit.CLUBS, Rank.ACE));
        dealerHand.add(new Card(Suit.SPADES, Rank.TWO));
        dealerHand.add(new Card(Suit.HEARTS, Rank.EIGHT));

        assertEquals(1, ThreeCardLogic.compareHands(dealerHand, playerHand));
    }

    //A complete draw test
    @Test
    void playerDrawTest(){
        List<Card> dealerDrawHand = new ArrayList<>();
        dealerDrawHand.add(new Card(Suit.CLUBS, Rank.ACE));
        dealerDrawHand.add(new Card(Suit.SPADES, Rank.ACE));
        dealerDrawHand.add(new Card(Suit.HEARTS, Rank.ACE));

        List<Card> playerDrawHand = new ArrayList<>();
        playerDrawHand.add(new Card(Suit.CLUBS, Rank.ACE));
        playerDrawHand.add(new Card(Suit.SPADES, Rank.ACE));
        playerDrawHand.add(new Card(Suit.HEARTS, Rank.ACE));

        assertEquals(0, ThreeCardLogic.compareHands(dealerDrawHand, playerDrawHand));
    }

    //With Three of a Kind for these two tests
    @Test
    void drawButWinTest(){
        List<Card> dealerDrawHand = new ArrayList<>();
        dealerDrawHand.add(new Card(Suit.CLUBS, Rank.TEN));
        dealerDrawHand.add(new Card(Suit.SPADES, Rank.TEN));
        dealerDrawHand.add(new Card(Suit.HEARTS, Rank.TEN));

        List<Card> playerDrawHand = new ArrayList<>();
        playerDrawHand.add(new Card(Suit.CLUBS, Rank.ACE));
        playerDrawHand.add(new Card(Suit.SPADES, Rank.ACE));
        playerDrawHand.add(new Card(Suit.HEARTS, Rank.ACE));

        assertEquals(1, ThreeCardLogic.compareHands(dealerDrawHand, playerDrawHand));
    }

    @Test
    void drawButLoseTest(){
        List<Card> dealerDrawHand = new ArrayList<>();
        dealerDrawHand.add(new Card(Suit.CLUBS, Rank.ACE));
        dealerDrawHand.add(new Card(Suit.SPADES, Rank.ACE));
        dealerDrawHand.add(new Card(Suit.HEARTS, Rank.ACE));

        List<Card> playerDrawHand = new ArrayList<>();
        playerDrawHand.add(new Card(Suit.CLUBS, Rank.NINE));
        playerDrawHand.add(new Card(Suit.SPADES, Rank.NINE));
        playerDrawHand.add(new Card(Suit.HEARTS, Rank.NINE));

        assertEquals(-1, ThreeCardLogic.compareHands(dealerDrawHand, playerDrawHand));
    }


    @Test
    void highCardDrawAndWinsTest(){
        List<Card> dealerDrawHand = new ArrayList<>();
        dealerDrawHand.add(new Card(Suit.CLUBS, Rank.QUEEN));
        dealerDrawHand.add(new Card(Suit.SPADES, Rank.SEVEN));
        dealerDrawHand.add(new Card(Suit.HEARTS, Rank.SIX));

        List<Card> playerDrawHand = new ArrayList<>();
        playerDrawHand.add(new Card(Suit.CLUBS, Rank.KING));
        playerDrawHand.add(new Card(Suit.SPADES, Rank.NINE));
        playerDrawHand.add(new Card(Suit.HEARTS, Rank.THREE));

        assertEquals(1, ThreeCardLogic.compareHands(dealerDrawHand, playerDrawHand));
    }

    @Test
    void straightDrawTiebreakerTest(){
        List<Card> dealerDrawHand = new ArrayList<>();
        dealerDrawHand.add(new Card(Suit.CLUBS, Rank.ACE));
        dealerDrawHand.add(new Card(Suit.SPADES, Rank.KING));
        dealerDrawHand.add(new Card(Suit.HEARTS, Rank.QUEEN));

        List<Card> playerDrawHand = new ArrayList<>();
        playerDrawHand.add(new Card(Suit.CLUBS, Rank.SEVEN));
        playerDrawHand.add(new Card(Suit.SPADES, Rank.SIX));
        playerDrawHand.add(new Card(Suit.HEARTS, Rank.FIVE));

        assertEquals(-1, ThreeCardLogic.compareHands(dealerDrawHand, playerDrawHand));
    }

    @Test
    void highCardDrawAndWinsFromSecondTest(){
        List<Card> dealerDrawHand = new ArrayList<>();
        dealerDrawHand.add(new Card(Suit.CLUBS, Rank.QUEEN));
        dealerDrawHand.add(new Card(Suit.SPADES, Rank.SEVEN));
        dealerDrawHand.add(new Card(Suit.HEARTS, Rank.SIX));

        List<Card> playerDrawHand = new ArrayList<>();
        playerDrawHand.add(new Card(Suit.CLUBS, Rank.QUEEN));
        playerDrawHand.add(new Card(Suit.SPADES, Rank.NINE));
        playerDrawHand.add(new Card(Suit.HEARTS, Rank.THREE));

        assertEquals(1, ThreeCardLogic.compareHands(dealerDrawHand, playerDrawHand));
    }

    @Test
    void highCardDrawAndWinsFromThirdTest(){
        List<Card> dealerDrawHand = new ArrayList<>();
        dealerDrawHand.add(new Card(Suit.CLUBS, Rank.QUEEN));
        dealerDrawHand.add(new Card(Suit.SPADES, Rank.SEVEN));
        dealerDrawHand.add(new Card(Suit.HEARTS, Rank.TWO));

        List<Card> playerDrawHand = new ArrayList<>();
        playerDrawHand.add(new Card(Suit.CLUBS, Rank.QUEEN));
        playerDrawHand.add(new Card(Suit.SPADES, Rank.SEVEN));
        playerDrawHand.add(new Card(Suit.HEARTS, Rank.NINE));

        assertEquals(1, ThreeCardLogic.compareHands(dealerDrawHand, playerDrawHand));
    }




    //Payouts Test

    @Test
    void straightFlushPayoutTest(){
        List<Card> hand = new ArrayList<>();
        hand.add(new Card(Suit.HEARTS, Rank.QUEEN));
        hand.add(new Card(Suit.HEARTS, Rank.KING));
        hand.add(new Card(Suit.HEARTS, Rank.ACE));

        assertEquals(1000, ThreeCardLogic.evalPPWinnings(hand, 25));
    }

    @Test
    void threeCardPayoutTest(){
        List<Card> hand = new ArrayList<>();
        hand.add(new Card(Suit.CLUBS, Rank.ACE));
        hand.add(new Card(Suit.SPADES, Rank.ACE));
        hand.add(new Card(Suit.HEARTS, Rank.ACE));

        assertEquals(750, ThreeCardLogic.evalPPWinnings(hand, 25));
    }

    @Test
    void straightPayoutTest(){
        List<Card> hand = new ArrayList<>();
        hand.add(new Card(Suit.CLUBS, Rank.QUEEN));
        hand.add(new Card(Suit.SPADES, Rank.KING));
        hand.add(new Card(Suit.HEARTS, Rank.ACE));

        assertEquals(150, ThreeCardLogic.evalPPWinnings(hand, 25));
    }

    @Test
    void flushPayoutTest(){
        List<Card> hand = new ArrayList<>();
        hand.add(new Card(Suit.CLUBS, Rank.ACE));
        hand.add(new Card(Suit.CLUBS, Rank.THREE));
        hand.add(new Card(Suit.CLUBS, Rank.EIGHT));

        assertEquals(75, ThreeCardLogic.evalPPWinnings(hand, 25));
    }

    @Test
    void pairPayoutTest(){
        List<Card> hand = new ArrayList<>();
        hand.add(new Card(Suit.CLUBS, Rank.ACE));
        hand.add(new Card(Suit.SPADES, Rank.ACE));
        hand.add(new Card(Suit.HEARTS, Rank.EIGHT));

        assertEquals(25, ThreeCardLogic.evalPPWinnings(hand, 25));
    }

    //gotta add more


}
