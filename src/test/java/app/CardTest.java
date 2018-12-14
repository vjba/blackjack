package app;

import org.junit.Test;
import static org.junit.Assert.*;

public class CardTest {

    @Test
    public void constructorRetainesValues() {
        Card card = new Card("S", "A", "");
        String result = card.getSuit() + card.getValue();
        assertEquals(result, "SA");
    }

    @Test
    public void defaultConstructorHasNullAttributes() {
        Card card = new Card();
        assertNull(card.getSuit());
        assertNull(card.getValue());
        assertNull(card.getImagePath());
    }
}