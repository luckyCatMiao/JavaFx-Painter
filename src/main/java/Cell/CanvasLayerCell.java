package Cell;



import Bean.CanvasLayer;
import Controller.PaintController;
import Tool.Tool;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;

public class CanvasLayerCell extends ListCell<CanvasLayer> {

	@FXML
	private Label canvasName;
	@FXML
	private TextField layerNameTF;
	@FXML
	private Button renameBT;
	private final PaintController paintController;
	
	public CanvasLayerCell(PaintController paintController) {
		this.paintController = paintController;
		// TODO Auto-generated constructor stub
	}


	@Override
	protected void updateItem(CanvasLayer item, boolean empty) {
		super.updateItem(item, empty);
		if(item!=null)
		{
			if(isEditing())
			{
				Parent parent=Tool.loadFxml("Item_Layer_Edit", this);
				layerNameTF.setText(item.getName());
				renameBT.setOnAction(event -> {
                    getItem().setName(layerNameTF.getText());
                    commitEdit(getItem());

                });
				setGraphic(parent);
				
			}
			else
			{
				Parent parent=Tool.loadFxml("Item_Layer", this);
				canvasName.setText(item.getName());
				setGraphic(parent);
			}
			
		}
		else
		{
			setGraphic(null);
			setText(null);
		}
	}
	
	
	@FXML
	public void deleteLayer()
	{
		//remove canvas from painter
		paintController.deleteLayer(getItem());
		
		
	}
	
	@Override
	public void startEdit() {
		// TODO Auto-generated method stub
		super.startEdit();
		updateItem(getItem(), isEmpty());
	}
}
