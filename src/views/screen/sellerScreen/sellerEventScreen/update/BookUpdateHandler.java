package views.screen.sellerScreen.sellerEventScreen.update;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import controller.SellerController;
import entity.media.Book;
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
import utils.SellerUtils;
import utils.Utils;
import views.screen.BaseScreenHandler;
import views.screen.popup.PopupScreen;
import views.screen.sellerScreen.sellerEventScreen.create.CommonInfoCreateHandler;

public class BookUpdateHandler extends BaseScreenHandler implements Initializable {

	public static Logger LOGGER = Utils.getLogger(BookUpdateHandler.class.getName());

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
	private Button create;

	@FXML
	private ComboBox<String> image_url;

	private CommonInfoCreateHandler commonInfoCreateHandler;
	private final Media media;

	SellerController sellerController;
	Book targetMedia;

	public BookUpdateHandler(Stage stage, String screenPath, Media media) throws IOException, SQLException {
		super(stage, screenPath);
		// TODO Auto-generated constructor stub
		sellerController = new SellerController();
		this.media = media;
		setMediaInfo();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		quantity.setValueFactory(
				new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 1)
		);

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
					updateMediaInformation(targetMedia);
					updateBook();
					PopupScreen.success("Book updated successfully!");
					this.stage.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
		});
	}



	public void setMediaInfo() throws SQLException {
		LOGGER.info("Id of the media: " + this.media);
		targetMedia = sellerController.getBookById(media.getId());
		category.setValue(targetMedia.getCategory());
		author.setText(targetMedia.getAuthor());
		cover_type.setText(targetMedia.getCoverType());
		publisher.setText(targetMedia.getPublisher());
		publish_date.setValue(LOCAL_DATE(targetMedia.getPublishDate().toString()));
		number_pages.setText("" + targetMedia.getNumOfPages());
		language.setText(targetMedia.getLanguage());
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
		String comboBoxText = category.getValue();
		String authorText = author.getText();
		String coverTypeText = cover_type.getText();
		String publisherText = publisher.getText();
		String publishDateText = publish_date.getValue().toString();
		String numOfPages = number_pages.getText();
		String languageText = language.getText();
		String imageUrl = image_url.getValue();
		String titleText = title.getText();
		String valueText = value.getText();
		String priceText = price.getText();
		int quantityText = quantity.getValue();
		return comboBoxText.length() > 0 &&
				authorText.length() > 0 &&
				imageUrl.length() > 0 &&
				coverTypeText.length() > 0 &&
				publisherText.length() > 0 &&
				publishDateText.length() > 0 &&
				numOfPages.length() > 0 &&
				languageText.length() > 0 &&
				titleText.length() > 0 &&
				valueText.length() > 0 &&
				priceText.length() > 0 &&
				quantityText > 0;
	}

	public void updateMediaInformation(Book media) {
		media.setCategory(category.getValue());
		media.setAuthor(author.getText());
		media.setCoverType(cover_type.getText());
		media.setPublisher(publisher.getText());
		media.setPublishDate(SellerUtils.converToDate(publish_date.getValue()));
		media.setNumOfPages(Integer.parseInt(number_pages.getText()));
		media.setLanguage(language.getText());
		media.setImageURL(image_url.getValue());
		media.setTitle(title.getText());
		media.setValue(Integer.parseInt(value.getText()));
		media.setPrice(Integer.parseInt(price.getText()));
		media.setQuantity(quantity.getValue());
	}



	public void updateBook() throws SQLException {
		sellerController.updateMedia(targetMedia);
		sellerController.updateBook((targetMedia));
	}
}
