package views.sellerScreen.sellerEventScreen;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import utils.Configs;
import views.screen.BaseScreenHandler;
import views.sellerScreen.sellerEventScreen.create.BookCreateHandler;
import views.sellerScreen.sellerEventScreen.create.CDCreateHandler;
import views.sellerScreen.sellerEventScreen.create.DVDCreateHandler;
import views.sellerScreen.sellerEventScreen.create.LPRecordCreateHandler;

public class CreateMediaByType extends BaseScreenHandler implements Initializable {

	public CreateMediaByType(Stage stage, String screenPath) throws IOException {
		super(stage, screenPath);
		// TODO Auto-generated constructor stub
	}

	@FXML
	private RadioButton book;

	@FXML
	private RadioButton cd;

	@FXML
	private RadioButton dvd;
	
	@FXML
	private RadioButton record;

	@FXML
	private Button create;

	@Override
	public void show() {
		super.show();
	}
	
	BookCreateHandler createBookItemHandler;
	CDCreateHandler createCDItemHandler;
	DVDCreateHandler createDVDItemHandler;
	LPRecordCreateHandler createLPRecordItemHandler;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		final ToggleGroup group = new ToggleGroup();
		// add radio buttons into a group
		book.setToggleGroup(group);
		cd.setToggleGroup(group);
		dvd.setToggleGroup(group);
		record.setToggleGroup(group);
		
		book.setSelected(true);

		create.setOnMouseClicked(event -> {
			if (book.isSelected()) {
				try {
					createBookItemHandler = new BookCreateHandler(this.stage, Configs.SELLER_CU_BOOK_VIEW_PATH);
					createBookItemHandler.setScreenTitle("Book Media Screen");
					createBookItemHandler.show();
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			} else if (cd.isSelected()) {
				try {
					createCDItemHandler = new CDCreateHandler(this.stage, Configs.SELLER_CU_CD_VIEW_PATH);
					createCDItemHandler.setScreenTitle("CD Media Screen");
					createCDItemHandler.show();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else if (dvd.isSelected()){
				try {
					createDVDItemHandler = new DVDCreateHandler(this.stage, Configs.SELLER_CU_DVD_VIEW_PATH);
					createDVDItemHandler.setScreenTitle("DVD Media Screen");
					createDVDItemHandler.show();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				try {
					createLPRecordItemHandler = new LPRecordCreateHandler(this.stage, Configs.SELLER_CU_LPRECORD_VIEW_PATH);
					createLPRecordItemHandler.setScreenTitle("Record Media Screen");
					createLPRecordItemHandler.show();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}
}
