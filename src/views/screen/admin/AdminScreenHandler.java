package views.screen.admin;

import controller.AdminController;
import entity.user.User;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import services.user.LoginManager;
import utils.AdminUtils;
import utils.Configs;
import utils.Utils;
import views.screen.BaseScreenHandler;
import views.screen.auth.LoginHandler;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class AdminScreenHandler extends BaseScreenHandler implements Initializable {

    public static Logger LOGGER = Utils.getLogger(AdminScreenHandler.class.getName());

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
    private Label sign_out;

    @FXML
    private SplitMenuButton splitMenuBtnSearch;

    @FXML
    private TableView<User> userTable;

    @FXML
    private TableColumn<User, Integer> id;
    @FXML
    private TableColumn<User, String> usernameColumn;

    @FXML
    private TableColumn<User, String> emailColumn;

    @FXML
    private TableColumn<User, String> addressColumn;

    @FXML
    private TableColumn<User, String> phoneColumn;

    @FXML
    private TableColumn<User, String> typeColumn;
    @FXML
    private TableColumn<User, String> statusColumn;

    @FXML
    private Button createUserButton;

    @FXML
    private Button viewUserInfoButton;

    @FXML
    private Button updateUserInfoButton;

    @FXML
    private Button deleteUserButton;

    @FXML
    private Button resetPasswordButton;

    @FXML
    private Button blockUserButton;

    @FXML
    private Button unblockUserButton;

    @FXML
    private ComboBox<?> userComboBox;

    @FXML
    private CheckBox adminRoleCheckBox;

    @FXML
    private CheckBox productManagerRoleCheckBox;

    @FXML
    private Button setRolesButton;

    @FXML
    private Button changePasswordButton;

    @FXML
    private Button logoutButton;

    private AdminController adminController;


    public AdminScreenHandler(Stage stage, String screenPath) throws IOException{
        super(stage, screenPath);

    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.adminController = new AdminController();
        initializeButtons();
        initializeTable();
        setupClickHandlerForEachRow();
    }

    private void initializeButtons() {
        createUserButton.setOnAction(event -> createUser());
        sign_out.setOnMouseClicked(event -> logout());
    }

    private void initializeTable() {
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        typeColumn.setCellValueFactory(cellData -> {
            User user = cellData.getValue();
//            System.out.println(user.getUserType());
            String userTypeString = AdminUtils.getUserTypeString(user.getUserType());
//            System.out.println(userTypeString);
            return new SimpleStringProperty(userTypeString);
        });
        statusColumn.setCellValueFactory(cellData -> {
            User user = cellData.getValue();
            String userTypeString = AdminUtils.getUserStatusString(user.getStatus());
            return new SimpleStringProperty(userTypeString);
        });
        try {
            List<User> userList = adminController.getAllUsers();
            ObservableList<User> users = FXCollections.observableArrayList(userList);
            userTable.setItems(users);
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exception
        }
    }


    private void createUser() {
        try {
            Stage popupStage = new Stage();
            CreateUserPopupScreen createUserPopup = new CreateUserPopupScreen(popupStage,this);
            createUserPopup.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setupClickHandlerForEachRow() {
        userTable.setRowFactory(tableView -> {
            TableRow<User> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    try {
                        User user = row.getItem();
                        Stage popupStage = new Stage();
                        EditUserPopupScreen editUserPopup = new EditUserPopupScreen(popupStage, user,this);
                        editUserPopup.show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            return row;
        });
    }

    private void logout() {
        try {
            LoginManager loginManager = new LoginManager();
            LoginManager.clearSavedLoginInfo();
            LoginHandler loginHandler = new LoginHandler(this.stage, Configs.LOGIN);
            loginHandler.setScreenTitle("Login");
            loginHandler.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void reloadPage() {
        try {
            ObservableList<User> users = FXCollections.observableArrayList(adminController.getAllUsers());
            userTable.setItems(users);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
