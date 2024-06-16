package views.screen.sellerScreen.sellerEventScreen.create;

import controller.SellerHomeController;
import db.AIMSDB;
import entity.media.Book;
import entity.media.CD;
import entity.media.DVD;
import entity.media.Media;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import utils.Utils;
import views.screen.BaseScreenHandler;
import views.screen.popup.PopupScreen;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class CommonInfoCreateHandler extends BaseScreenHandler implements Initializable {
	
	private static final Logger LOGGER = Utils.getLogger(CommonInfoCreateHandler.class.getName());

	@FXML
	private TextField title;
	
	@FXML
	private TextField value;

	@FXML
	private TextField rushDelivery;

	@FXML
	private TextField price;
	
	@FXML
	private TextField weight;

	@FXML
	private Spinner<Integer> quantity;
	
	@FXML
	private Button save;
	
	private final String type;
	private final String category;
	private final String imageUrl;
	private String createQuery;
	private final Media mediaItem;
	SellerHomeController sellerHomeController;

	public CommonInfoCreateHandler(Stage stage, String screenPath, String type, Media media, String category, String imageUrl) throws IOException {
		super(stage, screenPath);
		// TODO Auto-generated constructor
		sellerHomeController = new SellerHomeController();
		this.type = type;
		this.mediaItem = media;
		this.category = category;
		this.imageUrl = imageUrl;
		this.createQuery = createQuery;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		quantity.setValueFactory(
				new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 1)
				);

		save.setOnMouseClicked(event -> {
			if (checkFillInformation()) {
				Statement stm;
				try {
					Media media = new Media(
							0, // Assuming ID is auto-generated by the database
							title.getText(),
							this.category,
							Integer.parseInt(price.getText()),
							Integer.parseInt(value.getText()),
							quantity.getValue(),
							this.type,
							this.imageUrl,
							Integer.parseInt(rushDelivery.getText())
					);
					int rowInsertedId = sellerHomeController.createMedia(media);
					mediaItem.setId(rowInsertedId);
					if(this.type == "BOOK"){
						sellerHomeController.createBook((Book)mediaItem);
					}else if (this.type == "CD"){
						sellerHomeController.createCD((CD)mediaItem);
					}else {
						sellerHomeController.createDVD((DVD)mediaItem);
					}
					PopupScreen.success("Create success");
					this.stage.close();
				} catch (SQLException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				try {
					PopupScreen.error("Something is not right???");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}
	
	public boolean checkFillInformation() {
		String titleText = title.getText();
		String valueText = value.getText();
		String priceText = price.getText();
		String quantityText = quantity.getValue().toString();
		return titleText.length() > 0 &&
				priceText.length() > 0 && 
				valueText.length() > 0 && 
				Integer.parseInt(value.getText()) * 0.3 <= Integer.parseInt(price.getText()) && 
				Integer.parseInt(value.getText()) * 1.5 >= Integer.parseInt(price.getText()) &&
				quantityText.length() > 0 && quantity.getValue() > 0;
	}

	public String createMediaQuery() throws SQLException {
		String queryValues = "(" +
				"'" + title.getText() + "'" + ", " +
				"'" + this.category + "'" + ", " +
				Integer.parseInt(price.getText()) + ", " +
				quantity.getValue() + ", " +
				"'" + type + "'" + ", " +
				"'" + Integer.parseInt(value.getText()) + "'" + ", " +
				"'" + this.imageUrl + "'" + ", " +
				"'" + Integer.parseInt(rushDelivery.getText()) + "'" + ")";
		String sql = "INSERT INTO Media "
				+ "(title, category, price, quantity, type, value, imageUrl, support_for_rush_delivery)"
				+ " VALUES "
				+ queryValues + ";";
//		LOGGER.info("Errors occured: " + sql);
		return sql;
	}
}
