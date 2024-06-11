package views.screen.admin;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import controller.AdminController;
import entity.user.User;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import utils.Utils;
import views.screen.BaseScreenHandler;
import views.screen.popup.CreateUserPopupScreen;

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
    private TableView<?> userTable;

    @FXML
    private TableColumn<?, ?> usernameColumn;

    @FXML
    private TableColumn<?, ?> emailColumn;

    @FXML
    private TableColumn<?, ?> rolesColumn;

    @FXML
    private TableColumn<?, ?> statusColumn;

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
        initializeButtons();
//        initializeTable();
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
//        sign_out.setOnMouseClicked(event -> logout());
//
//        aimsImage.setOnMouseClicked(event -> reloadPage());
//        cartImage.setOnMouseClicked(event -> viewCart());
//        orderIcon.setOnMouseClicked(event -> viewOrders());
//
//        splitMenuBtnSearch.setOnMouseClicked(event -> search());
//        sort.setOnMouseClicked(event -> sort());
    }

    private void initializeTable() {
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        rolesColumn.setCellValueFactory(new PropertyValueFactory<>("roles"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        try {
            List<User> users = adminController.getAllUsers();
            userTable.getItems().addAll(users);
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exception
        }
    }

    private void createUser() {
        try {
            Stage popupStage = new Stage();
            CreateUserPopupScreen createUserPopup = new CreateUserPopupScreen(popupStage);
            createUserPopup.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        // Logic to logout
    }

    private void reloadPage() {
        // Logic to reload the page
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
