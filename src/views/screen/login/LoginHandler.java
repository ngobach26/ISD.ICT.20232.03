package views.screen.login;


import java.io.IOException;
import java.util.logging.Logger;

import DAO.UserDAO;
import common.exception.LoginAccountException;
import controller.LoginController;
import entity.user.User;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import services.user.LoginManager;
import utils.Configs;
import utils.Utils;
import views.screen.BaseScreenHandler;
//import views.screen.RegisterHandler;
import views.screen.home.HomeScreenHandler;
import views.screen.register.RegisterHandler;
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


    public static Logger LOGGER = Utils.getLogger(LoginHandler.class.getName());

    private LoginController loginController = new LoginController();

    public LoginHandler(Stage stage, String screenPath) throws IOException {
        super(stage, screenPath);
        loginBtn.setOnAction(actionEvent -> {
			try {
				loginBtn();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
        registerBtn.setOnAction(actionEvent -> {
			try {
				registerBtn();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
    }

    public void loginBtn() throws IOException{
        if (email.getText().isEmpty() || password.getText().isEmpty()){
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("You must fill in username and password");
            alert.showAndWait();
        } else {
            try {
                User user = loginController.validateLogin(email.getText(), password.getText(), new UserDAO());
                System.out.println(">>>check user: " + user.toString());
                if (user.getUser_type() == 1){
                    System.out.println("Admin logged in");
                    //tạo giao diện admin
                    
                }
                else {
                	LoginManager loginManager = new LoginManager();
                	loginManager.saveLoginInfo(user.getId(), user.getName(), user.getPhone(), user.getAddress(), user.getEmail());
                    System.out.println("Customer logged in");
                    homeHandler = new HomeScreenHandler(this.stage, Configs.HOME_PATH);
					homeHandler.setScreenTitle("Home Screen");
					homeHandler.setImage();
					homeHandler.show();
                }
            } catch (LoginAccountException e){
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Wrong username or password");
                alert.showAndWait();
                e.printStackTrace();
            }
        }
        
       
    }
    public void registerBtn() throws IOException{
        
		try {
         
          RegisterHandler registerHandler = new RegisterHandler(this.stage, Configs.REGISTER);
          registerHandler.setScreenTitle("Register");
          registerHandler.show();
		} catch (Exception e) {
			  e.printStackTrace();
		}
    }


}