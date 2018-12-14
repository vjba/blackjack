package app;

import java.util.ArrayList;

public class Deck {

    private ArrayList<Card> deck;

    public Deck() {
        this.deck = buildDeck();
    }

    private ArrayList<Card> buildDeck() {

        ArrayList<Card> deck = new ArrayList<>();

        String[] suits = { "H", "S", "C", "D" };
        String[] values = { "A", "K", "Q", "J", "10", "9", "8", "7", "6", "5", "4", "3", "2" };
        String imagePath = "src/main/java/app/img/";

        for (String suit : suits) {
            for (String value : values) {
                deck.add(new Card(suit, value, imagePath + suit + value + ".png"));
            }
        }
        return deck;
    }

    public Card newCard() {
        Card card = new Card("", "", "");
        while (true) {
            try {
                int rand = (int) (Math.random() * deck.size() + 1);
                assert rand >= 0 && rand <= deck.size() : "rand is OBOE";
                if (rand >= 0 && rand < deck.size()) {
                    card = deck.get(rand);
                    deck.remove(rand);
                    break;
                }
            } catch (IndexOutOfBoundsException i) {
                System.out.println(i.getMessage());
                i.printStackTrace();
            }
        }
        return card;
    }

    public int getSize() {
        return this.deck.size();
    }

    // Testing purposes only
    public ArrayList<Card> getDeck() {
        return deck;
    }

    // Testing purposes only
    public Card getCard(int index) {
        return deck.get(index);
    }
}