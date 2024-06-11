package views.screen.popup;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import controller.AdminController;
import entity.user.User;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import utils.Configs;
import views.screen.BaseScreenHandler;

public class CreateUserPopupScreen extends BaseScreenHandler {

    @FXML
    private TextField nameField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField addressField;

    @FXML
    private TextField phoneField;

    @FXML
    private Button createButton;
    @FXML
    private ComboBox userTypeComboBox;
    @FXML
    private TextField passwordField;

    private AdminController adminController;

    public CreateUserPopupScreen(Stage stage) throws IOException {
        super(stage, Configs.CREATE_USER_POPUP_PATH);
        init();
    }

    public void init() {
        createButton.setOnAction(event -> createUser());
        this.adminController = new AdminController();
        userTypeComboBox.getItems().clear();
        userTypeComboBox.getItems().addAll("Admin", "User", "Manager");

    }

    private void createUser() {
        String name = nameField.getText();
        String email = emailField.getText();
        String address = addressField.getText();
        String phone = phoneField.getText();
        String password = passwordField.getText();
        String userType = (String) userTypeComboBox.getValue();

        int userTypeValue;
        switch (userType) {
            case "User":
                userTypeValue = 0;
                break;
            case "Manager":
                userTypeValue = 1;
                break;
            case "Admin":
                userTypeValue = 2;
                break;
            default:
                // Handle invalid user type
                return;
        }
        User newUser = new User(name, email, address, phone , userTypeValue, password);
        try {
            adminController.createUser(newUser);
            // Show success popup
            PopupScreen.success("User created successfully!");
            // Close the popup
            Stage stage = (Stage) createButton.getScene().getWindow();
            stage.close();
        } catch (SQLException | IOException e) {
            try {
                // Show error popup
                PopupScreen.error("Error creating user: Invalid Information");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

}
