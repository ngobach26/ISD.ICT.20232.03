package views.screen.sellerScreen.sellerEventScreen.update;

import controller.SellerController;
import entity.media.DVD;
import entity.media.Media;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import utils.SellerUtils;
import views.screen.BaseScreenHandler;
import views.screen.popup.PopupScreen;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class DVDUpdateHandler extends BaseScreenHandler implements Initializable {

    public static Logger LOGGER = utils.LOGGER.getLogger(DVDUpdateHandler.class.getName());

    @FXML
    private TextField director;

    @FXML
    private TextField runtime;

    @FXML
    private TextField studio;
    @FXML
    private TextField title;
    @FXML
    private TextField price;
    @FXML
    private TextField value;

    @FXML
    private TextField subtitles;

    @FXML
    private ComboBox<String> discType;

    @FXML
    private ComboBox<String> filmType;

    @FXML
    private DatePicker releaseDate;

    @FXML
    private Spinner<Integer> quantity;

    @FXML
    private Button update;

    @FXML
    private ComboBox<String> image_url;

    private final Media media;

    SellerController sellerController;
    DVD targetMedia;

    public DVDUpdateHandler(Stage stage, String screenPath, Media media) throws IOException, SQLException {
        super(stage, screenPath);
        sellerController = new SellerController();
        this.media = media;
        setMediaInfo();
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        quantity.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 1)
        );

        // Initialize ComboBoxes and Image URLs
        initializeComboBoxes();

        update.setOnMouseClicked(event -> {
            if (checkFillInformation()) {
                try {
                    updateMediaInformation(targetMedia);
                    updateDVD();
                    PopupScreen.success("DVD updated successfully!");
                    this.stage.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    private void initializeComboBoxes() {
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
    }

    public void setMediaInfo() throws SQLException {
        LOGGER.info("Id of the media: " + this.media);
        targetMedia = sellerController.getDVDById(media.getId());
        director.setText(targetMedia.getDirector());
        runtime.setText(Integer.toString(targetMedia.getRuntime()));
        studio.setText(targetMedia.getStudio());
        subtitles.setText(targetMedia.getSubtitles());
        discType.setValue(targetMedia.getDiscType());
        filmType.setValue(targetMedia.getFilmType());
        releaseDate.setValue(LOCAL_DATE(targetMedia.getReleasedDate().toString()));
        quantity.getValueFactory().setValue(targetMedia.getQuantity());
        image_url.setValue(targetMedia.getImageURL());
        title.setText(targetMedia.getTitle());
        value.setText("" + targetMedia.getValue());
        price.setText("" + targetMedia.getPrice());
        quantity.getValueFactory().setValue(targetMedia.getQuantity());
    }

    public LocalDate LOCAL_DATE(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(dateString, formatter);
    }

    public boolean checkFillInformation() {
        String directorText = director.getText();
        String runtimeText = runtime.getText();
        String studioText = studio.getText();
        String subtitlesText = subtitles.getText();
        String discTypeText = discType.getValue();
        String filmTypeText = filmType.getValue();
        String releaseDateText = releaseDate.getValue().toString();
        int quantityText = quantity.getValue();
        return directorText != null && !directorText.isEmpty() &&
                runtimeText != null && !runtimeText.isEmpty() &&
                studioText != null && !studioText.isEmpty() &&
                subtitlesText != null && !subtitlesText.isEmpty() &&
                discTypeText != null && !discTypeText.isEmpty() &&
                filmTypeText != null && !filmTypeText.isEmpty() &&
                releaseDateText != null && !releaseDateText.isEmpty() &&
                quantityText > 0;
    }

    public void updateMediaInformation(DVD media) {
        media.setDirector(director.getText());
        media.setStudio(studio.getText());
        media.setSubtitles(subtitles.getText());
        media.setDiscType(discType.getValue());
        media.setFilmType(filmType.getValue());
        media.setReleasedDate(SellerUtils.converToDate(releaseDate.getValue()));
        media.setQuantity(quantity.getValue());
        media.setImageURL(image_url.getValue());
        media.setTitle(title.getText());
        media.setValue(Integer.parseInt(value.getText()));
        media.setPrice(Integer.parseInt(price.getText()));
        media.setQuantity(quantity.getValue());
    }

    public void updateDVD() throws SQLException {
        sellerController.updateMedia(targetMedia);
        sellerController.updateDVD(targetMedia);
    }

}
