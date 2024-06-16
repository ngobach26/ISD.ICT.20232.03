package views.screen.cart;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import common.exception.MediaUpdateException;
import common.exception.ViewCartException;
import controller.CartController;
import entity.cart.CartMedia;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import utils.Configs;
import utils.Utils;
import views.screen.FXMLScreenHandler;

public class MediaHandler extends FXMLScreenHandler {
	@FXML
	protected HBox hboxMedia;

	@FXML
	protected ImageView image;

	@FXML
	protected VBox description;

	@FXML
	protected Label labelOutOfStock;

	@FXML
	protected VBox spinnerFX;

	@FXML
	protected Label title;

	@FXML
	protected Label price;

	@FXML
	protected Label currency;

	@FXML
	protected Button btnDelete;

	private CartMedia cartMedia;
	private Spinner<Integer> spinner;
	private final CartScreenHandler cartScreen;
	private final CartController cartController;

	public MediaHandler(String screenPath, CartScreenHandler cartScreen) throws IOException {
		super(screenPath);
		this.cartScreen = cartScreen;
		this.cartController = new CartController();
		hboxMedia.setAlignment(Pos.CENTER);
	}

	public void setCartMedia(CartMedia cartMedia) {
		this.cartMedia = cartMedia;
		setMediaInfo();
	}

	private void setMediaInfo() {
		title.setText(cartMedia.getMedia().getTitle());
		price.setText(Utils.getCurrencyFormat(cartMedia.getPrice()));
		File file = new File(cartMedia.getMedia().getImageURL());
		Image im = new Image(file.toURI().toString());
		image.setImage(im);
		image.setPreserveRatio(false);
		image.setFitHeight(110);
		image.setFitWidth(92);

		btnDelete.setFont(Configs.REGULAR_FONT);
		btnDelete.setOnMouseClicked(e -> {
			try {
				cartController.removeCartMedia(cartMedia);
				cartScreen.updateCart();
			} catch (SQLException | ViewCartException exp) {
				exp.printStackTrace();
			}
		});

		initializeSpinner();
	}

	private void initializeSpinner() {
		SpinnerValueFactory<Integer> valueFactory =
				new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, cartMedia.getQuantity());
		spinner = new Spinner<>(valueFactory);
		spinner.setOnMouseClicked(e -> {
			try {
				int numOfProd = this.spinner.getValue();
				numOfProd = cartController.checkStockAvailability(cartMedia, numOfProd);
				labelOutOfStock.setText(numOfProd < cartMedia.getQuantity() ? "Sorry, Only " + numOfProd + " remain in stock" : "");
				spinner.getValueFactory().setValue(numOfProd);
				cartController.updateCartMediaQuantity(cartMedia, numOfProd);
				price.setText(Utils.getCurrencyFormat(numOfProd * cartMedia.getPrice()));
				cartScreen.updateCartAmount();
			} catch (SQLException | MediaUpdateException e1) {
				e1.printStackTrace();
			}
		});
		spinnerFX.setAlignment(Pos.CENTER);
		spinnerFX.getChildren().add(this.spinner);
	}
}
