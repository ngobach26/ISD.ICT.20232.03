package views.sellerScreen.sellerEventScreen.update;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import entity.media.Media;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import utils.Configs;
import views.screen.BaseScreenHandler;
import views.sellerScreen.sellerEventScreen.create.CommonInfoCreateHandler;

public class CDUpdateHandler extends BaseScreenHandler implements Initializable {

	@FXML
	private ComboBox<String> category;

	@FXML
	private TextField author;

	@FXML
	private TextField cover_type;

	@FXML
	private TextField publisher;

	@FXML
	private DatePicker publish_date;

	@FXML
	private TextField number_pages;

	@FXML
	private TextField language;
	
	@FXML
	private Button create;
	
	@FXML
	private ComboBox<String> image_url;
	
	CommonInfoCreateHandler commonInfoCreateHandler;

	public CDUpdateHandler(Stage stage, String screenPath, Media media) throws IOException {
		super(stage, screenPath);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		category.getItems().addAll(
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
				"assets/images/book/book1.jpg",
				"assets/images/book/book2.jpg",
				"assets/images/book/book3.jpg",
				"assets/images/book/book4.jpg",
				"assets/images/book/book5.jpg",
				"assets/images/book/book6.jpg",
				"assets/images/book/book7.jpg",
				"assets/images/book/book8.jpg",
				"assets/images/book/book9.jpg",
				"assets/images/book/book10.jpg",
				"assets/images/book/book11.jpg",
				"assets/images/book/book12.jpg"
			);
		
		create.setOnMouseClicked(event -> {
			if (checkFillInformation()) {
				try {
					commonInfoCreateHandler = new CommonInfoCreateHandler(this.stage, Configs.CREATE_COMMON_MEDIA_PATH, "BOOK", createBookQuery(), category.getValue(), image_url.getValue());
					commonInfoCreateHandler.setScreenTitle("Common information for Book");
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
		String comboBoxText = category.getValue();
		String authorText = author.getText();
		String coverTypeText = cover_type.getText();
		String publisherText = publisher.getText();
		String publishDateText = publish_date.getValue().toString();
		String numOfPages = number_pages.getText();
		String languageText = language.getText();
		String imageUrl = image_url.getValue();
		return comboBoxText.length() > 0 && 
				authorText.length() > 0 &&
				imageUrl.length() > 0 &&
				coverTypeText.length() > 0 && 
				publisherText.length() > 0 && 
				publishDateText.length() > 0 && 
				numOfPages.length() > 0 && 
				languageText.length() > 0;
	}
	
	public String createBookQuery() throws SQLException {
		String queryValues = "(" +
				"'" + author.getText() + "'" + ", " +
				"'" + cover_type.getText() + "'" + ", " +
				"'" + publisher.getText() + "'" + ", " +
				"'" + publish_date.getValue().toString() + "'" + ", " +
				number_pages.getText() + ", " +
				"'" + language.getText() + "'" + ", " +
				"'" + category.getValue() + "'" + ")";
		String sql = "INSERT INTO Book " 
				+ "(author, coverType, publisher, publishDate, numOfPages, language, bookCategory)"
				+ " VALUES "
				+ queryValues + ";";
		return sql;
	}
}
