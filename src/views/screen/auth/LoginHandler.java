package views.screen.auth;

import java.io.IOException;
import java.util.logging.Logger;

import common.exception.LoginAccountException;
import controller.AuthController;
import entity.user.User;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import services.user.LoginManager;
import utils.Configs;
import views.screen.BaseScreenHandler;
import views.screen.admin.AdminScreenHandler;
import views.screen.home.HomeScreenHandler;
import views.screen.popup.PopupScreen;
import views.screen.sellerScreen.ManageProductScreenHandler;

public class LoginHandler extends BaseScreenHandler {
    @FXML
    private TextField email;

    @FXML
    private PasswordField password;

    @FXML
    private Button loginBtn;

    @FXML
    private Button registerBtn;

    public Alert alert;
    private HomeScreenHandler homeHandler;

    public static Logger LOGGER = utils.LOGGER.getLogger(LoginHandler.class.getName());

    private final AuthController authController = new AuthController();

    public LoginHandler(Stage stage, String screenPath) throws IOException {
        super(stage, screenPath);
        loginBtn.setOnAction(actionEvent -> {
            try {
                loginBtn();
            } catch (IOException e) {
                LOGGER.severe("IOException occurred while handling login button action: " + e.getMessage());
                e.printStackTrace();
            }
        });
        registerBtn.setOnAction(actionEvent -> {
            try {
                registerBtn();
            } catch (IOException e) {
                LOGGER.severe("IOException occurred while handling register button action: " + e.getMessage());
                e.printStackTrace();
            }
        });
    }

    public void loginBtn() throws IOException {
        if (email.getText().isEmpty() || password.getText().isEmpty()) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("You must fill in username and password");
            alert.showAndWait();
        } else {
            try {
                User user = authController.validateLogin(email.getText(), password.getText());
                LOGGER.info("User validated successfully: " + user.toString());
                LoginManager loginManager = new LoginManager();
                LoginManager.saveLoginInfo(user.getId(), user.getName(), user.getPhone(), user.getAddress(), user.getEmail());
                if (user.getStatus() == 0){
                    if (user.getUserType() == 2) {
                        LOGGER.info("Admin logged in");
                        AdminScreenHandler adminHandler = new AdminScreenHandler(this.stage, Configs.ADMIN_PATH);
                        adminHandler.setScreenTitle("Admin Screen");
                        adminHandler.show();
                    } else if (user.getUserType() == 1) {
                        LOGGER.info("Manager logged in");
                        ManageProductScreenHandler manageProductScreenHandler = new ManageProductScreenHandler(this.stage, Configs.SELLER_HOMEPAGE_PATH);
                        manageProductScreenHandler.setScreenTitle("Manager screen");
                        manageProductScreenHandler.show();
                    } else {
                        LOGGER.info("Customer logged in");
                        homeHandler = new HomeScreenHandler(this.stage, Configs.HOME_PATH, user);
                        homeHandler.setScreenTitle("Home Screen");
                        homeHandler.setImage();
                        homeHandler.show();
                    }
                }else{
                    LOGGER.severe("LoginAccountException: Account is blocked");
                    PopupScreen.error("User is blocked");
                }

            } catch (LoginAccountException e) {
                LOGGER.severe("LoginAccountException: " + e.getMessage());
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Wrong username or password");
                alert.showAndWait();
                e.printStackTrace();
            }
        }
    }

    public void registerBtn() throws IOException {
        try {
            RegisterHandler registerHandler = new RegisterHandler(this.stage, Configs.REGISTER);
            registerHandler.setScreenTitle("Register");
            registerHandler.show();
        } catch (Exception e) {
            LOGGER.severe("Exception occurred while handling register button action: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
