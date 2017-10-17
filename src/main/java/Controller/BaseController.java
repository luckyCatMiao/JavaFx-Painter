package Controller;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import Manager.Painter;
import Paint.Config;
import Tool.Tool;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class BaseController {

	
	
	protected Painter painter;


	public BaseController() {
		this.painter=Painter.GetInstance();
	}
	
	/**
	 * load fxml
	 * @param path
	 * @return
	 */
	protected Parent loadFxml(String path) {
		
		return Tool.loadFxml(path,this);
		
	}
	
	
	/**
	 * create window
	 * @param stage
	 * @param root
	 * @param title
	 */
	protected void createWindow(Stage stage,Parent root,String title) {
		Scene scene = new Scene(root);
		stage.setTitle(title);
		stage.setScene(scene);
		stage.show();
	}
}
