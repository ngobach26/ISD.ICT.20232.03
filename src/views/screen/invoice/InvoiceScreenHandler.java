package views.screen.invoice;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.logging.Logger;

import common.exception.ProcessInvoiceException;
import controller.PaymentController;
import entity.invoice.Invoice;
import entity.order.DeliveryInformation;
import entity.order.OrderMedia;
import entity.user.User;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import utils.Configs;
import utils.Utils;
import views.screen.BaseScreenHandler;
import views.screen.payment.PaymentScreenHandler;



public class InvoiceScreenHandler extends BaseScreenHandler {

	private static final Logger LOGGER = Utils.getLogger(InvoiceScreenHandler.class.getName());

	@FXML
	private Label pageTitle;

	@FXML
	private Label name;

	@FXML
	private Label phone;

	@FXML
	private Label province;

	@FXML
	private Label address;

	@FXML
	private Label instructions;

	@FXML
	private Label subtotal;

	@FXML
	private Label shippingFees;

	@FXML
	private Label labelTime;

	@FXML
	private Label labelRushShippingInstr;

	@FXML
	private Label time;

	@FXML
	private Label rushInstruction;

	@FXML
	private Label total;

	@FXML
	private Label email;

	@FXML
	private VBox vboxItems;
	@FXML
	private void goBack() {
		if (getPreviousScreen() != null) {
			getPreviousScreen().show();
		} else {
		}
	}

	private final Invoice invoice;
	private User user;

	public InvoiceScreenHandler(Stage stage, String screenPath, Invoice invoice,User user) throws IOException {
		super(stage, screenPath);
		this.invoice = invoice;
		this.user = user;
		setInvoiceInfo();
	}

	private void setInvoiceInfo(){
		DeliveryInformation deliveryInfo = invoice.getDeliveryInformation();
		name.setText(deliveryInfo.getRecipientName());
		phone.setText(deliveryInfo.getPhoneNumber());
		province.setText(deliveryInfo.getProvinceCity());
		instructions.setText(deliveryInfo.getDeliveryAddress());
		address.setText(deliveryInfo.getDeliveryAddress());
		email.setText(deliveryInfo.getEmail());

		if(deliveryInfo.isRushShipping()){
			labelTime.setVisible(true);
			labelRushShippingInstr.setVisible(true);
			time.setVisible(true);
			rushInstruction.setVisible(true);
		}
		else{
			labelTime.setVisible(false);
			labelRushShippingInstr.setVisible(false);
			time.setVisible(false);
			rushInstruction.setVisible(false);
		}

		subtotal.setText(Utils.getCurrencyFormat(invoice.getOrder().calculateTotalProductIncludeVAT()));
		shippingFees.setText(Utils.getCurrencyFormat(invoice.getOrder().calculateShippingFees(deliveryInfo)));
		total.setText(Utils.getCurrencyFormat(invoice.getOrder().calculateTotalPrice(deliveryInfo)));
		invoice.getOrder().getlstOrderMedia().forEach(orderMedia -> {
			try {
				MediaInvoiceScreenHandler mis = new MediaInvoiceScreenHandler(Configs.INVOICE_MEDIA_SCREEN_PATH);
				mis.setOrderMedia(orderMedia);
				vboxItems.getChildren().add(mis.getContent());
			} catch (IOException | SQLException e) {
				System.err.println("errors: " + e.getMessage());
				throw new ProcessInvoiceException(e.getMessage());
			}
			
		});

	}
	@FXML
	void confirmInvoice(MouseEvent event) throws IOException {
		BaseScreenHandler paymentScreen = new PaymentScreenHandler(this.stage, Configs.PAYMENT_SCREEN_PATH, invoice.getOrder(), invoice.getDeliveryInformation(),user);
		paymentScreen.setBController(new PaymentController());
		paymentScreen.setPreviousScreen(this);
		paymentScreen.setHomeScreenHandler(homeScreenHandler);
		paymentScreen.setScreenTitle("Payment Screen");
		paymentScreen.show();
		LOGGER.info("Confirmed invoice");
	}
}
