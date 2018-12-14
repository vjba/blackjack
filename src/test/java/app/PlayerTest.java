package app;

import org.junit.Test;
import static org.junit.Assert.*;

public class PlayerTest {

    @Test
    public void newPlayerHas100Balance() {
        Player player = new Player();
        assertEquals(player.getBalance(), 100);
    }

    @Test
    public void newPlayerHasEmptyHand() {
        Player player = new Player();
        assertEquals(player.getHand().size(), 0);
    }

    @Test
    public void newPlayerHasBetValueOf0() {
        Player player = new Player();
        assertEquals(player.getBet(), 0);
    }

    @Test
    public void getCardRemovesTheCardFromDeck() {
        Deck deck = new Deck();
        Player player = new Player();
        player.getCardFrom(deck);
        assertEquals(deck.getSize(), 51);
    }

    @Test
    public void getCardIncreasesHandSize() {
        Deck deck = new Deck();
        Player player = new Player();
        player.getCardFrom(deck);
        assertEquals(player.getHand().size(), 1);
    }

    // TODO This is failing somewhere. Need to Debug using breakpoints!!
    // @Test
    // public void getCardSetsPointsEqualToCardValue() {
    // Deck deck = new Deck();
    // Player player = new Player();
    // player.getCard(deck);
    // System.out.println(player.getPoints());
    // assertEquals(player.getHand().get(0).getSuit(), player.getPoints());
    // }

    @Test
    public void cardToPointsFindsK() {
        Player player = new Player();
        Card card = new Card("S", "K", "");
        assertEquals(player.cardToPoints(card), 10);
    }

    @Test
    public void cardToPointsFindsQ() {
        Player player = new Player();
        Card card = new Card("C", "Q", "");
        assertEquals(player.cardToPoints(card), 10);
    }

    @Test
    public void cardToPointsFindsJ() {
        Player player = new Player();
        Card card = new Card("H", "J", "");
        assertEquals(player.cardToPoints(card), 10);
    }

    @Test
    public void cardToPointsFindsA() {
        Player player = new Player();
        Card card = new Card("D", "A", "");
        assertEquals(player.cardToPoints(card), 1);
    }

    @Test
    public void cardToPointsAssertionErrorCaught() {
        Player player = new Player();
        Card card = new Card("D", "12", "");
        assertEquals(player.cardToPoints(card), 0);
    }

    @Test
    public void cardToPointsNumberFormatExecptionCaught() {
        Player player = new Player();
        Card card = new Card("D", "X", "");
        assertEquals(player.cardToPoints(card), 0);
    }

    @Test
    public void playerCanBust() {
        Player player = new Player();
        Deck deck = new Deck();
        for (int i = 0; i < 22; i++) {
            player.getCardFrom(deck);
        }
        assertTrue(player.isBust());
    }

    @Test
    public void playerCanNotBust() {
        Player player = new Player();
        Deck deck = new Deck();
        player.getCardFrom(deck);
        assertFalse(player.isBust());
    }

    @Test
    public void betIsHandledWithNotBust() {
        Player player = new Player();
        player.setBet(100);
        player.handleBet();
        assertEquals(player.getBalance(), 200);
    }

    @Test
    public void betIsHandledWithBust() {
        Player player = new Player();
        player.setBet(100);
        player.setPoints(22);
        player.handleBet();
        assertEquals(player.getBalance(), 0);
    }

    @Test
    public void setBalanceSavesValues() {
        Player player = new Player();
        player.setBalance(999);
        assertEquals(player.getBalance(), 999);
    }

    @Test
    public void getPointsReturnsPoints() {
        Player player = new Player();
        player.setPoints(13);
        assertEquals(player.getPoints(), 13);
    }
}