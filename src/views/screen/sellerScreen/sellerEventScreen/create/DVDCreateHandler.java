package views.screen.sellerScreen.sellerEventScreen.create;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;

import entity.media.CD;
import entity.media.DVD;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import utils.Configs;
import views.screen.BaseScreenHandler;

public class DVDCreateHandler extends BaseScreenHandler implements Initializable {

	@FXML
	private ComboBox<String> discType;

	@FXML
	private TextField director;
	
	@FXML
	private TextField studio;
	
	@FXML
	private TextField runtime;
	
	@FXML
	private TextField subtitles;
	
	@FXML
	private DatePicker releasedDate;
	
	@FXML
	private ComboBox<String> filmType;
	
	@FXML
	private Button create;
	
	@FXML
	private ComboBox<String> image_url;
	
	CommonInfoCreateHandler commonInfoCreateHandler;

	public DVDCreateHandler(Stage stage, String screenPath) throws IOException {
		super(stage, screenPath);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
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
				"assets/images/book/dvd1.jpg",
				"assets/images/book/dvd2.jpg",
				"assets/images/book/dvd3.jpg",
				"assets/images/book/dvd4.jpg",
				"assets/images/book/dvd5.jpg",
				"assets/images/book/dvd6.jpg",
				"assets/images/book/dvd7.jpg",
				"assets/images/book/dvd8.jpg",
				"assets/images/book/dvd9.jpg",
				"assets/images/book/dvd10.jpg",
				"assets/images/book/dvd11.jpg",
				"assets/images/book/dvd12.jpg"
			);
		
		create.setOnMouseClicked(event -> {
			if (checkFillInformation()) {
				try {
					LocalDate dateToConvert = releasedDate.getValue();
					Date date = Date.from(dateToConvert.atStartOfDay(ZoneId.systemDefault()).toInstant());
					DVD newDVD = new DVD(discType.getValue(), director.getText(), Integer.parseInt(runtime.getText()), studio.getText(), subtitles.getText(), date, filmType.getValue());
					commonInfoCreateHandler = new CommonInfoCreateHandler(this.stage, Configs.CREATE_COMMON_MEDIA_PATH, "DVD", newDVD, filmType.getValue(), image_url.getValue());
					commonInfoCreateHandler.setScreenTitle("Common information for DVD");
					commonInfoCreateHandler.show();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}
	
	public boolean checkFillInformation() {
		String discTypeText = discType.getValue();
		String imageUrl = image_url.getValue();
		String directorText = director.getText();
		String studioText = studio.getText();
		String runtimeText = runtime.getText();
		String subtitlesText = subtitles.getText();
		String releasedDateText = releasedDate.getValue().toString();
		String filmTypeText = filmType.getValue();
		return discTypeText.length() > 0 && 
				imageUrl.length() > 0 &&
				directorText.length() > 0 &&
				studioText.length() > 0 && 
				runtimeText.length() > 0 && 
				subtitlesText.length() > 0 && 
				releasedDateText.length() > 0 && 
				filmTypeText.length() > 0;
	}
}
