package views.screen.home;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import common.exception.ViewCartException;
//import common.exception.ViewCartException;
import controller.CartController;
import controller.HomeController;
//import controller.CartController;
import entity.cart.Cart;
import entity.media.Media;
import entity.user.User;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import services.user.LoginManager;
import utils.Configs;
import views.screen.BaseScreenHandler;
//import views.screen.ChooseRoleScreenHandler;
//import views.screen.cart.CartScreenHandler;
//import views.screen.order.OrderScreenHandler;
import views.screen.cart.CartScreenHandler;
import views.screen.auth.LoginHandler;


public class HomeScreenHandler extends BaseScreenHandler implements Initializable{

    public static Logger LOGGER = utils.LOGGER.getLogger(HomeScreenHandler.class.getName());

    //search

    @FXML
    private SplitMenuButton sort;

    @FXML
    private TextField searchText;

    @FXML
    private Label numMediaInCart;

    @FXML
    private ImageView aimsImage;

    @FXML
    private ImageView cartImage;

    @FXML
    private ImageView orderIcon;

    @FXML
    private VBox vboxMedia1;

    @FXML
    private VBox vboxMedia2;

    @FXML
    private VBox vboxMedia3;

    @FXML
    private HBox hboxMedia;

    @FXML
    private Label sign_out;

    @FXML
    private SplitMenuButton splitMenuBtnSearch;

    private List homeItems;
    private User loggedInUser;
    HomeController homeController = new HomeController();
    public HomeScreenHandler(Stage stage, String screenPath) throws IOException{
        super(stage, screenPath);
        sign_out.setOnMouseClicked(mouseEvent -> {

            try {
                LoginManager loginManager = new LoginManager();
                LoginManager.clearSavedLoginInfo();
                LoginHandler loginHandler = new LoginHandler(this.stage, Configs.LOGIN);
                loginHandler.setScreenTitle("Login");
//				loginHandler.setImage();
                loginHandler.show();
//            	Stage stage1 = (Stage) searchText.getScene().getWindow();
//    			Parent root = FXMLLoader.load(getClass().getResource(Configs.LOGIN));
//    			stage1.setScene(new Scene(root));
//    			stage1.setTitle("Login");
            } catch (IOException e) {
                e.printStackTrace();
            }

        });
    }

    public HomeScreenHandler(Stage stage, String screenPath,User loggedInUser) throws IOException{
        super(stage, screenPath);
        this.loggedInUser = loggedInUser;
        sign_out.setOnMouseClicked(mouseEvent -> {
           
            try {
            	LoginManager loginManager = new LoginManager();
            	LoginManager.clearSavedLoginInfo();
            	LoginHandler loginHandler = new LoginHandler(this.stage, Configs.LOGIN);
				loginHandler.setScreenTitle("Login");
				loginHandler.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
           
        });
    }

    public Label getNumMediaCartLabel(){
        return this.numMediaInCart;
    }

    public HomeController getBController() {
        return (HomeController) super.getBController();
    }

    @Override
    public void show() {
        User user = LoginManager.getSavedLoginInfo();
        numMediaInCart.setText(Cart.getCart(user.getId()).getTotalMedia() + " media");
        super.show();
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        setBController(new HomeController());
        try{
            List medium = getBController().getAllMedia();
            this.homeItems = new ArrayList<>();
            for (Object object : medium) {
                Media media = (Media)object;
                MediaHandler m1 = new MediaHandler(Configs.HOME_MEDIA_PATH, media, this);
                this.homeItems.add(m1);
            }
        }catch (SQLException | IOException e){
            LOGGER.info("Errors occured: " + e.getMessage());
            e.printStackTrace();
        }


        this.splitMenuBtnSearch.setOnMouseClicked(e ->{
            try {
                List medium = this.getBController().search(this.searchText.getText());
                this.homeItems = new ArrayList<>();
                for (Object object : medium) {
                    Media media = (Media)object;
                    MediaHandler m1 = new MediaHandler(Configs.HOME_MEDIA_PATH, media, this);
                    this.homeItems.add(m1);
                }
            } catch (SQLException | IOException e1) {
                e1.printStackTrace();
            }
            this.addMediaHome(homeItems);
        });

        aimsImage.setOnMouseClicked(e -> {
            addMediaHome(this.homeItems);
        });

        cartImage.setOnMouseClicked(e -> {
            CartScreenHandler cartScreen;
            try {
                LOGGER.info("User clicked to view cart");
                cartScreen = new CartScreenHandler(this.stage, Configs.CART_SCREEN_PATH, this.loggedInUser);
                cartScreen.setHomeScreenHandler(this);
                cartScreen.setBController(new CartController());
                cartScreen.requestToViewCart(this);
            } catch (IOException | SQLException e1) {
                throw new ViewCartException(Arrays.toString(e1.getStackTrace()).replaceAll(", ", "\n"));
            }
        });

        addMediaHome(this.homeItems);
        addMenuItem(0, "Book", splitMenuBtnSearch);
        addMenuItem(1, "DVD", splitMenuBtnSearch);
        addMenuItem(2, "CD", splitMenuBtnSearch);

        //search
        addMenuItemSearch(0, "Title", sort);
        addMenuItemSearch(1, "Price", sort);
    }

    public void setImage(){
        // fix image path caused by fxml
        File file1 = new File(Configs.IMAGE_PATH + "/" + "Logo.png");
        Image img1 = new Image(file1.toURI().toString());
        aimsImage.setImage(img1);

        File file2 = new File(Configs.IMAGE_PATH + "/" + "cart.png");
        Image img2 = new Image(file2.toURI().toString());
        cartImage.setImage(img2);

        File file3 = new File(Configs.IMAGE_PATH + "/" + "orderIcon.png");
        Image img3 = new Image(file3.toURI().toString());
        orderIcon.setImage(img3);
    }

    public void addMediaHome(List items){
        ArrayList mediaItems = (ArrayList)((ArrayList) items).clone();
        hboxMedia.getChildren().forEach(node -> {
            VBox vBox = (VBox) node;
            vBox.getChildren().clear();
        });
        while(!mediaItems.isEmpty()){
            hboxMedia.getChildren().forEach(node -> {
                int vid = hboxMedia.getChildren().indexOf(node);
                VBox vBox = (VBox) node;
                while(vBox.getChildren().size()<3 && !mediaItems.isEmpty()){
                    MediaHandler media = (MediaHandler) mediaItems.get(0);
                    vBox.getChildren().add(media.getContent());
                    mediaItems.remove(media);
                }
            });
            return;
        }
    }

    private void addMenuItemSearch(int position, String text, MenuButton menuButton) {

        MenuItem menuItem = new MenuItem();
        Label label = new Label();
        label.prefWidthProperty().bind(menuButton.widthProperty().subtract(31));
        label.setText(text);
        label.setTextAlignment(TextAlignment.RIGHT);
        menuItem.setGraphic(label);
        menuItem.setOnAction( e ->{
            hboxMedia.getChildren().forEach(node -> {
                VBox vBox = (VBox) node;
                vBox.getChildren().clear();
            });

            if(text.contains("Title")) {
                this.getBController().sortTitle(homeItems);
            }
            else if(text.contains("Price")) {
                this.getBController().sortPrice(homeItems);
            }
            this.addMediaHome(homeItems);
        });
        menuButton.getItems().add(position, menuItem);
    }

    private void addMenuItem(int position, String text, MenuButton menuButton){
        MenuItem menuItem = new MenuItem();
        Label label = new Label();
        label.prefWidthProperty().bind(menuButton.widthProperty().subtract(31));
        label.setText(text);
        label.setTextAlignment(TextAlignment.RIGHT);
        menuItem.setGraphic(label);
        menuItem.setOnAction(e -> {
            // empty home media
            hboxMedia.getChildren().forEach(node -> {
                VBox vBox = (VBox) node;
                vBox.getChildren().clear();
            });

            // filter only media with the choosen category
            List filteredItems = new ArrayList<>();
            homeItems.forEach(me -> {
                MediaHandler media = (MediaHandler) me;
                if (media.getMedia().getTitle().toLowerCase().startsWith(text.toLowerCase())){
                    filteredItems.add(media);
                }
            });

            // fill out the home with filted media as category
            addMediaHome(filteredItems);
        });
        menuButton.getItems().add(position, menuItem);
    }
}
