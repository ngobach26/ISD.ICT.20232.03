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
import utils.Utils;
import views.screen.BaseScreenHandler;
import views.screen.popup.PopupScreen;

public class CreateUserPopupScreen extends BaseScreenHandler {

    private static final Logger LOGGER = Utils.getLogger(CreateUserPopupScreen.class.getName());

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
    private ComboBox<String> userTypeComboBox;

    @FXML
    private TextField passwordField;

    private final AdminController adminController;
    private final AdminScreenHandler adminScreenHandler;

    public CreateUserPopupScreen(Stage stage, AdminScreenHandler adminScreenHandler) throws IOException {
        super(stage, Configs.CREATE_USER_POPUP_PATH);
        this.adminController = new AdminController();
        this.adminScreenHandler = adminScreenHandler;
        LOGGER.info("CreateUserPopupScreen initialized.");
        init();
    }

    public void init() {
        if (createButton == null) {
            LOGGER.warning("createButton is null");
        } else {
            LOGGER.info("createButton is initialized");
        }
        createButton.setOnAction(event -> createUser());

        userTypeComboBox.getItems().clear();
        userTypeComboBox.getItems().addAll("Admin", "User", "Manager");
        LOGGER.info("User type combo box initialized with values.");
    }

    private void createUser() {
        String name = nameField.getText();
        String email = emailField.getText();
        String address = addressField.getText();
        String phone = phoneField.getText();
        String password = passwordField.getText();
        String userType = userTypeComboBox.getValue();

        LOGGER.info("Creating user with details: name=" + name + ", email=" + email + ", address=" + address + ", phone=" + phone + ", userType=" + userType);

        int userTypeValue = AdminUtils.getUserTypeValue(userType);

        User newUser = new User(name, email, address, phone, userTypeValue, password);
        try {
            adminController.createUser(newUser);
            LOGGER.info("User created successfully in the database.");
            // Show success popup
            PopupScreen.success("User created successfully!");
            adminScreenHandler.reloadPage();
            LOGGER.info("Admin screen reloaded.");
            // Close the popup
            Stage stage = (Stage) createButton.getScene().getWindow();
            stage.close();
            LOGGER.info("Create user popup closed.");
        } catch (SQLException | IOException e) {
            LOGGER.severe("Error creating user: " + e.getMessage());
            try {
                // Show error popup
                PopupScreen.error("Error creating user: Invalid Information");
            } catch (IOException ex) {
                LOGGER.severe("Error displaying error popup: " + ex.getMessage());
                ex.printStackTrace();
            }
        }
    }
}
