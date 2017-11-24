package Controller;

import Manager.Painter;
import Tool.Tool;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class BaseController {

	
	
	protected final Painter painter;


	protected BaseController() {
		this.painter=Painter.GetInstance();
	}
	
	/**
	 * load fxml
	 * @param path
	 * @return
	 */
	Parent loadFxml(String path) {
		
		return Tool.loadFxml(path,this);
		
	}
	
	
	/**
	 * create window
	 * @param stage
	 * @param root
	 * @param title
	 */
	void createWindow(Stage stage, Parent root, String title) {
		Scene scene = new Scene(root);
		stage.setTitle(title);
		stage.setScene(scene);
		stage.show();
	}
}
