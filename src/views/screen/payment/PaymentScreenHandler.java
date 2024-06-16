package views.screen.payment;

import controller.PaymentController;
import entity.order.DeliveryInformation;
import entity.order.Order;
import entity.payment.PaymentTransaction;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import services.vnpay.ConfigVNPay;
import utils.Configs;
import utils.Utils;
import views.screen.BaseScreenHandler;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.Map;

public class PaymentScreenHandler extends BaseScreenHandler {

    private final Order order;
    private final DeliveryInformation deliveryInformation;
    @FXML
    private Label pageTitle;
    @FXML
    private VBox vBox;

    public PaymentScreenHandler(Stage stage, String screenPath, Order order, DeliveryInformation deliveryInformation) throws IOException {
        super(stage, screenPath);
        this.setBController(new PaymentController());
        this.order = order;
        this.deliveryInformation = deliveryInformation;
        WebView paymentView = new WebView();
        WebEngine webEngine = paymentView.getEngine();
        webEngine.load(((PaymentController) getBController()).generateURL(order.calculateTotalPrice(deliveryInformation), "Payment"));
        webEngine.locationProperty().addListener((observable, oldValue, newValue) -> {
            completePaymentTransaction(newValue);
        });
        vBox.getChildren().clear();
        vBox.getChildren().add(paymentView);
    }

    private void completePaymentTransaction(String newValue) {
        if (newValue.contains(ConfigVNPay.vnp_ReturnUrl)) {
            try {
                URI uri = new URI(newValue);
                String query = uri.getQuery();
                System.out.println(query);

                Map<String, String> params = Utils.parseQueryString(query);
                PaymentController controller = (PaymentController) getBController();
                int orderId = controller.createOrder(deliveryInformation,order);
                if (orderId != -1) {
                    params.put("orderId", String.valueOf(orderId));
                    PaymentTransaction transaction = controller.makePayment(params);
                    showResult(transaction);
                }

            } catch (URISyntaxException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @throws IOException
     */
    void showResult(PaymentTransaction paymentTransaction) throws IOException, SQLException {
        PaymentController controller = (PaymentController) getBController();
        BaseScreenHandler resultScreen = new ResultScreenHandler(this.stage, Configs.RESULT_SCREEN_PATH, paymentTransaction);
        controller.emptyCart();
        resultScreen.setPreviousScreen(this);
        resultScreen.setHomeScreenHandler(homeScreenHandler);
        resultScreen.setScreenTitle("Result Screen");
        resultScreen.show();

    }

}