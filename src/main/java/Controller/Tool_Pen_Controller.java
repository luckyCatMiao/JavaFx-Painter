package Controller;

import Paint.Config;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ScrollBar;
import javafx.scene.paint.Color;

public class Tool_Pen_Controller extends ToolController {

	@FXML
	private ColorPicker colorPicker;
	@FXML
	private ScrollBar penWidthSB;
	@FXML
	private ScrollBar alphaSB;

	public Tool_Pen_Controller() {
		root=loadFxml(Config.TOOL_FXML_PEN);
		colorPicker.setValue(Color.BLACK);
		
		initView();
	}
	

	protected void initView() {
		penWidthSB.valueProperty().addListener((ChangeListener<Number>) (observable, oldValue, newValue) -> painter.setLineWidth(newValue.doubleValue()));
		alphaSB.valueProperty().addListener((ChangeListener<Number>) (observable, oldValue, newValue) -> painter.setAlpha(newValue.doubleValue()));
	}
	
	@FXML
	public void colorPickAction(ActionEvent event)
	{
		ColorPicker colorPicker=(ColorPicker) event.getSource();
		painter.setStroke(colorPicker.getValue());

	}
	
	
}
