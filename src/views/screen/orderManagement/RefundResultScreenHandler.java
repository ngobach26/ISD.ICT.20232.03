package views.screen.orderManagement;

import entity.payment.RefundTransaction;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import utils.Configs;
import utils.Utils;
import views.screen.BaseScreenHandler;

import java.io.IOException;
import java.sql.SQLException;

public class RefundResultScreenHandler extends BaseScreenHandler {

    @FXML
    private Label id;

    @FXML
    private Label amount;

    @FXML
    private Label content;

    @FXML
    private Label status;

    @FXML
    private Button okButton;


    public RefundResultScreenHandler(Stage stage, String screenPath, RefundTransaction refundTransaction) throws IOException {
        super(stage, screenPath);
        id.setText(refundTransaction.getId());
        amount.setText(Utils.getCurrencyFormat(refundTransaction.getAmount()));
        content.setText(refundTransaction.getContent());
        status.setText(refundTransaction.getMessage());

        okButton.setOnMouseClicked(mouseEvent -> {
            try {
                goBack();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }
    private void goBack() throws SQLException {
        OrderManagementScreenHandler orderManagementAdminScreenHandler = null;
        try {
            orderManagementAdminScreenHandler = new OrderManagementScreenHandler(stage, Configs.ORDER_MANAGEMENT_ADMIN_PATH);
        } catch (IOException e) {
            e.printStackTrace();
        }
        orderManagementAdminScreenHandler.show();
    }
}
