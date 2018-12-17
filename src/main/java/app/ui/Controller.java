package app.ui;

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
	private int potValue = 0;

	@FXML
	private Button startGameButton, placeBetButton, hitButton, standButton, resetGameButton;

	@FXML
	private Pane welcomePane, rulesPane, gamePane;

	@FXML
	private Text playerName, balance, pot, playerHandScore, dealerHandScore, validationOutputText, statusText;

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
		statusText.setText("New hand");
		if (playerNameInput.getText().isEmpty()) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("ALERT");
			alert.setHeaderText(null);
			alert.setContentText("Please enter a name");
			alert.showAndWait();
		} else {
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

			player.setPlayerName(playerNameInput.getText());
			playerName.setText(player.getPlayerName());
			balance.setText(Integer.toString(player.getBalance()));

			hitButton.setDisable(true);
			standButton.setDisable(true);
			resetGameButton.setDisable(true);
			updateUI();
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

	@FXML
	void hit(ActionEvent event) {
		player.getCardFrom(deck);
		dealer.makeDecision(deck);

		hitButton.setDisable(true);
		standButton.setDisable(true);
		placeBetButton.setDisable(false);
		placeBetValue.setDisable(false);
		updateUI();
	}

	@FXML
	void stand(ActionEvent event) {
		dealer.makeDecision(deck);
		hitButton.setDisable(true);
		standButton.setDisable(true);
		placeBetButton.setDisable(false);
		placeBetValue.setDisable(false);
		updateUI();
	}

	@FXML
	void placeBet(ActionEvent event) {
		try {
			int betPlaced = Integer.parseInt(placeBetValue.getText());
			if (betPlaced > player.getBalance()) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("ALERT");
				alert.setHeaderText(null);
				alert.setContentText("You cannot bet more chips than your balance");
				alert.showAndWait();
			} else {
				player.setBet(betPlaced);
				player.setBalance(player.getBalance() - betPlaced);
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Bet Confirmed");
				alert.setHeaderText(null);
				alert.setContentText("You have bet a total of " + player.getBet() + " chips");
				alert.showAndWait();
			}

		} catch (NumberFormatException n) {
			n.printStackTrace();
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("ALERT");
			alert.setHeaderText(null);
			alert.setContentText("Please enter an integer value as your bet");
			alert.showAndWait();
		}

		placeBetButton.setDisable(true);
		placeBetValue.setDisable(true);
		hitButton.setDisable(false);
		standButton.setDisable(false);
		player.setBet(0);
		placeBetValue.setText("0");
		updateUI();
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
			newGame();
		}

	}

	@Override
	public void start(Stage primarystage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("Game.fxml"));
		primarystage.setTitle("Blackjack");
		primarystage.setScene(new Scene(root));
		primarystage.show();
	}

	// ######### Methods ##########
	public static void main(String[] args) {
		launch(args);
	}

	private void exitGame() {

		// if (player.getBalance() >= 100) {
		// Alert alert = new Alert(AlertType.INFORMATION);
		// alert.setTitle("Won");
		// alert.setHeaderText(null);
		// alert.setContentText("You have won!");
		// alert.showAndWait();
		// } else {
		// Alert alert = new Alert(AlertType.INFORMATION);
		// alert.setTitle("Lost");
		// alert.setHeaderText(null);
		// alert.setContentText("You have lost!");
		// alert.showAndWait();
		// }
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

	private void newGame() {
		player.newGame();
		dealer.newGame();
		deck = new Deck();

		for (int i = 0; i < 2; i++) {
			player.getCardFrom(deck);
			dealer.getCardFrom(deck);
		}

		statusText.setText("New hand");
		potValue = 0;
		updateUI();
		placeBetButton.setDisable(false);
		placeBetValue.setDisable(false);
		resetGameButton.setDisable(true);

	}

	private void setImageViews() {
		player.loadImages();
		playerImage1.setImage(player.getImageList().get(0));
		playerImage2.setImage(player.getImageList().get(1));
		playerImage3.setImage(player.getImageList().get(2));
		playerImage4.setImage(player.getImageList().get(3));
		playerImage5.setImage(player.getImageList().get(4));

		dealer.loadImages();
		dealerImage1.setImage(dealer.getImageList().get(0));
		dealerImage2.setImage(dealer.getImageList().get(1));
		dealerImage3.setImage(dealer.getImageList().get(2));
		dealerImage4.setImage(dealer.getImageList().get(3));
		dealerImage5.setImage(dealer.getImageList().get(4));

		setImageViewVisibility();
	}

	private void setImageViewVisibility() {

		// Player
		if (player.getHand().size() == 2) {
			// visible
			playerImage1.setVisible(true);
			playerImage2.setVisible(true);

			// invisible
			playerImage3.setVisible(false);
			playerImage4.setVisible(false);
			playerImage5.setVisible(false);
		} else if (player.getHand().size() == 3) {
			playerImage3.setVisible(true);
			playerImage4.setVisible(false);
			playerImage5.setVisible(false);
		} else if (player.getHand().size() == 4) {
			playerImage4.setVisible(true);
			playerImage5.setVisible(false);
		} else if (player.getHand().size() == 5) {
			playerImage5.setVisible(true);
		}

		// Dealer
		if (dealer.getHand().size() == 2) {
			// visible
			dealerImage1.setVisible(true);
			dealerImage2.setVisible(true);

			// invisible
			dealerImage3.setVisible(false);
			dealerImage4.setVisible(false);
			dealerImage5.setVisible(false);
		} else if (dealer.getHand().size() == 3) {
			dealerImage3.setVisible(true);
			dealerImage4.setVisible(false);
			dealerImage5.setVisible(false);
		} else if (dealer.getHand().size() == 4) {
			dealerImage4.setVisible(true);
			dealerImage5.setVisible(false);
		} else if (dealer.getHand().size() == 5) {
			dealerImage5.setVisible(true);
		}

	}

	private void setCardValues() {
		if (player.getHand().size() == 2) {
			playerCard1Value.setText(Integer.toString(player.cardToPoints(player.getHand().get(0))));
			playerCard2Value.setText(Integer.toString(player.cardToPoints(player.getHand().get(1))));
		} else if (player.getHand().size() == 3) {
			playerCard3Value.setText(Integer.toString(player.cardToPoints(player.getHand().get(2))));
		} else if (player.getHand().size() == 4) {
			playerCard4Value.setText(Integer.toString(player.cardToPoints(player.getHand().get(3))));
		} else if (player.getHand().size() == 5) {
			playerCard5Value.setText(Integer.toString(player.cardToPoints(player.getHand().get(4))));
		}

		if (dealer.getHand().size() == 2) {
			dealerCard1Value.setText(Integer.toString(dealer.cardToPoints(dealer.getHand().get(0))));
			dealerCard2Value.setText(Integer.toString(dealer.cardToPoints(dealer.getHand().get(1))));
		} else if (dealer.getHand().size() == 3) {
			dealerCard3Value.setText(Integer.toString(dealer.cardToPoints(dealer.getHand().get(2))));
		} else if (dealer.getHand().size() == 4) {
			dealerCard4Value.setText(Integer.toString(dealer.cardToPoints(dealer.getHand().get(3))));
		} else if (dealer.getHand().size() == 5) {
			dealerCard5Value.setText(Integer.toString(dealer.cardToPoints(dealer.getHand().get(4))));
		}
		setCardValueVisibility();
	}

	private void setCardValueVisibility() {
		if (player.getHand().size() == 2) {
			playerCard1Value.setVisible(true);
			playerCard2Value.setVisible(true);

			playerCard3Value.setVisible(false);
			playerCard4Value.setVisible(false);
			playerCard5Value.setVisible(false);
		} else if (player.getHand().size() == 3) {
			playerCard3Value.setVisible(true);

			playerCard4Value.setVisible(false);
			playerCard5Value.setVisible(false);
		} else if (player.getHand().size() == 4) {
			playerCard4Value.setVisible(true);

			playerCard5Value.setVisible(false);
		} else if (player.getHand().size() == 5) {
			playerCard5Value.setVisible(true);
		}

		if (dealer.getHand().size() == 2) {
			dealerCard1Value.setVisible(true);
			dealerCard2Value.setVisible(true);

			dealerCard3Value.setVisible(false);
			dealerCard4Value.setVisible(false);
			dealerCard5Value.setVisible(false);
		} else if (dealer.getHand().size() == 3) {
			dealerCard3Value.setVisible(true);

			dealerCard4Value.setVisible(false);
			dealerCard5Value.setVisible(false);
		} else if (dealer.getHand().size() == 4) {
			dealerCard4Value.setVisible(true);

			dealerCard5Value.setVisible(false);
		} else if (dealer.getHand().size() == 5) {
			dealerCard5Value.setVisible(true);
		}
	}

	private void setPlayerHandScore() {
		playerHandScore.setText(Integer.toString(player.getPoints()));
		dealerHandScore.setText(Integer.toString(dealer.getPoints()));
	}

	private void updateUI() {
		setImageViews();
		setCardValues();
		setPlayerHandScore();
		statusCheck();
		pot.setText(Integer.toString(potValue));
		balance.setText(Integer.toString(player.getBalance()));
	}

	private void setDisableButtons(boolean value) {
		placeBetButton.setDisable(value);
		hitButton.setDisable(value);
		standButton.setDisable(value);
		placeBetValue.setDisable(value);
	}

	private void statusCheck() {
		if (player.isBust() || dealer.isBust() || player.isWinner() || dealer.isWinner()) {
			setDisableButtons(true);
			resetGameButton.setDisable(false);
		}

		if (player.isBust()) {
			statusText.setText("You bust!");
			setDisableButtons(true);

		}

		if (dealer.isBust()) {
			statusText.setText("Dealer bust!");
			setDisableButtons(true);

		}

		if (dealer.isBust() && player.isBust()) {
			statusText.setText("You both bust!");
		}

		if (player.isWinner()) {
			statusText.setText(player.getPlayerName() + " wins!");
			setDisableButtons(true);
			player.handleBet();

		}

		if (dealer.isWinner()) {
			statusText.setText("Dealer wins!");
			setDisableButtons(true);
		}
		player.setBet(0);
	}
}