package app;

public class Card {

    private String suit;
    private String value;
    private String imagePath;

    public Card() {
    }

    public Card(String suit, String value, String imagePath) {
        this.suit = suit;
        this.value = value;
        this.imagePath = imagePath;
    }

    public String getValue() {
        return value;
    }

    public String getSuit() {
        return suit;
    }

    public String getImagePath() {
        return imagePath;
    }
}