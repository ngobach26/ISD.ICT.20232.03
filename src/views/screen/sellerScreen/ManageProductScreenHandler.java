package views.screen.sellerScreen;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import controller.SellerController;
import entity.media.Media;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import services.user.LoginManager;
import utils.Configs;
import views.screen.BaseScreenHandler;
import views.screen.auth.LoginHandler;
import views.screen.sellerScreen.sellerEventScreen.CreateMediaByType;

public class ManageProductScreenHandler extends BaseScreenHandler implements Initializable {

	public static Logger LOGGER = utils.LOGGER.getLogger(ManageProductScreenHandler.class.getName());

	@FXML
	private ScrollPane scroll_pane;

	@FXML
	private VBox vboxes;

	@FXML
	private Button add_btn;

	@FXML
	private Button reset;

	@FXML
	private ImageView order_img;

	@FXML
	private Button exit_btn;

	@FXML
	private Label sign_out;

	private CreateMediaByType createMediaByType;
	private List<?> allTheMedia;

	public SellerController getBController() {
		return (SellerController) super.getBController();
	}

	public ManageProductScreenHandler(Stage stage, String screenPath) throws IOException {
		super(stage, screenPath);
	}

	@Override
	public void show() {
		super.show();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		setBController(new SellerController());

		add_btn.setOnMouseClicked(event -> {
			try {
				createMediaByType = new CreateMediaByType(new Stage(), Configs.CHOOSE_TYPE_CREATE_PATH);
				createMediaByType.setScreenTitle("Choose Media Type");
				createMediaByType.show();
			} catch (IOException e) {
				LOGGER.log(Level.SEVERE, "IOException occurred while creating media by type: ", e);
			}
		});

		reset.setOnMouseClicked(event -> {
			try {
				this.vboxes.getChildren().clear();
				this.allTheMedia = getBController().getAllMedia();
				List<?> convertMediaList = convertMediaToRenderItem(allTheMedia);
				renderMediaToScreen(convertMediaList);
			} catch (SQLException e) {
				LOGGER.log(Level.SEVERE, "SQLException occurred while resetting media list: ", e);
			}
		});

		sign_out.setOnMouseClicked(mouseEvent -> {
			try {
				LoginManager loginManager = new LoginManager();
				LoginManager.clearSavedLoginInfo();
				LoginHandler loginHandler = new LoginHandler(this.stage, Configs.LOGIN);
				loginHandler.setScreenTitle("Login");
				loginHandler.show();
			} catch (IOException e) {
				LOGGER.log(Level.SEVERE, "IOException occurred while signing out: ", e);
			}
		});

		try {
			this.vboxes.getChildren().clear();
			this.allTheMedia = getBController().getAllMedia();
			List<?> convertMediaList = convertMediaToRenderItem(allTheMedia);
			renderMediaToScreen(convertMediaList);
		} catch (Error | SQLException e) {
			LOGGER.log(Level.SEVERE, "Error or SQLException occurred while initializing media list: ", e);
		}
	}

	public List<MediaProductHandler> convertMediaToRenderItem(List<?> MediaList) throws SQLException {
		List<MediaProductHandler> renderList = new ArrayList<>();
		try {
			for (Object item : MediaList) {
				MediaProductHandler mediaProductHandler = new MediaProductHandler(Configs.SELLER_ITEM_VIEW_PATH, (Media) item);
				renderList.add(mediaProductHandler);
			}
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, "IOException occurred while converting media to render item: ", e);
		}
		return renderList;
	}

	public void renderMediaToScreen(List<?> MediaList) {
		if (MediaList.size() == 0) return;
		int numberOfLastRow = MediaList.size() % 3;
		int numberOfRows = (numberOfLastRow > 0) ? (int) Math.floor(MediaList.size() / 3) + 1 : (int) Math.floor(MediaList.size() / 3);

		for (int rowIndex = 0; rowIndex < numberOfRows; rowIndex++) {
			HBox hbox = new HBox();
			if (rowIndex == numberOfRows - 1 && numberOfLastRow > 0) {
				for (int index = 0; index < numberOfLastRow; index++) {
					hbox.getChildren().add(((MediaProductHandler) MediaList.get(rowIndex * 3 + index)).getContent());
				}
			} else {
				for (int index = 0; index < 3; index++) {
					hbox.getChildren().add(((MediaProductHandler) MediaList.get(rowIndex * 3 + index)).getContent());
				}
			}
			this.vboxes.getChildren().add(hbox);
		}
	}
}
