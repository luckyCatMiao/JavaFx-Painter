package Controller.ToolController;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ScrollBar;

public class Tool_Rect_Controller extends BaseToolController {

	@FXML ToggleButton ssRB;
	@FXML ScrollBar YJSB;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		painter.setNeedFill(ssRB.isSelected());
		
		YJSB.valueProperty().addListener((observable, oldValue, newValue) -> painter.setArc(newValue.doubleValue()));

	}

	

	@FXML public void ssRBPress(ActionEvent event) {
		
		painter.setNeedFill(ssRB.isSelected());
	}

}
