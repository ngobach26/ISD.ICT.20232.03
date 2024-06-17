package views.screen.home;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Logger;

import common.exception.MediaNotAvailableException;
import controller.HomeController;
import entity.cart.Cart;
import entity.cart.CartMedia;
import entity.media.Media;
import entity.user.User;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import services.user.LoginManager;
import utils.Utils;
import views.screen.FXMLScreenHandler;
import views.screen.popup.PopupScreen;

public class MediaHandler extends FXMLScreenHandler {

    @FXML
    protected ImageView mediaImage;

    @FXML
    protected Label mediaTitle;

    @FXML
    protected Label mediaPrice;

    @FXML
    protected Label mediaAvail;

    @FXML
    protected Spinner<Integer> spinnerChangeNumber;

    @FXML
    protected Button addToCartBtn;

    private static final Logger LOGGER = Utils.getLogger(MediaHandler.class.getName());
    private final Media media;
    private final HomeScreenHandler home;
    private final HomeController homeController;

    public MediaHandler(String screenPath, Media media, HomeScreenHandler home) throws SQLException, IOException {
        super(screenPath);
        this.media = media;
        this.home = home;
        this.homeController = new HomeController();
        addToCartBtn.setOnMouseClicked(event -> addToCart());
        setMediaInfo();
    }

    private void addToCart() {
        try {
            int quantity = spinnerChangeNumber.getValue();
            homeController.addMediaToCart(media, quantity);
            updateUIAfterAddingToCart(quantity);
            PopupScreen.success("The media " + media.getTitle() + " added to Cart");
        } catch (MediaNotAvailableException exp) {
            handleMediaNotAvailableException(exp);
        } catch (Exception exp) {
            LOGGER.severe("Cannot add media to cart: ");
            exp.printStackTrace();
        }
    }

    private void updateUIAfterAddingToCart(int quantity) throws SQLException {
        mediaAvail.setText(String.valueOf(media.getQuantity()));
        User user = LoginManager.getSavedLoginInfo();
        home.getNumMediaCartLabel().setText(Cart.getCart(user.getId()).getTotalMedia() + " media");
    }

    private void handleMediaNotAvailableException(MediaNotAvailableException exp) {
        try {
            String message = "Not enough media:\nRequired: " + spinnerChangeNumber.getValue() + "\nAvail: " + media.getQuantity();
            LOGGER.severe(message);
            PopupScreen.error(message);
        } catch (Exception e) {
            LOGGER.severe("Cannot add media to cart: " + e.getMessage());
        }
    }

    public Media getMedia() {
        return media;
    }

    private void setMediaInfo() throws SQLException {
        File file = new File(media.getImageURL());
        Image image = new Image(file.toURI().toString());
        mediaImage.setFitHeight(160);
        mediaImage.setFitWidth(152);
        mediaImage.setImage(image);

        mediaTitle.setText(media.getTitle());
        mediaPrice.setText(Utils.getCurrencyFormat(media.getPrice()));
        mediaAvail.setText(Integer.toString(media.getQuantity()));
        spinnerChangeNumber.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 1));

        setImage(mediaImage, media.getImageURL());
    }
}