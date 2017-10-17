package Cell;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeCell;
import javafx.scene.layout.HBox;

public class PreferenceTreeCell extends TreeCell<String> {

	@Override
	protected void updateItem(String item, boolean empty) {
		// TODO Auto-generated method stub
		super.updateItem(item, empty);
		
		setGraphic(null);
		setText(null);
		if(item!=null)
		{
			if(isEditing())
			{
				
			}
			else
			{
				HBox box=new HBox();
				box.setPadding(new Insets(10));
				Label label=new Label(item+":");
				
				TextField textField=new TextField();
				textField.setMaxWidth(150);
				String TFValue="";
				switch (item) {
				case "宽度":
					
					break;
				case "长度":
					
					break;
				default:
					break;
				}
				
				box.getChildren().addAll(label,textField);

				setGraphic(box);
			}
			
		}
		
	}
}
