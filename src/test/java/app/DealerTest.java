package app;

import org.junit.Test;
import static org.junit.Assert.*;

public class DealerTest {

    @Test
    public void dealerMakesLowRiskDecision() {
        Dealer dealer = new Dealer();
        Deck deck = new Deck();
        dealer.setPoints(0);
        dealer.makeDecision(deck);
        assertTrue(dealer.points > 0);
    }

    @Test
    public void dealerMakesMediumRiskDecision() {
        Dealer dealer = new Dealer();
        Deck deck = new Deck();
        dealer.setPoints(11);
        dealer.makeDecision(deck);
        assertTrue(dealer.points > 11);
    }

    @Test
    public void dealerMakesHighRiskDecision() {
        Dealer dealer = new Dealer();
        Deck deck = new Deck();
        dealer.setPoints(16);
        dealer.makeDecision(deck);
        assertTrue(dealer.points > 16);
    }
}