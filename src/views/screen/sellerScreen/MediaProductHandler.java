package views.screen.sellerScreen;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;
import java.util.logging.Logger;

import controller.SellerController;
import entity.media.Media;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import utils.Configs;
import views.screen.FXMLScreenHandler;
import views.screen.popup.PopupScreen;
import views.screen.sellerScreen.sellerEventScreen.update.BookUpdateHandler;
import views.screen.sellerScreen.sellerEventScreen.update.CDUpdateHandler;
import views.screen.sellerScreen.sellerEventScreen.update.DVDUpdateHandler;

// This class is used for handle the administrators/seller action 
// to each media product in the AdminHome page
public class MediaProductHandler extends FXMLScreenHandler {
	
	public static Logger LOGGER = utils.LOGGER.getLogger(MediaProductHandler.class.getName());
	// media product's Image
	@FXML
	protected ImageView item_img;
	// media product's name
	@FXML
	protected Text item_name;
	// media product's quantity
	@FXML
	protected Text item_quantity;
	// media product's type
	@FXML
	protected Text item_type;
	// media product's Delete button
	@FXML
	protected Text item_price;
	
	@FXML
	protected Button delete_btn;
	// media product's Change button
	@FXML
	protected Button update_btn;

	private final Media media;
	private final int id;
	private BookUpdateHandler bookUpdateHandler;
	private CDUpdateHandler cdUpdateHandler;
	private DVDUpdateHandler dvdUpdateHandler;
	private Media mediaHandler;
	private SellerController sellerController;

	public MediaProductHandler(String screenPath, Media media) throws IOException, SQLException {
		super(screenPath);
		// TODO Auto-generated constructor stub
		sellerController = new SellerController();
		this.media = media;
		this.id = media.getId();
		setMediaInfo();
	}

	// handle display Media item (with adding event to those buttons)
	private void setMediaInfo() throws SQLException {
		// set the cover image of media
		File file = new File(media.getImageURL());
		Image image = new Image(file.toURI().toString());
		item_img.setFitHeight(160);
		item_img.setFitWidth(152);
		item_img.setImage(image);
		
		item_name.setText(media.getTitle());
		item_quantity.setText(Integer.toString(media.getQuantity()));
		item_type.setText(media.getTitle());
		item_price.setText("" + media.getPrice());

		update_btn.setOnMouseClicked(event -> {
			if (this.media.getType().equals("BOOK")) {
				try {
					bookUpdateHandler = new BookUpdateHandler(new Stage(), Configs.SELLER_UPDATE_BOOK_PATH, this.media);
					bookUpdateHandler.setScreenTitle("Book Update Dialog");
					bookUpdateHandler.show();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else if (this.media.getType().equals("CD")) {
				try {
					cdUpdateHandler = new CDUpdateHandler(new Stage(), Configs.SELLER_UPDATE_CD_PATH, this.media);
					cdUpdateHandler.setScreenTitle("CD Update Dialog");
					cdUpdateHandler.show();
				} catch (IOException | SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				try {
					dvdUpdateHandler = new DVDUpdateHandler(new Stage(), Configs.SELLER_UPDATE_DVD_PATH, this.media);
					dvdUpdateHandler.setScreenTitle("DD Update Dialog");
					dvdUpdateHandler.show();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (SQLException e) {
					e.printStackTrace();
				}

			}
		});

		delete_btn.setOnMouseClicked(event -> {
			try {
				sellerController.deleteMediaById(this.id);
				if (Objects.equals(this.media.getType(), "BOOK")) {
					sellerController.deleteBookById(this.media.getId());
					// mediaHandler.deleteMediaFieldById(this.media.getId());
				} else if (Objects.equals(this.media.getType(), "CD")) {
					sellerController.deleteCDById(this.media.getId());
					// mediaHandler.deleteMediaFieldById(this.media.getId());
				} else {
					sellerController.deleteDVDById(this.media.getId());
					// mediaHandler.deleteMediaFieldById(this.media.getId());
				}
				PopupScreen.success("Delete success");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
	}
}
