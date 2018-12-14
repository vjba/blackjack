package app.ui;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Optional;

import app.Dealer;
import app.Deck;
import app.Player;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Controller extends Application {

	private Player player = new Player();
	private Dealer dealer = new Dealer();
	private Deck deck = new Deck();
	private GaussianBlur blur = new GaussianBlur(20);
	private boolean gameStarted = false;

	@FXML
	private Button startGameButton, placeBetButton, hitButton;

	@FXML
	private Pane welcomePane, rulesPane, gamePane;

	@FXML
	private Text playerName, balance, pot, playerHandScore, dealerHandScore, validationOutputText;

	@FXML
	private TextField playerNameInput, placeBetValue;

	// ######### Player ##########

	@FXML
	private ImageView playerImage1, playerImage2, playerImage3, playerImage4, playerImage5;

	@FXML
	private Text playerCard1Value, playerCard2Value, playerCard3Value, playerCard4Value, playerCard5Value;

	// ######### Dealer ##########

	@FXML
	private ImageView dealerImage1, dealerImage2, dealerImage3, dealerImage4, dealerImage5;

	@FXML
	private Text dealerCard1Value, dealerCard2Value, dealerCard3Value, dealerCard4Value, dealerCard5Value;

	@FXML
	void welcomePaneViewRules(ActionEvent event) {
		gamePane.setEffect(blur);
		rulesPane.setVisible(true);
	}

	@FXML
	void welcomePaneExit(ActionEvent event) {
		exitGame();
	}

	@FXML
	void startGame(ActionEvent event) {
		try {
			if (playerNameInput.getText().isEmpty()) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("ALERT");
				alert.setHeaderText(null);
				alert.setContentText("Please enter a name");
				alert.showAndWait();
			} else {

				// initiate game
				// pull 2 cards from deck for each player
				for (int i = 0; i < 2; i++) {
					player.getCardFrom(deck);
					dealer.getCardFrom(deck);
				}

				startGameButton.setDisable(true);
				startGameButton.setDefaultButton(false);
				placeBetButton.setDefaultButton(true);
				gameStarted = true;
				welcomePane.setVisible(false);
				gamePane.setEffect(null);

				// set player attributes
				player.setPlayerName(playerNameInput.getText());
				playerName.setText(player.getPlayerName());
				balance.setText(Integer.toString(player.getBalance()));
				pot.setText(Integer.toString(player.getBet()));

				playerImage1.setImage(new Image(new FileInputStream(player.getHand().get(0).getImagePath())));
				playerCard1Value.setText(Integer.toString(player.cardToPoints(player.getHand().get(0))));
				playerImage2.setImage(new Image(new FileInputStream(player.getHand().get(1).getImagePath())));
				playerCard2Value.setText(Integer.toString(player.cardToPoints(player.getHand().get(1))));

				dealerImage1.setImage(new Image(new FileInputStream(dealer.getHand().get(0).getImagePath())));
				dealerCard1Value.setText(Integer.toString(dealer.cardToPoints(dealer.getHand().get(0))));
				dealerImage2.setImage(new Image(new FileInputStream(dealer.getHand().get(1).getImagePath())));
				dealerCard2Value.setText(Integer.toString(dealer.cardToPoints(dealer.getHand().get(1))));
				playerHandScore.setText(Integer.toString(player.getPoints()));
				dealerHandScore.setText(Integer.toString(dealer.getPoints()));

				gameConditionalCheck();
			}
		} catch (FileNotFoundException noFile) {
			noFile.printStackTrace();

			// set gui

			if (playerNameInput.getText().isEmpty()) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("ALERT");
				alert.setHeaderText(null);
				alert.setContentText("Please enter a name");
				alert.showAndWait();
			} else {

				startGameButton.setDisable(true);
				startGameButton.setDefaultButton(false);
				placeBetButton.setDefaultButton(true);
				gameStarted = true;
				welcomePane.setVisible(false);
				gamePane.setEffect(null);

				// set player attributes
				player.setPlayerName(playerNameInput.getText());
				playerName.setText(player.getPlayerName());
				balance.setText(Integer.toString(player.getBalance()));
				pot.setText(Integer.toString(player.getBet()));

				// initiate game
				// pull 2 cards from deck for each player
				for (int i = 0; i < 2; i++) {
					player.getCardFrom(deck);
					dealer.getCardFrom(deck);

					if (playerNameInput.getText().isEmpty()) {
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("ALERT");
						alert.setHeaderText(null);
						alert.setContentText("Please enter a name");
						alert.showAndWait();
					} else {
						startGameButton.setDisable(true);
						startGameButton.setDefaultButton(false);
						placeBetButton.setDefaultButton(true);
						gameStarted = true;
						welcomePane.setVisible(false);
						gamePane.setEffect(null);

						// set player attributes
						player.setPlayerName(playerNameInput.getText());
						playerName.setText(player.getPlayerName());
						balance.setText(Integer.toString(player.getBalance()));
						pot.setText(Integer.toString(player.getBet()));

					}
				}
			}
		}
	}

	@FXML
	void exit(ActionEvent event) {
		exitGame();
	}

	@FXML
	void rulesPaneBack(ActionEvent event) {
		if (gameStarted) {
			gamePane.setEffect(null);
			welcomePane.setVisible(false);
		} else {
			welcomePane.setVisible(true);
		}
		rulesPane.setVisible(false);
	}

	// TODO Consider building lists and iterating over to set images
	@FXML
	void hit(ActionEvent event) {

		player.getCardFrom(deck);
		dealer.getCardFrom(deck);
		playerHandScore.setText(Integer.toString(player.getPoints()));
		dealerHandScore.setText(Integer.toString(dealer.getPoints()));

		try {
			dealer.getCardFrom(deck);
			if (player.getHand().size() == 3) {
				// Player
				playerImage3.setVisible(true);
				playerImage3.setImage(new Image(new FileInputStream(player.getHand().get(2).getImagePath())));
				playerCard3Value.setText(Integer.toString(player.cardToPoints(player.getHand().get(2))));

				// Dealer
				dealerImage3.setVisible(true);
				dealerImage3.setImage(new Image(new FileInputStream(dealer.getHand().get(2).getImagePath())));
				dealerCard3Value.setText(Integer.toString(dealer.cardToPoints(dealer.getHand().get(2))));

			} else if (player.getHand().size() == 4) {
				// Player
				playerImage4.setVisible(true);
				playerImage4.setImage(new Image(new FileInputStream(player.getHand().get(3).getImagePath())));
				playerCard4Value.setText(Integer.toString(player.cardToPoints(player.getHand().get(3))));

				// Dealer
				dealerImage4.setVisible(true);
				dealerImage4.setImage(new Image(new FileInputStream(dealer.getHand().get(3).getImagePath())));
				dealerCard4Value.setText(Integer.toString(dealer.cardToPoints(dealer.getHand().get(3))));

			} else if (player.getHand().size() == 5) {
				// Player
				playerImage5.setVisible(true);
				playerImage5.setImage(new Image(new FileInputStream(player.getHand().get(4).getImagePath())));
				playerCard5Value.setText(Integer.toString(player.cardToPoints(player.getHand().get(4))));

				// Dealer
				dealerImage5.setVisible(true);
				dealerImage5.setImage(new Image(new FileInputStream(dealer.getHand().get(4).getImagePath())));
				dealerCard5Value.setText(Integer.toString(dealer.cardToPoints(dealer.getHand().get(4))));

				hitButton.setDisable(true);
			}
		} catch (FileNotFoundException noFile) {
			noFile.printStackTrace();
		}
		gameConditionalCheck();
	}

	@FXML
	void stand(ActionEvent event) {
		dealer.getCardFrom(deck);

		gameConditionalCheck();
	}

	@FXML
	void placeBet(ActionEvent event) {
		try {
			int bet = Integer.parseInt(placeBetValue.getText());
			if (bet > player.getBalance()) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("ALERT");
				alert.setHeaderText(null);
				alert.setContentText("You cannot bet more chips than what is in your pot");
				alert.showAndWait();
			} else {
				player.setBet(player.getBet() + bet);
				player.setBalance(player.getBalance() - bet);
				pot.setText(Integer.toString(player.getBet()));
				balance.setText(Integer.toString(player.getBalance()));
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Bet Confirmed");
				alert.setHeaderText(null);
				alert.setContentText("You have bet a total of " + player.getBet() + " chips");
				alert.showAndWait();
			}

		} catch (NumberFormatException ex) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("ALERT");
			alert.setHeaderText(null);
			alert.setContentText("Please enter an integer value as your bet");
			alert.showAndWait();

		}
		gameConditionalCheck();
	}

	@FXML
	void gamePaneViewRules(ActionEvent event) {
		gamePane.setEffect(blur);
		rulesPane.setVisible(true);
	}

	@FXML
	void gamePaneExit(ActionEvent event) {
		exitGame();
	}

	@FXML
	void resetGame(ActionEvent event) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Confirm Reset");
		alert.setHeaderText(null);
		alert.setContentText("Are you sure you would like reset the game?");

		ButtonType reset = new ButtonType("Reset");
		ButtonType cancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);

		alert.getButtonTypes().setAll(reset, cancel);

		Optional<ButtonType> result = alert.showAndWait();

		if (result.get() == reset) {
			resetGameMethod();
		}
		gameConditionalCheck();
	}

	@Override
	public void start(Stage primarystage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("Game.fxml"));
		primarystage.setTitle("CO2012 2018-2019 CW2 Group17: Blackjack Game");
		primarystage.setScene(new Scene(root));
		primarystage.show();
	}

	// ######### Methods ##########
	public static void main(String[] args) {
		launch(args);
	}

	private void gameConditionalCheck() {
		if ((player.isBust() && !dealer.isBust()) || (dealer.isBust() && !player.isBust())) {
			if (player.isBust() && !dealer.isBust()) {
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Player bust");
				alert.setHeaderText(null);
				alert.setContentText("Unlucky! You went over 21.\n Would you like to play again?");

				ButtonType reset = new ButtonType("Play Again");
				ButtonType exit = new ButtonType("Exit");

				alert.getButtonTypes().setAll(reset, exit);

				Optional<ButtonType> result = alert.showAndWait();

				if (result.get() == reset) {
					resetGameMethod();
				} else if (result.get() == exit) {
					exitGame();
				}
			} else if (dealer.isBust() && !player.isBust()) {
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Dealer bust");
				alert.setHeaderText(null);
				alert.setContentText(
						"Congratulations! The dealer went over 21 and you win!\n Would you like to play again?");

				ButtonType reset = new ButtonType("Play Again");
				ButtonType exit = new ButtonType("Exit");

				alert.getButtonTypes().setAll(reset, exit);

				Optional<ButtonType> result = alert.showAndWait();

				if (result.get() == reset) {
					resetGameMethod();
				} else if (result.get() == exit) {
					exitGame();
				}
			}
		} else if (player.isBust() && dealer.isBust()) {
			if (player.getPoints() < dealer.getPoints()) {
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("You win");
				alert.setHeaderText(null);
				alert.setContentText(
						"Congratulations! You won because you both bust, but you were nearest to 21.\n Would you like to play again?");

				ButtonType reset = new ButtonType("Play Again");
				ButtonType exit = new ButtonType("Exit");

				alert.getButtonTypes().setAll(reset, exit);

				Optional<ButtonType> result = alert.showAndWait();

				if (result.get() == reset) {
					resetGameMethod();
				} else if (result.get() == exit) {
					exitGame();
				}
			} else if (dealer.getPoints() < player.getPoints()) {
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("You lose");
				alert.setHeaderText(null);
				alert.setContentText(
						"Unlucky! Dealer won because you both bust, but the dealer was nearest to 21.\n Would you like to play again?");

				ButtonType reset = new ButtonType("Play Again");
				ButtonType exit = new ButtonType("Exit");

				alert.getButtonTypes().setAll(reset, exit);

				Optional<ButtonType> result = alert.showAndWait();

				if (result.get() == reset) {
					resetGameMethod();
				} else if (result.get() == exit) {
					exitGame();
				}
			}
		} else if (player.getHand().size() == 5) {
			if (player.getPoints() > dealer.getPoints()) {
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("You win");
				alert.setHeaderText(null);
				alert.setContentText(
						"Congratulations! You won this hand by points because card limit was met.\n Would you like to play again?");

				ButtonType reset = new ButtonType("Play Again");
				ButtonType exit = new ButtonType("Exit");

				alert.getButtonTypes().setAll(reset, exit);

				Optional<ButtonType> result = alert.showAndWait();

				if (result.get() == reset) {
					resetGameMethod();
				} else if (result.get() == exit) {
					exitGame();
				}
			} else if (dealer.getPoints() > player.getPoints()) {
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("You lose");
				alert.setHeaderText(null);
				alert.setContentText(
						"Unlucky! You lost this hand by points because card limit was met.\n Would you like to play again?");

				ButtonType reset = new ButtonType("Play Again");
				ButtonType exit = new ButtonType("Exit");

				alert.getButtonTypes().setAll(reset, exit);

				Optional<ButtonType> result = alert.showAndWait();

				if (result.get() == reset) {
					resetGameMethod();
				} else if (result.get() == exit) {
					exitGame();
				}
			}
		} else if (player.getBalance() <= 0) {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("You lose");
			alert.setHeaderText(null);
			alert.setContentText("Unlucky! You ran out of chips!\n Would you like to play again?");

			ButtonType reset = new ButtonType("Play Again");
			ButtonType exit = new ButtonType("Exit");

			alert.getButtonTypes().setAll(reset, exit);

			Optional<ButtonType> result = alert.showAndWait();

			if (result.get() == reset) {
				resetGameMethod();
			} else if (result.get() == exit) {
				exitGame();
			}
		} else if (player.getPoints() == 21 || dealer.getPoints() == 21) {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Blackjack!");
			alert.setHeaderText(null);
			alert.setContentText("That's Blackjack!");
		}
	}

	private void exitGame() {

		if (player.getBalance() >= 100) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Won");
			alert.setHeaderText(null);
			alert.setContentText("You have won!");
			alert.showAndWait();
		} else {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Lost");
			alert.setHeaderText(null);
			alert.setContentText("You have lost!");
			alert.showAndWait();
		}
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Confirm Exit");
		alert.setHeaderText(null);
		alert.setContentText("Are you sure you would like to exit?");

		ButtonType exit = new ButtonType("Exit");
		ButtonType cancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);

		alert.getButtonTypes().setAll(exit, cancel);

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == exit) {
			System.exit(0);
		}
	}

	private void resetGameMethod() {

		player.setPoints(0);
		dealer.setPoints(0);

		player.emptyHand();
		dealer.emptyHand();

		player.setBalance(100);
		player.setBet(0);
		this.deck = new Deck();

		for (int i = 0; i < 2; i++) {
			player.getCardFrom(deck);
			dealer.getCardFrom(deck);
		}

		playerImage3.setVisible(false);
		playerImage4.setVisible(false);
		playerImage5.setVisible(false);
		dealerImage3.setVisible(false);
		dealerImage4.setVisible(false);
		dealerImage5.setVisible(false);

		player.setPlayerName(playerNameInput.getText());
		playerName.setText(player.getPlayerName());
		balance.setText(Integer.toString(player.getBalance()));
		pot.setText(Integer.toString(player.getBet()));

		try {
			playerImage1.setImage(new Image(new FileInputStream(player.getHand().get(0).getImagePath())));
			playerCard1Value.setText(Integer.toString(player.cardToPoints(player.getHand().get(0))));
			playerImage2.setImage(new Image(new FileInputStream(player.getHand().get(1).getImagePath())));
			playerCard2Value.setText(Integer.toString(player.cardToPoints(player.getHand().get(1))));

			dealerImage1.setImage(new Image(new FileInputStream(dealer.getHand().get(0).getImagePath())));
			dealerCard1Value.setText(Integer.toString(dealer.cardToPoints(dealer.getHand().get(0))));
			dealerImage2.setImage(new Image(new FileInputStream(dealer.getHand().get(1).getImagePath())));
			dealerCard2Value.setText(Integer.toString(dealer.cardToPoints(dealer.getHand().get(1))));
			playerHandScore.setText(Integer.toString(player.getPoints()));
			dealerHandScore.setText(Integer.toString(dealer.getPoints()));
			hitButton.setDisable(false);
		} catch (FileNotFoundException noFile) {
			noFile.printStackTrace();
		}
		gameConditionalCheck();
	}
}