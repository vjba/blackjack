package app;

public class Dealer extends Player {

    private int lowRisk = 10, mediumRisk = 15, highRisk = 19;
    // Difficulty Explanation:
    // Higher is easier. It makes the dealer more stupid / riskier.
    private double difficultyLevel = 19;

    public Dealer() {
        super();
    }

    public void makeDecision(Deck deck) {
        if (points < lowRisk) {
            getCardFrom(deck);
        } else if (points >= lowRisk && points < mediumRisk) {
            if (randomDecision(mediumRisk)) {
                getCardFrom(deck);
            }
        } else if (points >= mediumRisk && points < highRisk) {
            if (randomDecision(highRisk)) {
                getCardFrom(deck);
            }
        }
    }

    private boolean randomDecision(int risk) {
        boolean standOrHit = false;
        while (true) {
            double rand = (Math.random() * (risk / 2));
            double median = risk / difficultyLevel;
            if (rand < median) {
                standOrHit = false;
                break;
            } else if (rand >= median) {
                standOrHit = true;
                break;
            }
        }
        return standOrHit;
    }
}
