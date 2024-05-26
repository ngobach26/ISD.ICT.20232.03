package utils;

import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

public class Configs {

	// database Configs
	public static final String DB_NAME = "aims";
	public static final String DB_USERNAME = System.getenv("DB_USERNAME");
	public static final String DB_PASSWORD = System.getenv("DB_PASSWORD");
	public static final String HOME_PATH  = "/views/fxml/home.fxml";
	public static final String HOME_MEDIA_PATH = "/views/fxml/media_home.fxml";
	public static final String IMAGE_PATH = "assets/images";
	public static final String POPUP_PATH = "/views/fxml/popup.fxml";
	public static final String SPLASH_SCREEN_PATH = "/views/fxml/splash.fxml";
	
	
	public static float PERCENT_VAT = 10;
	
	//	Viewing cart
	public static final String CART_SCREEN_PATH = "/views/fxml/cart.fxml";
	public static final String CART_MEDIA_PATH = "/views/fxml/media_cart.fxml";
	
	//	FONT
	public static Font REGULAR_FONT = Font.font("Segoe UI", FontWeight.NORMAL, FontPosture.REGULAR, 24);

}
