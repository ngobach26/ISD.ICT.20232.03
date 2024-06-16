package views.sellerScreen.sellerEventScreen.create;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import entity.media.LPRecord;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import utils.Configs;
import views.screen.BaseScreenHandler;

public class LPRecordCreateHandler extends BaseScreenHandler implements Initializable {

	@FXML
	private ComboBox<String> genre;

	@FXML
	private TextField artist;

	@FXML
	private TextField recordLabel;

	@FXML
	private DatePicker releasedDate;
	
	@FXML
	private Button create;
	
	@FXML
	private ComboBox<String> image_url;
	
	CommonInfoCreateHandler commonInfoCreateHandler;

	public LPRecordCreateHandler(Stage stage, String screenPath) throws IOException {
		super(stage, screenPath);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
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
		
		create.setOnMouseClicked(event -> {
			if (checkFillInformation()) {
				try {
					commonInfoCreateHandler = new CommonInfoCreateHandler(this.stage, Configs.CREATE_COMMON_MEDIA_PATH, "CD", createLPRecordQuery(), genre.getValue(), image_url.getValue());
					commonInfoCreateHandler.setScreenTitle("Common information for Records");
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
		String genreText = genre.getValue();
		String imageUrl = image_url.getValue();
		String artistText = artist.getText();
		String recordLabelText = recordLabel.getText();
		String releasedDateText = releasedDate.getValue().toString();
		return genreText.length() > 0 && 
				imageUrl.length() > 0 &&
				artistText.length() > 0 &&
				recordLabelText.length() > 0 &&
				releasedDateText.length() > 0;
	}

	public String createLPRecordQuery() throws SQLException {
		LPRecord lpRecordEntity = new LPRecord();
		String createSql = lpRecordEntity.createLPRecordQuery(artist.getText(), recordLabel.getText(), genre.getValue(), releasedDate.getValue().toString());
		return createSql;
	}
}
