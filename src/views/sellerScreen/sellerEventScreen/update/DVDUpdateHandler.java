package views.sellerScreen.sellerEventScreen.update;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import db.AIMSDB;
import entity.media.DVD;
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
import views.sellerScreen.sellerEventScreen.create.CommonInfoCreateHandler;

public class DVDUpdateHandler extends BaseScreenHandler implements Initializable {
	
	public static Logger LOGGER = Utils.getLogger(DVDUpdateHandler.class.getName());

	@FXML
	private ComboBox<String> discType;

	@FXML
	private TextField director;

	@FXML
	private TextField runtime;

	@FXML
	private TextField studio;
	
	@FXML
	private TextField language;
	
	@FXML
	private TextField subtitle;

	@FXML
	private DatePicker releasedDate;

	@FXML
	private ComboBox<String> filmType;
	
	@FXML
	private Button update;
	
	@FXML
	private TextField title;

	@FXML
	private TextField value;

	@FXML
	private TextField price;

	@FXML
	private TextField weight;

	@FXML
	private Spinner<Integer> quantity;

	
	@FXML
	private ComboBox<String> image_url;
	
	private CommonInfoCreateHandler commonInfoCreateHandler;
	private Media media;

	public DVDUpdateHandler(Stage stage, String screenPath, Media media) throws IOException, SQLException {
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
		
		discType.getItems().addAll(
				"Hard",
				"Soft",
				"USB"
			);
		
		filmType.getItems().addAll(
				"Horror",
				"Science fiction",
				"Western",
				"Action",
				"Drama",
				"Comedy",
				"Thriller",
				"Romance"
			);
		
		image_url.getItems().addAll(
				"assets/images/dvd/dvd1.jpg",
				"assets/images/dvd/dvd2.jpg",
				"assets/images/dvd/dvd3.jpg",
				"assets/images/dvd/dvd4.jpg",
				"assets/images/dvd/dvd5.jpg",
				"assets/images/dvd/dvd6.jpg",
				"assets/images/dvd/dvd7.jpg",
				"assets/images/dvd/dvd8.jpg",
				"assets/images/dvd/dvd9.jpg",
				"assets/images/dvd/dvd10.jpg",
				"assets/images/dvd/dvd11.jpg",
				"assets/images/dvd/dvd12.jpg"
			);
		
		update.setOnMouseClicked(event -> {
			if (checkFillInformation()) {
				try {
					updateDVDQuery();
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
		DVD targetMedia = (DVD) new DVD().getMediaById(media.getId());
		discType.setValue(targetMedia.getDiscType());
		director.setText(targetMedia.getDirector());
		runtime.setText("" + targetMedia.getRuntime());
		studio.setText(targetMedia.getStudio());
		language.setText(targetMedia.getLanguage());
		subtitle.setText(targetMedia.getSubtitle());
		releasedDate.setValue(LOCAL_DATE(targetMedia.getReleasedDate().toString()));
		filmType.setValue(targetMedia.getFilmType());
		title.setText(targetMedia.getTitle());
		value.setText("" + targetMedia.getValue());
		price.setText("" + targetMedia.getPrice());
		weight.setText("" + targetMedia.getWeight());
		quantity.getValueFactory().setValue(targetMedia.getQuantity());
		image_url.setValue(targetMedia.getImageURL());
	}

	public LocalDate LOCAL_DATE (String dateString){
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate localDate = LocalDate.parse(dateString, formatter);
		return localDate;
	}
	
	public boolean checkFillInformation() {
		String discTypeText = discType.getValue();
		String directorText = director.getText();
		String runtimeText = runtime.getText();
		String studioText = studio.getText();
		String languageText = language.getText();
		String subtitleText = subtitle.getText();
		String releasedDateText = releasedDate.getValue().toString();
		String filmTypeText = filmType.getValue();
		String imageUrl = image_url.getValue();
		return discTypeText.length() > 0 && 
				directorText.length() > 0 &&
				imageUrl.length() > 0 &&
				runtimeText.length() > 0 && 
				studioText.length() > 0 && 
				releasedDateText.length() > 0 && 
				subtitleText.length() > 0 && 
				filmTypeText.length() > 0 &&
				languageText.length() > 0;
	}
	
	public void updateDVDQuery() throws SQLException {
		String dvdSQL = "UPDATE DVD "
				+ "SET "
				+ "discType='" + discType.getValue() + "',"
				+ "director='" + director.getText() + "',"
				+ "runtime='" + runtime.getText() + "',"
				+ "studio='" + studio.getText() + "',"
				+ "language='" + language.getText() + "',"
				+ "subtitle='" + subtitle.getText() + "',"
				+ "releasedDate='" + releasedDate.getValue().toString() + "',"
				+ "filmType='" + filmType.getValue() + "',"				
				+ " WHERE "
				+ "id = " + this.media.getId() + ";";

		LOGGER.info("" + dvdSQL);

		String mediaSQL = "UPDATE Media "
				+ "SET "
				+ "title='" + title.getText() + "',"
				+ "price='" + price.getText() + "',"
				+ "value='" + value.getText() + "',"
				+ "quantity=" + quantity.getValue() + ","
				+ "weight='" + weight.getText() + "',"
				+ "imageURL='" + image_url.getValue() + "'"
				+ " WHERE "
				+ "id = " + this.media.getId() + ";";

		LOGGER.info("" + mediaSQL);
		Statement stm = AIMSDB.getConnection().createStatement();
		stm.executeUpdate(dvdSQL);
		stm.executeUpdate(mediaSQL);
	}
}