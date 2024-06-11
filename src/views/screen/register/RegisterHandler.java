package views.screen.register;

import java.io.IOException;
import java.util.logging.Logger;

import DAO.UserDAO;
import controller.RegisterController;
//import entity.user.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import utils.Configs;
import utils.Utils;
import views.screen.BaseScreenHandler;
import views.screen.login.LoginHandler;

public class RegisterHandler extends BaseScreenHandler {
	@FXML
	private TextField name;
	@FXML
	private TextField phone;
	@FXML
	private TextField address;
	@FXML
	private TextField email;
	@FXML
	private PasswordField password;
	@FXML
	private TextField userType;
	@FXML
	private Button registerBtn;
	@FXML
	private Button loginBtn;
	public Alert alert;
	private RegisterController registerController = new RegisterController();
	public static Logger LOGGER = Utils.getLogger(LoginHandler.class.getName());

	public RegisterHandler(Stage stage, String screenPath) throws IOException {
		super(stage, screenPath);

	}

	@FXML
	public void initialize() {
		loginBtn.setOnAction(actionEvent -> {
			try {
				loginBtn();
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		registerBtn.setOnAction(actionEvent -> {
			try {
				registerButton();
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
	}

	public void registerButton() throws IOException {
		
        if(name.getText().isEmpty()|| phone.getText().isEmpty() || address.getText().isEmpty() || email.getText().isEmpty() || password.getText().isEmpty()) {
//        	System.out.println("abc");
        	 alert = new Alert(Alert.AlertType.ERROR);
             alert.setTitle("Error Message");
             alert.setHeaderText(null);
             alert.setContentText("You must fill in all information");
             alert.showAndWait();
        } else {
        	String nameText = name.getText();
            String phoneText = phone.getText();
            String addressText = address.getText();
            String emailText = email.getText();
            String passwordText = password.getText();
            int userTypeText = Integer.parseInt(userType.getText());
			System.out.println("đăng kí");
			boolean success = registerController.registerUser(nameText, emailText, addressText, phoneText, passwordText,
					userTypeText, new UserDAO());
			try {
				LoginHandler loginHandler = new LoginHandler(this.stage, Configs.LOGIN);
				loginHandler.setScreenTitle("Login");
				loginHandler.show();
				System.out.println("Đăng kí thành công");
			} catch (Exception e) {
				alert = new Alert(Alert.AlertType.ERROR);
				alert.setTitle("Đăng kí không thành công");
				alert.setHeaderText(null);
				alert.setContentText("Tài khoản đã tồn tại");
				alert.showAndWait();
				e.printStackTrace();
			}
        }
	}

	public void loginBtn() throws IOException {

		try {
			LoginHandler loginHandler = new LoginHandler(this.stage, Configs.LOGIN);
			loginHandler.setScreenTitle("Login");
			loginHandler.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
