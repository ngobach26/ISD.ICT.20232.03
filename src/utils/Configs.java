package utils;

import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

public class Configs {

	// database Configs
	public static final String DB_NAME = "aims";
	public static final String DB_USERNAME = System.getenv("DB_USERNAME");
	public static final String DB_PASSWORD = System.getenv("DB_PASSWORD");

    public static float PERCENT_VAT = 10;
	
	//	Static resource
	public static final String IMAGE_PATH = "assets/images";
	public static final String SELLER_HOMEPAGE_PATH = "/views/fxml/sellerHomeScreen.fxml";
	public static final String INVOICE_SCREEN_PATH = "/views/fxml/invoice.fxml";
	public static final String INVOICE_MEDIA_SCREEN_PATH = "/views/fxml/media_invoice.fxml";
	public static final String PAYMENT_SCREEN_PATH = "/views/fxml/payment.fxml";
	public static final String RESULT_SCREEN_PATH = "/views/fxml/result.fxml";
	public static final String SPLASH_SCREEN_PATH = "/views/fxml/splash.fxml";
	public static final String CART_SCREEN_PATH = "/views/fxml/cart.fxml";
	public static final String VIEW_ORDER_SCREEN_PATH = "/views/fxml/view_order.fxml";
	public static final String SHIPPING_SCREEN_PATH = "/views/fxml/shipping.fxml";
	public static final String CART_MEDIA_PATH = "/views/fxml/media_cart.fxml";
	public static final String HOME_PATH  = "/views/fxml/home.fxml";
	public static final String HOME_MEDIA_PATH = "/views/fxml/media_home.fxml";
	public static final String POPUP_PATH = "/views/fxml/popup.fxml";
	public static final String LOGIN = "/views/fxml/login.fxml";
	public static final String REGISTER = "/views/fxml/register.fxml";

	public static final String CREATE_USER_POPUP_PATH = "/views/fxml/create_user_popup.fxml";

	public static final String CHOOSE_TYPE_CREATE_PATH = "/views/fxml/choose_create_media.fxml";
	public static final String SELLER_OR_USER_PATH = "/views/fxml/seller_or_user.fxml";
	public static final String CREATE_COMMON_MEDIA_PATH = "/views/fxml/common_media_info.fxml";
	public static final String SELLER_ITEM_VIEW_PATH = "/views/fxml/media_item.fxml";
	public static final String SELLER_CU_BOOK_VIEW_PATH = "/views/fxml/media_book.fxml";
	public static final String SELLER_CU_CD_VIEW_PATH = "/views/fxml/media_cd.fxml";
	public static final String SELLER_CU_DVD_VIEW_PATH = "/views/fxml/media_dvd.fxml";
	public static final String SELLER_UPDATE_BOOK_PATH = "/views/fxml/update_book.fxml";
	public static final String SELLER_UPDATE_CD_PATH = "/views/fxml/update_cd.fxml";
	public static final String SELLER_UPDATE_DVD_PATH = "/views/fxml/update_dvd.fxml";
	public static final String REFUND_RESULT_PATH = "/views/fxml/refund_result.fxml";
	public static final String ORDER_DETAIL_ADMIN_PATH = "/views/fxml/detail_order_admin.fxml";
	public static final String ITEM_ORDER_PATH = "/views/fxml/item_order.fxml";
	public static final String EDIT_USER_POPUP_PATH = "/views/fxml/edit_user_popup.fxml";
	public static final String ADMIN_PATH = "/views/fxml/admin.fxml";
	//	FONT
	public static Font REGULAR_FONT = Font.font("Segoe UI", FontWeight.NORMAL, FontPosture.REGULAR, 24);

	public static final String ORDER_MANAGEMENT_ADMIN_PATH = "/views/fxml/order_management_admin.fxml";
	
	public static String[] PROVINCES = { "Bắc Giang", "Bắc Kạn", "Cao Bằng", "Hà Giang", "Lạng Sơn", "Phú Thọ",
			"Quảng Ninh", "Thái Nguyên", "Tuyên Quang", "Yên Bái", "Điện Biên", "Hòa Bình", "Lai Châu", "Sơn La",
			"Bắc Ninh", "Hà Nam", "Hải Dương", "Hưng Yên", "Nam Định", "Ninh Bình", "Thái Bình", "Vĩnh Phúc", "Hà Nội",
			"Hải Phòng", "Hà Tĩnh", "Nghệ An", "Quảng Bình", "Quảng Trị", "Thanh Hóa", "Thừa Thiên-Huế", "Đắk Lắk",
			"Đắk Nông", "Gia Lai", "Kon Tum", "Lâm Đồng", "Bình Định", "Bình Thuận", "Khánh Hòa", "Ninh Thuận",
			"Phú Yên", "Quảng Nam", "Quảng Ngãi", "Đà Nẵng", "Bà Rịa-Vũng Tàu", "Bình Dương", "Bình Phước", "Đồng Nai",
			"Tây Ninh", "Hồ Chí Minh", "An Giang", "Bạc Liêu", "Bến Tre", "Cà Mau", "Đồng Tháp", "Hậu Giang",
			"Kiên Giang", "Long An", "Sóc Trăng", "Tiền Giang", "Trà Vinh", "Vĩnh Long", "Cần Thơ" };

}
