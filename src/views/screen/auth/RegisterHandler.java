package views.screen.auth;

import java.io.IOException;
import java.util.logging.Logger;

import controller.AuthController;
//import entity.user.User;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import utils.Configs;
import views.screen.BaseScreenHandler;

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
	private final AuthController authController = new AuthController();
	public static Logger LOGGER = utils.LOGGER.getLogger(LoginHandler.class.getName());

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
		
		if(name.getText().isEmpty()) {
			 alert = new Alert(Alert.AlertType.ERROR);
             alert.setTitle("Error Message");
             alert.setHeaderText(null);
             alert.setContentText("Invalid name");
             alert.showAndWait();
             throw new RuntimeException("Some field were left blank! Please re-enter register information");
		}
		if(!validatePhoneField(phone)) {
			alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Invalid phone");
            alert.showAndWait();
            throw new RuntimeException("Some field were left blank! Please re-enter register information");
		}
		if(address.getText().isEmpty()) {
			alert = new Alert(Alert.AlertType.ERROR);
           alert.setTitle("Error Message");
           alert.setHeaderText(null);
           alert.setContentText("Invalid address");
           alert.showAndWait();
           throw new RuntimeException("Some field were left blank! Please re-enter register information");
		}
		if(!validateEmailField(email)) {
			alert = new Alert(Alert.AlertType.ERROR);
	           alert.setTitle("Error Message");
	           alert.setHeaderText(null);
	           alert.setContentText("Invalid email");
	           alert.showAndWait();
	           throw new RuntimeException("Some field were left blank! Please re-enter register information");
		}
		if(password.getText().isEmpty()) {
			alert = new Alert(Alert.AlertType.ERROR);
	           alert.setTitle("Error Message");
	           alert.setHeaderText(null);
	           alert.setContentText("Invalid password");
	           alert.showAndWait();
	           throw new RuntimeException("Some field were left blank! Please re-enter register information");
		}
		if(userType.getText().isEmpty()) {
			alert = new Alert(Alert.AlertType.ERROR);
	           alert.setTitle("Error Message");
	           alert.setHeaderText(null);
	           alert.setContentText("Invalid user type");
	           alert.showAndWait();
	           throw new RuntimeException("Some field were left blank! Please re-enter register information");
		}
        
         else {
        	String nameText = name.getText();
            String phoneText = phone.getText();
            String addressText = address.getText();
            String emailText = email.getText();
            String passwordText = password.getText();
            int userTypeText = Integer.parseInt(userType.getText());
			System.out.println("đăng kí");

			try {
				boolean success = authController.registerUser(nameText, emailText, addressText, phoneText, passwordText, userTypeText);
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
	public boolean validatePhoneField(TextField phoneField) {
        if(phoneField.getText().isEmpty()) {
			return false;
		}
        String phone = phoneField.getText();
        if(phone.length() != 10) {
			return false;
		}
        if(phone.charAt(0) != '0') {
			return false;
		} else if(phone.charAt(1) == '0') {
			return false;
		}
        for(int i=1; i<phone.length(); i++) {
            if(phone.charAt(i) < 48 && phone.charAt(i) > 57) {
				return false;
			}
        }
        return true;
    }

    public boolean validateEmailField(TextField emailField) {
        if(emailField.getText().isEmpty()) {
			return false;
		}
        String email = emailField.getText();
        if(!email.contains("@") || (!email.contains(".com") && !email.contains(".org") && !email.contains(".edu"))) {
			return false;
		}

        // Allow only 1 @ character in email
        int count = 0;
        for(int i=0; i<email.length(); i++) {
			if(email.charAt(i) == '@') {
				count++;
			}
		}
        if(count > 1) {
			return false;
		}
        int idx_at = email.indexOf('@');
        if(idx_at == 0) {
			return false;
		}
        return email.charAt(idx_at + 1) != '.';
    }

}
