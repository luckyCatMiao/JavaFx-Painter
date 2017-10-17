package Controller;

import Paint.Config;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollBar;

public class Tool_Eraser_Controller extends ToolController {
	
	@FXML
	private ScrollBar eraserBar;
	
	public Tool_Eraser_Controller() {
		root=loadFxml(Config.TOOL_FXML_ERASER);
		
		initView();
		
	}

	private void initView() {
		eraserBar.valueProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				painter.setEraserWidth(newValue.doubleValue());
			}
		});
		
	}
	
	
}
