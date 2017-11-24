package Controller;

import java.net.URL;
import java.util.ResourceBundle;

import Paint.Config;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ColorPicker;
import javafx.scene.paint.Color;

public class ColorChooserController extends BaseController implements Initializable{

	@FXML ColorPicker strokeColorPicker;
	@FXML ColorPicker fillColorPicker;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		fillColorPicker.valueProperty().addListener((observable, oldValue, newValue) -> painter.setFill(newValue));
		strokeColorPicker.valueProperty().addListener((ChangeListener<Color>) (observable, oldValue, newValue) -> painter.setStroke(newValue));
	
		//set init color
		fillColorPicker.setValue(Config.INIT_COLOR_FILL);
		strokeColorPicker.setValue(Config.INIT_COLOR_STROKE);
		
	
	}
	

}
