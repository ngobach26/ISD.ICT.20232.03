package views.screen.cart;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import common.exception.MediaNotAvailableException;
import common.exception.PlaceOrderException;
import controller.PlaceOrderController;
import controller.CartController;
import entity.cart.CartMedia;
import entity.order.Order;
import entity.user.User;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import utils.Configs;
import utils.PaymentUtils;
import views.screen.BaseScreenHandler;
import views.screen.popup.PopupScreen;
import views.screen.shipping.ShippingScreenHandler;

public class CartScreenHandler extends BaseScreenHandler {

	private static final Logger LOGGER = utils.LOGGER.getLogger(CartScreenHandler.class.getName());

	@FXML
	private ImageView aimsImage;

	@FXML
	private Label pageTitle;

	@FXML
	VBox vboxCart;

	@FXML
	private Label shippingFees;

	@FXML
	private Label labelAmount;

	@FXML
	private Label labelSubtotal;

	@FXML
	private Label labelVAT;

	@FXML
	private Button btnPlaceOrder;

	@FXML
	private Button btnCancelOrder;

	private final CartController cartController;
	private User loggedInUser;

	public CartScreenHandler(Stage stage, String screenPath, User loggedInUser) throws IOException, SQLException {
		super(stage, screenPath);
		this.cartController = new CartController();
		this.loggedInUser = loggedInUser;
		// fix relative image path caused by fxml
		File file = new File("assets/images/Logo.png");
		Image im = new Image(file.toURI().toString());
		aimsImage.setImage(im);

		// Click to go back to home screen
		aimsImage.setOnMouseClicked(e -> {
				homeScreenHandler.show();
		});

		// On clicked, start Place Order UC
		btnPlaceOrder.setOnMouseClicked(e -> {
			LOGGER.info("Place Order button clicked");
			try {
				requestToPlaceOrder();
			} catch (SQLException | IOException exp) {
				LOGGER.severe("Cannot place the order, see the logs");
				exp.printStackTrace();
				throw new PlaceOrderException(Arrays.toString(exp.getStackTrace()).replaceAll(", ", "\n"));
			}

		});
		btnCancelOrder.setOnMouseClicked(e -> {
			LOGGER.info("Cancel Order button clicked");
			navigateToHomeScreen();
		});
	}
	private void navigateToHomeScreen() {
		try {
			homeScreenHandler.show();
			LOGGER.info("Navigated to Home Screen");
		} catch (Exception e) {
			LOGGER.severe("Error while navigating to Home Screen: " + e.getMessage());
			e.printStackTrace();
		}
	}

	public Label getLabelAmount() {
		return labelAmount;
	}

	public Label getLabelSubtotal() {
		return labelSubtotal;
	}

	public CartController getBController(){
		return (CartController) super.getBController();
	}

	public void requestToViewCart(BaseScreenHandler prevScreen) throws SQLException {
		try{
			setPreviousScreen(prevScreen);
			setScreenTitle("Cart Screen");
			getBController().checkAvailabilityOfProduct();
			displayCartWithMediaAvailability();
			show();
		} catch (MediaNotAvailableException e) {
			// if some media are not available then display cart and break usecase Place Order
			displayCartWithMediaAvailability();
		}

	}

	public void requestToPlaceOrder() throws SQLException, IOException {
		try {
			// create placeOrderController and process the order
			PlaceOrderController placeOrderController = new PlaceOrderController();
			if (cartController.getListCartMedia().size() == 0){
				PopupScreen.error("You don't have anything to place");
				return;
			}

			placeOrderController.placeOrder();

			// display available media
			displayCartWithMediaAvailability();

			// create order
			Order order = placeOrderController.createOrder();

			ShippingScreenHandler ShippingScreenHandler = new ShippingScreenHandler(this.stage, Configs.SHIPPING_SCREEN_PATH, order);
			ShippingScreenHandler.setPreviousScreen(this);
			ShippingScreenHandler.setLoggedInUser(this.loggedInUser);
			ShippingScreenHandler.setHomeScreenHandler(homeScreenHandler);
			ShippingScreenHandler.setScreenTitle("Shipping Screen");
			ShippingScreenHandler.setBController(placeOrderController);
			ShippingScreenHandler.show();

		} catch (MediaNotAvailableException e) {
			// if some media are not available then display cart and break usecase Place Order
			displayCartWithMediaAvailability();
		}

	}

	public void updateCart() throws SQLException{
		getBController().checkAvailabilityOfProduct();
		displayCartWithMediaAvailability();
	}

	void updateCartAmount() throws SQLException {
		// calculate subtotal and amount
		int subtotal = getBController().getCartSubtotal();
		int vat = (int)((Configs.PERCENT_VAT/100)*subtotal);
		int amount = subtotal + vat;
		LOGGER.info("amount" + amount);

		// update subtotal and amount of Cart
		labelSubtotal.setText(PaymentUtils.getCurrencyFormat(subtotal));
		labelVAT.setText(PaymentUtils.getCurrencyFormat(vat));
		labelAmount.setText(PaymentUtils.getCurrencyFormat(amount));
	}

	private void displayCartWithMediaAvailability() throws SQLException {
		// clear all old cartMedia
		vboxCart.getChildren().clear();

		// get list media of cart after check availability
		List lstMedia = cartController.getListCartMedia();

		try {
			for (Object cm : lstMedia) {

				// display the attribute of vboxCart media
				CartMedia cartMedia = (CartMedia) cm;
				MediaHandler mediaCartScreen = new MediaHandler(Configs.CART_MEDIA_PATH, this);
				mediaCartScreen.setCartMedia(cartMedia);

				// add spinner
				vboxCart.getChildren().add(mediaCartScreen.getContent());
			}
			// calculate subtotal and amount
			updateCartAmount();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}