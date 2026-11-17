package edu.uic.cs342.poker;

import edu.uic.cs342.poker.GameLogic.Dealer;
import edu.uic.cs342.poker.GameLogic.Player;
import edu.uic.cs342.poker.Service.DefaultGameRules;
import edu.uic.cs342.poker.Service.GameRules;
import edu.uic.cs342.poker.Service.GameService;
import edu.uic.cs342.poker.cards.Card;
import edu.uic.cs342.poker.cards.Deck;
import edu.uic.cs342.poker.cards.Rank;
import edu.uic.cs342.poker.cards.Suit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;




class TestGameRules implements GameRules {

    private final int ppWinnings;

    public TestGameRules(int ppWinnings) {
        this.ppWinnings = ppWinnings;
    }

    @Override
    public int compareHands(
            List<Card> dealerHand,
            List<Card> playerHand) {
        return 0;
    }

    @Override
    public int evalPPWinnings(
            List<Card> playerHand,
            int pairPlusWager) {
        return ppWinnings;
    }
}


class TestDealer extends Dealer {

    private final boolean qualifies;

    public TestDealer(Deck deck, boolean qualifies) {
        super(deck);
        this.qualifies = qualifies;
    }

    @Override
    public boolean qualify() {
        return qualifies;
    }
}

public class AdditionalTests {

    private Player player1;
    private Player player2;
    private Dealer dealer;
    private GameService game;

    @BeforeEach
    void setup(){
        player1 = new Player(1000);
        player2 = new Player(1000);

        dealer = new Dealer(new Deck());

        GameRules rules = new DefaultGameRules();

        game = new GameService(player1, player2, dealer, rules);
    }

    @Test
    void wagersTest() {
        game.startGame();

        game.wagers(10, 5, 10, 5);

        assertEquals(
                GameService.GameStates.DECIDING,
                game.getState()
        );

        assertEquals(3, player1.getHand().size());
        assertEquals(3, player2.getHand().size());
    }

    @Test
    void initialStateTest() {
        assertEquals(
                GameService.GameStates.WAITING,
                game.getState()
        );
    }

    @Test
    void startGameTest() {
        game.startGame();

        assertEquals(
                GameService.GameStates.WAGERING,
                game.getState()
        );
    }

    @Test
    void startGameInvalidStateTest() {
        game.startGame();

        assertThrows(
                IllegalStateException.class,
                () -> game.startGame()
        );
    }

    @Test
    void playerWinsDealerQualifiesTest() {

        Player player1 = new Player(1000);
        Player player2 = new Player(1000);

        player1.setAnte(10);
        player1.setPlayWager(10);
        player1.setPairPlusWager(5);

        Dealer dealer = new TestDealer(new Deck(), true);

        GameRules rules = new TestGameRules(0);

        GameService game = new GameService(
                player1, player2, dealer, rules
        );

        int winnings = game.playerPayouts(player1, 1);

        assertEquals(20, winnings);
    }





    @Test
    void menuTest() {
        game.menu();

        assertEquals(
                GameService.GameStates.WAITING,
                game.getState()
        );
    }


}
