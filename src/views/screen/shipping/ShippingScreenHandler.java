package views.screen.shipping;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.ResourceBundle;

import controller.PlaceOrderController;
import common.exception.InvalidDeliveryInfoException;
import entity.invoice.Invoice;
import entity.order.DeliveryInformation;
import entity.order.Order;
import entity.user.User;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import utils.Configs;
import views.screen.BaseScreenHandler;
import views.screen.invoice.InvoiceScreenHandler;
import views.screen.popup.PopupScreen;

public class ShippingScreenHandler extends BaseScreenHandler implements Initializable {

    @FXML
    private Label screenTitle;

    @FXML
    private TextField name;

    @FXML
    private TextField phone;

    @FXML
    private TextField address;

    @FXML
    private Label labelTime;

    @FXML
    private Label labelRushShippingInstr;

    @FXML
    private TextField time;

    @FXML
    private TextField email;

    @FXML
    private TextField rushShippingInstr;

    @FXML
    private RadioButton chooseShip;

    @FXML
    private ComboBox<String> province;
    @FXML
    private void goBack() {
        if (getPreviousScreen() != null) {
            getPreviousScreen().show();
        } else {
        }
    }
    private final Order order;
    private User loggedInUser;

    public ShippingScreenHandler(Stage stage, String screenPath, Order order) throws IOException {
        super(stage, screenPath);
        this.order = order;
        System.out.println("Constructor user: " + this.loggedInUser);
    }
    public void setLoggedInUser(User loggedInUser) {
        this.loggedInUser = loggedInUser;
        initializeUserFields();
    }

    private void initializeUserFields() {
        if (this.loggedInUser != null) {
            name.setText(this.loggedInUser.getName());
            phone.setText(this.loggedInUser.getPhone());
            email.setText(this.loggedInUser.getEmail());
            address.setText(this.loggedInUser.getAddress());
        }
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        System.out.println("initialize method called");

        final BooleanProperty firstTime = new SimpleBooleanProperty(true);
        name.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue && firstTime.get()) {
                content.requestFocus();
                firstTime.setValue(false);
            }
        });

        this.province.getItems().addAll(Configs.PROVINCES);
        updateRushShippingView(false);

        chooseShip.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> obs, Boolean wasPreviouslySelected, Boolean isNowSelected) {
                updateRushShippingView(isNowSelected);
            }
        });
    }

    private void updateRushShippingView(boolean check) {
        labelTime.setVisible(check);;
        time.setVisible(check);
        rushShippingInstr.setVisible(check);
    }

    @FXML
    void submitDeliveryInfo(MouseEvent event) throws IOException, InterruptedException, SQLException {

        // add info to messages

        HashMap messages = new HashMap<>();
        messages.put("name", name.getText());
        messages.put("phone", phone.getText());
        messages.put("address", address.getText());
        DeliveryInformation deliveryInformation = new DeliveryInformation();
        deliveryInformation.setDeliveryAddress(address.getText());
        deliveryInformation.setRecipientName(name.getText());
        deliveryInformation.setPhoneNumber(phone.getText());
        if(province.getValue() != null){
            messages.put("province", province.getValue());
            deliveryInformation.setProvinceCity(province.getValue());
        }
        else notifyError("Empty province");
        messages.put("email", email.getText());
        deliveryInformation.setEmail(email.getText());

        if (chooseShip.isSelected()) {
            messages.put("isRushShipping", "Yes");
            deliveryInformation.setIsRushShipping("Yes");
            messages.put("time", time.getText());
            messages.put("rushShippingInstruction", rushShippingInstr.getText());
        } else {
            messages.put("isRushShipping", "No");
            deliveryInformation.setIsRushShipping("No");
        }
        if(!validateShippingInformation(messages)){
            return;
        }

        try {
            getBController().processDeliveryInfo(messages);
        } catch (InvalidDeliveryInfoException e) {
            throw new InvalidDeliveryInfoException(e.getMessage());
        }

        // calculate shipping fees
        int shippingFees = getBController().calculateShippingFee(order);
        order.setShippingFees(shippingFees);

        // create invoice screen
        Invoice invoice = getBController().createInvoice(deliveryInformation,order);
        BaseScreenHandler InvoiceScreenHandler = new InvoiceScreenHandler(this.stage, Configs.INVOICE_SCREEN_PATH, invoice,loggedInUser);
        InvoiceScreenHandler.setPreviousScreen(this);
        InvoiceScreenHandler.setHomeScreenHandler(homeScreenHandler);
        InvoiceScreenHandler.setScreenTitle("Invoice Screen");
        InvoiceScreenHandler.setBController(getBController());
        InvoiceScreenHandler.show();
    }

    public PlaceOrderController getBController() {
        return (PlaceOrderController) super.getBController();
    }

    public void notifyError(String error) throws IOException {
        PopupScreen.error(error);
    }
    private boolean validateShippingInformation(HashMap<String,String> deliveryInfor) throws IOException {
        String res = getBController().validateDeliveryInfo(deliveryInfor);
        if(res.equals("Valid")){
            return true;
        }
        notifyError(res);
        return false;
    }
}
