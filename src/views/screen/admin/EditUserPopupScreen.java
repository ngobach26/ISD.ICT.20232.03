package views.screen.admin;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Logger;

import controller.AdminController;
import entity.user.User;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import utils.AdminUtils;
import utils.Configs;
import views.screen.BaseScreenHandler;
import views.screen.popup.PopupScreen;

public class EditUserPopupScreen extends BaseScreenHandler {

    private static final Logger LOGGER = utils.LOGGER.getLogger(EditUserPopupScreen.class.getName());

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
    private Button deleteButton;

    @FXML
    private ComboBox<String> userTypeComboBox;

    @FXML
    private ComboBox<String> userStatusComboBox;

    @FXML
    private TextField passwordField;

    private AdminController adminController;
    AdminScreenHandler adminScreenHandler;
    private final User user;

    public EditUserPopupScreen(Stage stage, User user, AdminScreenHandler adminScreenHandler) throws IOException {
        super(stage, Configs.EDIT_USER_POPUP_PATH);
        this.adminScreenHandler = adminScreenHandler;
        this.user = user;
        LOGGER.info("EditUserPopupScreen initialized for user: " + user.getId());
        init();
    }

    public void init() {
        this.adminController = new AdminController();
        LOGGER.info("Initializing user details in the form.");
        userTypeComboBox.getItems().clear();
        userTypeComboBox.getItems().addAll("Admin", "User", "Manager");
        userStatusComboBox.getItems().addAll("Active", "Block");
        nameField.setText(user.getName());
        emailField.setText(user.getEmail());
        addressField.setText(user.getAddress());
        phoneField.setText(user.getPhone());
        passwordField.setText(user.getPassword());
        userTypeComboBox.setValue(AdminUtils.getUserTypeString(user.getUserType()));
        userStatusComboBox.setValue(AdminUtils.getUserStatusString(user.getStatus()));

        saveButton.setOnAction(event -> saveUser());
        cancelButton.setOnAction(event -> cancelEdit());
        deleteButton.setOnAction(event -> deleteUser());
        LOGGER.info("Button actions initialized.");
    }

    private void saveUser() {
        String name = nameField.getText();
        String email = emailField.getText();
        String address = addressField.getText();
        String phone = phoneField.getText();
        String password = passwordField.getText();
        String userType = userTypeComboBox.getValue();
        String status = userStatusComboBox.getValue();

        LOGGER.info("Saving user details: name=" + name + ", email=" + email + ", address=" + address + ", phone=" + phone + ", userType=" + userType + ", status=" + status);

        int userTypeValue = AdminUtils.getUserTypeValue(userType);
        int statusValue = AdminUtils.getUserStatusValue(status);

        user.setName(name);
        user.setEmail(email);
        user.setAddress(address);
        user.setPhone(phone);
        user.setPassword(password);
        user.setUserType(userTypeValue);
        user.setStatus(statusValue);

        try {
            adminController.updateUser(user);
            LOGGER.info("User updated successfully in the database.");
            // Show success popup
            PopupScreen.success("User information updated successfully!");
            adminScreenHandler.reloadPage();
            LOGGER.info("Admin screen reloaded.");
            // Close the popup
            Stage stage = (Stage) saveButton.getScene().getWindow();
            stage.close();
            LOGGER.info("Edit user popup closed.");
        } catch (SQLException | IOException e) {
            LOGGER.severe("Error updating user: " + e.getMessage());
            try {
                // Show error popup
                PopupScreen.error("Error updating user: Invalid Information");
            } catch (IOException ex) {
                LOGGER.severe("Error displaying error popup: " + ex.getMessage());
                ex.printStackTrace();
            }
        }
    }

    private void deleteUser() {
        try {
            adminController.deleteUser(user.getId());
            LOGGER.info("User deleted successfully from the database.");
            // Show success popup
            PopupScreen.success("User deleted successfully!");
            adminScreenHandler.reloadPage();
            LOGGER.info("Admin screen reloaded.");
            // Close the popup
            Stage stage = (Stage) deleteButton.getScene().getWindow();
            stage.close();
            LOGGER.info("Delete user popup closed.");
        } catch (SQLException | IOException e) {
            LOGGER.severe("Error deleting user: " + e.getMessage());
            try {
                // Show error popup
                PopupScreen.error("Error deleting user");
            } catch (IOException ex) {
                LOGGER.severe("Error displaying error popup: " + ex.getMessage());
                ex.printStackTrace();
            }
        }
    }

    private void cancelEdit() {
        LOGGER.info("Cancel edit action triggered.");
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
        LOGGER.info("Edit user popup closed.");
    }
}
