package views.screen.sellerScreen.sellerEventScreen.create;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;

import entity.media.CD;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import utils.Configs;
import views.screen.BaseScreenHandler;

public class CDCreateHandler extends BaseScreenHandler implements Initializable {

	@FXML
	private ComboBox<String> musicType;

	@FXML
	private TextField artist;

	@FXML
	private TextField recordLabel;

	@FXML
	private DatePicker releaseDate;
	
	@FXML
	private Button create;
	
	@FXML
	private ComboBox<String> image_url;
	
	CommonInfoCreateHandler commonInfoCreateHandler;

	public CDCreateHandler(Stage stage, String screenPath) throws IOException {
		super(stage, screenPath);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		musicType.getItems().addAll(
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
				"assets/images/book/cd1.jpg",
				"assets/images/book/cd2.jpg",
				"assets/images/book/cd3.jpg",
				"assets/images/book/cd4.jpg",
				"assets/images/book/cd5.jpg",
				"assets/images/book/cd6.jpg",
				"assets/images/book/cd7.jpg",
				"assets/images/book/cd8.jpg",
				"assets/images/book/cd9.jpg",
				"assets/images/book/cd10.jpg",
				"assets/images/book/cd11.jpg",
				"assets/images/book/cd12.jpg"
			);
		
		create.setOnMouseClicked(event -> {
			if (checkFillInformation()) {
				try {
					LocalDate dateToConvert = releaseDate.getValue();
					Date date = Date.from(dateToConvert.atStartOfDay(ZoneId.systemDefault()).toInstant());
					CD newCD = new CD(artist.getText(), recordLabel.getText(), musicType.getValue(), date);
					commonInfoCreateHandler = new CommonInfoCreateHandler(this.stage, Configs.CREATE_COMMON_MEDIA_PATH, "CD", newCD, musicType.getValue(), image_url.getValue());
					commonInfoCreateHandler.setScreenTitle("Common information for CD");
					commonInfoCreateHandler.show();
				} catch (IOException e) {
					// TODO Auto-generated catch blockS
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}
	
	public boolean checkFillInformation() {
		String musicTypeText = musicType.getValue();
		String imageUrl = image_url.getValue();
		String artistText = artist.getText();
		String recordLabelText = recordLabel.getText();
		String releaseDateText = releaseDate.getValue().toString();
		return musicTypeText.length() > 0 && 
				imageUrl.length() > 0 &&
				artistText.length() > 0 &&
				recordLabelText.length() > 0 &&
				releaseDateText.length() > 0;
	}

}
