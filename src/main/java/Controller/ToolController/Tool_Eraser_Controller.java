package Controller.ToolController;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollBar;

public class Tool_Eraser_Controller extends BaseToolController {
	
	@FXML
	private ScrollBar eraserBar;
	
	public Tool_Eraser_Controller() {
		
		
		
		
	}

	
	
	
	private void initView() {
		painter.setEraserWidth(eraserBar.getValue());
		eraserBar.valueProperty().addListener((observable, oldValue, newValue) -> painter.setEraserWidth(newValue.doubleValue()));
		
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initView();
		
	}
	
	
}
