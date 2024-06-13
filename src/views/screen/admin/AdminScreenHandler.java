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
//        viewUserInfoButton.setOnAction(event -> viewUserInfo());
//        updateUserInfoButton.setOnAction(event -> updateUserInfo());
//        deleteUserButton.setOnAction(event -> deleteUser());
//        resetPasswordButton.setOnAction(event -> resetPassword());
//        blockUserButton.setOnAction(event -> blockUser());
//        unblockUserButton.setOnAction(event -> unblockUser());
//        setRolesButton.setOnAction(event -> setRoles());
//        changePasswordButton.setOnAction(event -> changePassword());
//        logoutButton.setOnAction(event -> logout());
//
        sign_out.setOnMouseClicked(event -> logout());
//
//        aimsImage.setOnMouseClicked(event -> reloadPage());
//        cartImage.setOnMouseClicked(event -> viewCart());
//        orderIcon.setOnMouseClicked(event -> viewOrders());
//
//        splitMenuBtnSearch.setOnMouseClicked(event -> search());
//        sort.setOnMouseClicked(event -> sort());
    }

    private void initializeTable() {
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        typeColumn.setCellValueFactory(cellData -> {
            User user = (User) cellData.getValue();
//            System.out.println(user.getUserType());
            String userTypeString = getUserTypeString(user.getUserType());
//            System.out.println(userTypeString);
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

    private String getUserTypeString(int userType) {
        switch (userType) {
            case 0:
                return "User";
            case 1:
                return "Manager";
            case 2:
                return "Admin";
            default:
                return "Unknown";
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

    private void viewUserInfo() {
        // Logic to view user information
    }

    private void updateUserInfo() {
        // Logic to update user information
    }

    private void deleteUser() {
        // Logic to delete a user
    }

    private void resetPassword() {
        // Logic to reset password
    }

    private void blockUser() {
        // Logic to block a user
    }

    private void unblockUser() {
        // Logic to unblock a user
    }

    private void setRoles() {
        // Logic to set or change user roles
    }

    private void changePassword() {
        // Logic to change the password
    }

    private void logout() {
        try {
            LoginManager loginManager = new LoginManager();
            loginManager.clearSavedLoginInfo();
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

    private void viewCart() {
        // Logic to view the cart
    }

    private void viewOrders() {
        // Logic to view orders
    }

    private void search() {
        // Logic to search users
    }

    private void sort() {
        // Logic to sort users
    }
}
