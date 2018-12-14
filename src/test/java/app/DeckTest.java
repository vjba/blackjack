package app;

import org.junit.Test;
import static org.junit.Assert.*;

public class DeckTest {

    @Test
    public void builtDeckHas52Cards() {
        Deck deck = new Deck();
        assertEquals(deck.getSize(), 52);
    }

    @Test
    public void takingNewCardChangesDeskSize() {
        Deck deck = new Deck();
        deck.newCard();
        assertNotEquals(deck.getSize(), new Deck());
    }

    @Test
    public void taking5CardsDeckEquals51() {
        Deck deck = new Deck();
        deck.newCard();
        assertEquals(deck.getSize(), 51);
    }
}