package views.screen.payment;

import entity.payment.PaymentTransaction;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import utils.Utils;
import views.screen.BaseScreenHandler;

import java.io.IOException;
import java.sql.SQLException;

public class ResultScreenHandler extends BaseScreenHandler {

	@FXML
	private Label pageTitle;

	@FXML
	private Label resultLabel;

	@FXML
	private Label transactionID;


	@FXML
	private Label amount;

	@FXML
	private Label content;

	@FXML
	private Label transtime;

	@FXML
	private Button okButton;

	public ResultScreenHandler(Stage stage, String screenPath, PaymentTransaction transaction) throws IOException {
		super(stage, screenPath);
		resultLabel.setText(transaction.getMessage());
		transactionID.setText(transaction.getTransactionId());
		amount.setText(Utils.getCurrencyFormat(transaction.getAmount()));
		transtime.setText(transaction.getCreatedAt());
		content.setText(transaction.getTransactionContent());
	}

	@FXML
	void confirmPayment(MouseEvent event) throws IOException, SQLException {
		homeScreenHandler.show();
	}
}
