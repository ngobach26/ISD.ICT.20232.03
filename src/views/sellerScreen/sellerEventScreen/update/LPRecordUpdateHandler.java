package views.sellerScreen.sellerEventScreen.update;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import db.AIMSDB;
import entity.media.LPRecord;
import entity.media.Media;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import utils.Configs;
import utils.Utils;
import views.screen.BaseScreenHandler;
import views.sellerScreen.MediaProductHandler;
import views.sellerScreen.sellerEventScreen.create.CommonInfoCreateHandler;

public class LPRecordUpdateHandler extends BaseScreenHandler implements Initializable {
	
	public static Logger LOGGER = Utils.getLogger(LPRecordUpdateHandler.class.getName());

	@FXML
	private ComboBox<String> genre;

	@FXML
	private TextField artist;

	@FXML
	private TextField recordLabel;

	@FXML
	private DatePicker releasedDate;
	
	@FXML
	private TextField title;

	@FXML
	private TextField value;

	@FXML
	private TextField price;

	@FXML
	private Spinner<Integer> quantity;
	
	@FXML 
	private Button update;
	
	@FXML
	private ComboBox<String> image_url;
	
	private CommonInfoCreateHandler commonInfoCreateHandler;
	private Media media;

	public LPRecordUpdateHandler(Stage stage, String screenPath, Media media) throws IOException, SQLException {
		super(stage, screenPath);
		// TODO Auto-generated constructor stub
		this.media = media;
		setMediaInfo();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		quantity.setValueFactory(
				new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 1)
		);

		genre.getItems().addAll(
				"Pop",
				"Rhythm and blues",
				"Rock",
				"Jazz",
				"Electronic",
				"Country",
				"Blues",
				"Funk"
			);

		image_url.getItems().addAll(
				"assets/images/LPRecord/LPR1.jpg",
				"assets/images/LPRecord/LPR2.jpg",
				"assets/images/LPRecord/LPR3.jpg",
				"assets/images/LPRecord/LPR4.jpg",
				"assets/images/LPRecord/LPR5.jpg",
				"assets/images/LPRecord/LPR6.jpg",
				"assets/images/LPRecord/LPR7.jpg",
				"assets/images/LPRecord/LPR8.jpg",
				"assets/images/LPRecord/LPR9.jpg",
				"assets/images/LPRecord/LPR10.jpg",
				"assets/images/LPRecord/LPR11.jpg",
				"assets/images/LPRecord/LPR12.jpg"
			);

		update.setOnMouseClicked(event -> {
			if (checkFillInformation()) {
				try {
					updateLPRecordQuery();
					this.stage.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}

	public void setMediaInfo() throws SQLException {
		LOGGER.info("Id of the media: " + this.media);
		LPRecord targetMedia = (LPRecord) new LPRecord().getMediaById(media.getId());
		genre.setValue(targetMedia.getGenre());
		artist.setText(targetMedia.getArtist());
		recordLabel.setText(targetMedia.getRecordLabel());
		releasedDate.setValue(LOCAL_DATE(targetMedia.getReleasedDate().toString()));
		title.setText(targetMedia.getTitle());
		value.setText("" + targetMedia.getValue());
		price.setText("" + targetMedia.getPrice());
		quantity.getValueFactory().setValue(targetMedia.getQuantity());
		image_url.setValue(targetMedia.getImageURL());
	}

	public LocalDate LOCAL_DATE (String dateString){
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate localDate = LocalDate.parse(dateString, formatter);
		return localDate;
	}

	public boolean checkFillInformation() {
		String genreText = genre.getValue();
		String artistText = artist.getText();
		String recordLabelText = recordLabel.getText();
		String releasedDateText = releasedDate.getValue().toString();
		String imageUrl = image_url.getValue();
		String titleText = title.getText();
		String valueText = value.getText();
		String priceText = price.getText();
		int quantityText = quantity.getValue();
		return genreText.length() > 0 &&
				artistText.length() > 0 &&
				imageUrl.length() > 0 &&
				recordLabelText.length() > 0 &&
				releasedDateText.length() > 0 &&
				titleText.length() > 0 &&
				valueText.length() > 0 &&
				priceText.length() > 0 &&
				quantityText > 0;
	}

	public void updateLPRecordQuery() throws SQLException {
		String lpRecordSQL = "UPDATE LPRecord "
				+ "SET "
				+ "genre='" + genre.getValue() + "',"
				+ "artist='" + artist.getText() + "',"
				+ "recordLabel='" + recordLabel.getText() + "',"
				+ "releasedDate='" + releasedDate.getValue().toString() + "',"
				+ " WHERE "
				+ "id = " + this.media.getId() + ";";

		LOGGER.info("" + lpRecordSQL);

		String mediaSQL = "UPDATE Media "
				+ "SET "
				+ "title='" + title.getText() + "',"
				+ "price='" + price.getText() + "',"
				+ "value='" + value.getText() + "',"
				+ "quantity=" + quantity.getValue() + ","
				+ "imageURL='" + image_url.getValue() + "'"
				+ " WHERE "
				+ "id = " + this.media.getId() + ";";

		LOGGER.info("" + mediaSQL);
		Statement stm = AIMSDB.getConnection().createStatement();
		stm.executeUpdate(lpRecordSQL);
		stm.executeUpdate(mediaSQL);
	}
}
