package views.screen.sellerScreen.sellerEventScreen.update;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import controller.SellerHomeController;
import entity.media.CD;
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

public class CDUpdateHandler extends BaseScreenHandler implements Initializable {

    public static Logger LOGGER = Utils.getLogger(CDUpdateHandler.class.getName());

    @FXML
    private TextField artist;

    @FXML
    private TextField recordLabel;
    @FXML
    private TextField title;
    @FXML
    private TextField price;
    @FXML
    private TextField value;

    @FXML
    private ComboBox<String> musicType;

    @FXML
    private DatePicker releasedDate;

    @FXML
    private Spinner<Integer> quantity;

    @FXML
    private Button update;

    @FXML
    private ComboBox<String> image_url;

    private final Media media;

    SellerHomeController sellerHomeController;
    CD targetMedia;

    public CDUpdateHandler(Stage stage, String screenPath, Media media) throws IOException, SQLException {
        super(stage, screenPath);
        sellerHomeController = new SellerHomeController();
        this.media = media;
        setMediaInfo();
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        quantity.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 1)
        );
        musicType.getItems().addAll(
                "Classical",
                "Rock",
                "Pop",
                "Jazz",
                "Hip Hop",
                "Electronic",
                "Country",
                "Blues"
        );

        image_url.getItems().addAll(
                "assets/images/cd/cd1.jpg",
                "assets/images/cd/cd2.jpg",
                "assets/images/cd/cd3.jpg",
                "assets/images/cd/cd4.jpg",
                "assets/images/cd/cd5.jpg",
                "assets/images/cd/cd6.jpg",
                "assets/images/cd/cd7.jpg",
                "assets/images/cd/cd8.jpg",
                "assets/images/cd/cd9.jpg",
                "assets/images/cd/cd10.jpg",
                "assets/images/cd/cd11.jpg",
                "assets/images/cd/cd12.jpg"
        );

        update.setOnMouseClicked(event -> {
            if (checkFillInformation()) {
                try {
                    updateMediaInformation(targetMedia);
                    updateCD();
                    PopupScreen.success("CD updated successfully!");
                    this.stage.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    public void setMediaInfo() throws SQLException {
        LOGGER.info("Id of the media: " + this.media);
        targetMedia = sellerHomeController.getCDById(media.getId());
        artist.setText(targetMedia.getArtist());
        recordLabel.setText(targetMedia.getRecordLabel());
        musicType.setValue(targetMedia.getMusicType());
        releasedDate.setValue(LOCAL_DATE(targetMedia.getReleasedDate().toString()));
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
        String artistText = artist.getText();
        String recordLabelValue = recordLabel.getText();
        String musicTypeText = musicType.getValue();
        String releasedDateText = releasedDate.getValue().toString();
        int quantityText = quantity.getValue();
        return artistText != null && !artistText.isEmpty() &&
                recordLabelValue != null && !recordLabelValue.isEmpty() &&
                musicTypeText != null && !musicTypeText.isEmpty() &&
                releasedDateText != null && !releasedDateText.isEmpty() &&
                quantityText > 0;
    }

    public void updateMediaInformation(CD media) {

        media.setArtist(artist.getText());
        media.setRecordLabel(recordLabel.getText());
        media.setMusicType(musicType.getValue());
        media.setReleasedDate(SellerUtils.converToDate(releasedDate.getValue()));
        media.setQuantity(quantity.getValue());
        media.setImageURL(image_url.getValue());
        media.setTitle(title.getText());
        media.setValue(Integer.parseInt(value.getText()));
        media.setPrice(Integer.parseInt(price.getText()));
        media.setQuantity(quantity.getValue());
    }

    public void updateCD() throws SQLException {
        sellerHomeController.updateMedia(targetMedia);
        sellerHomeController.updateCD(targetMedia);
    }

}
