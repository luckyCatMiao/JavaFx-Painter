package Controller;

import Cell.PreferenceTreeCell;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

class PopUpWindow_Preference extends BaseController{
	
	@FXML
	private TreeView<String> treeView;
	
	
	public PopUpWindow_Preference(Stage owner) {
		
		
		Stage stage=new Stage();
		stage.setResizable(false);
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.initOwner(owner);

		
		Parent root=loadFxml("PopUpWindow_Preference");
		createWindow(stage, root, "Preference");
		initView();
	}

	private void initView() {
		initTree();
		
	}

	private void initTree() {
	 	TreeItem<String> rootItem = new TreeItem<> ("默认窗口大小");
        rootItem.setExpanded(true);
        TreeItem<String> rootItem2 = new TreeItem<> ("宽度");
        TreeItem<String> rootItem3 = new TreeItem<> ("长度");
        rootItem.getChildren().addAll(rootItem2,rootItem3);
        
        
        TreeItem<String> rootItem4 = new TreeItem<> ("路径");
        rootItem4.setExpanded(true);
        TreeItem<String> rootItem5 = new TreeItem<> ("保存路径");
        TreeItem<String> rootItem6 = new TreeItem<> ("元数据路径");
        rootItem4.getChildren().addAll(rootItem5,rootItem6);
      
        TreeItem<String> rootTreeItem=new TreeItem<>();
        rootTreeItem.getChildren().addAll(rootItem,rootItem4);
        
       
        
       treeView.setRoot(rootTreeItem); 
       treeView.setShowRoot(false);    

	   treeView.setEditable(true);
	   

	   treeView.setCellFactory(param -> {
           // TODO Auto-generated method stub
           return new PreferenceTreeCell();
       });
		
	}

}
