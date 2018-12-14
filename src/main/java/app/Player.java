package app;

import java.util.*;

public class Player {

	public int points;
	public ArrayList<Card> hand = new ArrayList<>();
	public int bet;
	public int balance;
	public int latestCardValue;
	public String playerName;

	public String getPlayerName() {
		return this.playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public Player() {
		this.balance = 100;
		newGame();
	}

	public void newGame() {
		this.points = 0;
		this.bet = 0;
		this.emptyHand();
	}

	public void getCardFrom(Deck deck) {
		Card card = deck.newCard();
		hand.add(card);
		this.latestCardValue = cardToPoints(card);
		this.points += latestCardValue;
	}

	public int cardToPoints(Card card) {
		int result = 0;
		String cardValue = card.getValue();
		try {
			if (cardValue.equals("J") || cardValue.equals("Q") || cardValue.equals("K")) {
				result = 10;
			} else if (cardValue.equals("A")) {
				// TODO Need to call and retrieve decision value from interface and pass it to
				// below. Cannot get 100% code coverage until implemented
				result = aceDecision(1); // TODO Placeholder parameter
			} else {
				int toInt = Integer.parseInt(cardValue);
				assert toInt > 1 && toInt < 11 : "Card not recognised";
				result = toInt;
			}
		} catch (AssertionError a) {
			System.out.println(a.getMessage());
			a.printStackTrace();
		} catch (NumberFormatException n) {
			System.out.println(n.getMessage());
			n.printStackTrace();
		}
		return result;
	}

	// TODO cannot fully test until interface can pass either argument
	public int aceDecision(int decision) {
		int result = 0;
		try {
			assert decision == 1 || decision == 11 : "Input needs to be 1 or 11";
			if (decision == 1) {
				result = 1;
			} else if (decision == 11) {
				result = 11;
			}
		} catch (AssertionError a) {
			System.out.println(a.getMessage());
			a.printStackTrace();
			result = 1;
		} catch (InputMismatchException i) {
			System.out.println(i.getMessage());
			i.printStackTrace();
			result = 1;
		}
		return result;
	}

	public boolean isBust() {
		if (points > 21) {
			return true;
		}
		return false;
	}

	public void handleBet() {
		if (isBust()) {
			balance -= bet;
		} else {
			balance += bet;
		}
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public void emptyHand() {
		this.hand = new ArrayList<>();
	}

	public int getBet() {
		return bet;
	}

	public void setBet(int bet) {
		this.bet = bet;
	}

	public int getBalance() {
		return balance;
	}

	public void setBalance(int balance) {
		this.balance = balance;
	}

	// Test purposes only
	public ArrayList<Card> getHand() {
		return hand;
	}

	// Testing purposes only
	public String toString() {
		return "\nPlayer details:" + "\nBalance:\t" + balance + "\nPoints:\t\t" + points + "\nHand:\t\t"
				+ handToString() + "\nBet:\t\t" + bet + "\nLast card:\t" + latestCardValue;
	}

	// Testing purposes only
	public String handToString() {
		String result = "";
		for (Card card : hand) {
			result += card.getSuit() + card.getValue() + " ";

		}
		return result;
	}

}
