package views.screen.admin;

import java.io.IOException;
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
import views.screen.popup.PopupScreen;

public class EditUserPopupScreen extends BaseScreenHandler {

    @FXML
    private TextField nameField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField addressField;

    @FXML
    private TextField phoneField;

    @FXML
    private Button saveButton;

    @FXML
    private Button cancelButton;

    @FXML
    private ComboBox<String> userTypeComboBox;

    @FXML
    private TextField passwordField;

    private AdminController adminController;
    AdminScreenHandler adminScreenHandler;
    private User user;

    public EditUserPopupScreen(Stage stage, User user, AdminScreenHandler adminScreenHandler) throws IOException {
        super(stage, Configs.EDIT_USER_POPUP_PATH);
        this.adminScreenHandler = adminScreenHandler;
        this.user = user;
        init();
    }

    public void init() {
        this.adminController = new AdminController();
        userTypeComboBox.getItems().clear();
        userTypeComboBox.getItems().addAll("Admin", "User", "Manager");

        nameField.setText(user.getName());
        emailField.setText(user.getEmail());
        addressField.setText(user.getAddress());
        phoneField.setText(user.getPhone());
        passwordField.setText(user.getPassword());
        userTypeComboBox.setValue(getUserTypeString(user.getUserType()));

        saveButton.setOnAction(event -> saveUser());
        cancelButton.setOnAction(event -> cancelEdit());
    }

    private String getUserTypeString(int userType) {
        switch (userType) {
            case 0: return "User";
            case 1: return "Manager";
            case 2: return "Admin";
            default: return "User";
        }
    }

    private int getUserTypeValue(String userType) {
        switch (userType) {
            case "User": return 0;
            case "Manager": return 1;
            case "Admin": return 2;
            default: return 0;
        }
    }

    private void saveUser() {
        String name = nameField.getText();
        String email = emailField.getText();
        String address = addressField.getText();
        String phone = phoneField.getText();
        String password = passwordField.getText();
        String userType = userTypeComboBox.getValue();

        int userTypeValue = getUserTypeValue(userType);

        user.setName(name);
        user.setEmail(email);
        user.setAddress(address);
        user.setPhone(phone);
        user.setPassword(password);
        user.setUserType(userTypeValue);

        try {
            adminController.updateUser(user);
            // Show success popup
            PopupScreen.success("User information updated successfully!");
            adminScreenHandler.reloadPage();
            // Close the popup
            Stage stage = (Stage) saveButton.getScene().getWindow();
            stage.close();
        } catch (SQLException | IOException e) {
            try {
                // Show error popup
                PopupScreen.error("Error updating user: Invalid Information");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void cancelEdit() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
}