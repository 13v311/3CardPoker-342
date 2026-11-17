package edu.uic.cs342.poker;

import edu.uic.cs342.poker.GameLogic.Dealer;
import edu.uic.cs342.poker.GameLogic.Player;
import edu.uic.cs342.poker.cards.Card;
import edu.uic.cs342.poker.cards.Deck;
import edu.uic.cs342.poker.cards.Rank;
import edu.uic.cs342.poker.cards.Suit;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

public class DeckAndDealerTests {

    @Test
    void shuffleTest(){
        Deck deck1 = new Deck();
        Deck deck2 = new Deck();

        deck1.shuffle(1);
        deck2.shuffle(1);

        assertEquals(deck1.getCards(), deck2.getCards());
    }

    @Test
    void shuffleFailsTest(){
        Deck deck1 = new Deck();
        Deck deck2 = new Deck();

        deck1.shuffle(1);
        deck2.shuffle(2);

        assertNotEquals(deck1.getCards(), deck2.getCards());
    }

    @Test
    void deckResetTest(){
        Deck deck1 = new Deck();
        Deck deck2 = new Deck();
        deck1.shuffle();
        deck1.reset();

        assertEquals(deck1.getCards(), deck2.getCards());
    }

    @Test
    void dealerQualifiesHighCardTest(){
        Deck deck = new Deck();
        Dealer dealer = new Dealer(deck);

        dealer.getHand().add(new Card(Suit.HEARTS, Rank.QUEEN));
        dealer.getHand().add(new Card(Suit.CLUBS, Rank.THREE));
        dealer.getHand().add(new Card(Suit.DIAMONDS, Rank.FOUR));

        assertTrue(dealer.qualify());
    }

    @Test
    void dealerQualifiesWithPairTest(){
        Deck deck = new Deck();
        Dealer dealer = new Dealer(deck);

        dealer.getHand().add(new Card(Suit.HEARTS, Rank.THREE));
        dealer.getHand().add(new Card(Suit.CLUBS, Rank.THREE));
        dealer.getHand().add(new Card(Suit.DIAMONDS, Rank.FOUR));

        assertTrue(dealer.qualify());
    }

    @Test
    void dealerQualifiesWithStraightTest(){
        Deck deck = new Deck();
        Dealer dealer = new Dealer(deck);

        dealer.getHand().add(new Card(Suit.HEARTS, Rank.SIX));
        dealer.getHand().add(new Card(Suit.CLUBS, Rank.SEVEN));
        dealer.getHand().add(new Card(Suit.DIAMONDS, Rank.FIVE));

        assertTrue(dealer.qualify());
    }

    @Test
    void dealerNoQualifyTest(){
        Deck deck = new Deck();
        Dealer dealer = new Dealer(deck);

        dealer.getHand().add(new Card(Suit.HEARTS, Rank.JACK));
        dealer.getHand().add(new Card(Suit.CLUBS, Rank.SEVEN));
        dealer.getHand().add(new Card(Suit.DIAMONDS, Rank.FIVE));

        assertFalse(dealer.qualify());
    }

    @Test
    void dealsThreeCardsToEachPlayerTest() {
        Deck deck = new Deck();
        Dealer dealer = new Dealer(deck);

        Player player1 = new Player(1000);
        Player player2 = new Player(1000);

        dealer.dealsPlayer(player1, player2);

        assertEquals(3, player1.getHand().size());
        assertEquals(3, player2.getHand().size());
    }

    @Test
    void dealsThreeCardsToDealerTest() {
        Deck deck = new Deck();
        Dealer dealer = new Dealer(deck);

        dealer.dealsDealer();

        assertEquals(3, dealer.getHand().size());
    }

    @Test
    void dealingRemovesCardsFromDeckTest() {
        Deck deck = new Deck();
        Dealer dealer = new Dealer(deck);

        int initialSize = deck.getCards().size();

        Player player1 = new Player(10000);
        Player player2 = new Player(400);

        dealer.dealsPlayer(player1, player2);

        assertEquals(initialSize - 6, deck.getCards().size());
    }

    @Test
    void noDuplicateCardsAreDealt() {
        Deck deck = new Deck();
        Dealer dealer = new Dealer(deck);

        Player player1 = new Player(1000);
        Player player2 = new Player(1000);

        dealer.dealsPlayer(player1, player2);

        List<Card> allCards = new ArrayList<>();
        allCards.addAll(player1.getHand());
        allCards.addAll(player2.getHand());

        assertEquals(6, new HashSet<>(allCards).size());
    }

    @Test
    void dealingWithEmptyDeck(){
        Deck deck = new Deck();
        Dealer dealer = new Dealer(deck);

        deck.getCards().clear();

        assertThrows(NoSuchElementException.class,
                dealer::dealsDealer);
    }

}
