package Controller;

import Paint.Config;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ColorPicker;

public class Tool_Bucket_Controller extends ToolController {

	@FXML
	private ColorPicker bucketColorPicker;
	public Tool_Bucket_Controller() {
		root=loadFxml(Config.TOOL_FXML_BUCKET);
		
		initView();
	}
	private void initView() {
		bucketColorPicker.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				painter.setFill(bucketColorPicker.getValue());
				
			}
		});
		
	}

	
}
