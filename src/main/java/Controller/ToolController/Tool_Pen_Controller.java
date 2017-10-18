package Controller.ToolController;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollBar;
import javafx.scene.shape.StrokeLineCap;

public class Tool_Pen_Controller extends BaseToolController{

	

	@FXML ScrollBar penWidthSB;
	@FXML ComboBox<String> capCB;
	@FXML ScrollBar spaceSB;

	public Tool_Pen_Controller() {
		

	}
	

	protected void initView() {
		penWidthSB.valueProperty().addListener((ChangeListener<Number>) (observable, oldValue, newValue) -> painter.setLineWidth(newValue.doubleValue()));
		capCB.getItems().addAll("无","圆","方");
		capCB.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				StrokeLineCap value = null;
				switch (newValue) {
				case "无":
					value=StrokeLineCap.BUTT;
					break;
				case "圆":
					value=StrokeLineCap.ROUND;
					break;
				case "方":
					value=StrokeLineCap.SQUARE;
					break;
					

				default:
					break;
				}
				painter.setStrokeLineCap(value);
				
			}
		});
		
		capCB.getSelectionModel().selectFirst();

		
		spaceSB.valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				if(newValue.doubleValue()!=0)
				{
					painter.setLineSpace(newValue.doubleValue());
				}
			}
		});
	}
	

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initView();
		
	}
	
	
}
