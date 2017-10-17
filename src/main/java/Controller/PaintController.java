package Controller;


import Bean.CanvasLayer;
import Bean.ToolIcon;
import Cell.CanvasLayerCell;
import Manager.PaintType;
import Manager.ToolManager;
import Tool.Tool;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Control;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class PaintController extends BaseController{

	
	@FXML
	private FlowPane flowPane;
	@FXML
	private ListView<CanvasLayer> canvasLV; 
	@FXML
	private StackPane stackPane;
	@FXML
	private Group toolSetGroup;
	private Stage stage;
	private Parent root;
	
	public PaintController(Stage primaryStage) {
		stage = primaryStage;
		this.root=loadFxml("Main");
		initStage();		
		createWindow(primaryStage, root, "Paint");
		initView();
		initDrapAndDrop();

	}

	private void initStage() {
		stage.setFullScreen(true);
		stage.setFullScreenExitHint("按G键切换全屏模式");
		stage.addEventFilter(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				
				if(event.getCode().equals(KeyCode.G))
				{
					if(stage.fullScreenProperty().get()==true)
					{
						
						stage.setFullScreen(false);
					}
					else
					{
						stage.setFullScreen(true);
					}
				}
				event.consume();
				
			}
		});
		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {

			@Override
			public void handle(WindowEvent event) {
				//deal with unsaved change
				
			}
		});
		
	
	
	}

	private void initDrapAndDrop() {
			stage.getScene().setOnDragOver((DragEvent event) -> {
			Dragboard db = event.getDragboard();
			if(db.hasUrl())
			{			
			event.acceptTransferModes(TransferMode.LINK);
			} else {
			event.consume();
			}
			});
		
			
			stage.getScene().setOnDragDropped((DragEvent event) -> {
				Dragboard db = event.getDragboard();
				if(db.hasUrl())
				{
					addImageToNewCanvasByUrl(db.getUrl());
				
				} else {
				event.consume();
				}
				});
	}

	/**
	 * add a outer image to the new canvas
	 * @param url
	 */
	private void addImageToNewCanvasByUrl(String url) {
		createNewLayer();
		Image image=new Image(url);
		painter.drawImage(image);
		
		
	}

	private void initView() {
		initLayerList();
		initCanvas();
		initToolsPane();
		initStackPane();
		initContextMenu();
	}

	private void initContextMenu() {
		//create contextmenu of canvas
		ContextMenu contextMenuCanvas=new ContextMenu();
		MenuItem menuItem=new MenuItem("清屏");
		menuItem.setOnAction(event -> clearCurrentCanvas());
		contextMenuCanvas.getItems().add(menuItem);
		MenuItem menuItem2=new MenuItem("保存");
		menuItem2.setOnAction(event -> save());
		contextMenuCanvas.getItems().add(menuItem2);
		
		stackPane.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
			if(event.getButton()==MouseButton.SECONDARY)
			{
				contextMenuCanvas.show(stage,event.getScreenX(),event.getScreenY());
			}
			else
			{
				contextMenuCanvas.hide();
			}
			
		});
		
		
		
	}

	protected void save() {
		// TODO Auto-generated method stub
		
	}

	protected void clearCurrentCanvas() {
		painter.clearCanvas();
		
	}

	private void initStackPane() {

		stackPane.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				checkCurrentCanvas();
				painter.mousePress(event.getX(), event.getY());
			}
		});		
		stackPane.addEventFilter(MouseEvent.MOUSE_DRAGGED, event -> painter.mouseMove(event.getX(), event.getY()));
		stackPane.addEventFilter(MouseEvent.MOUSE_RELEASED, event -> painter.mouseUp(event.getX(), event.getY()));
		
	}

	protected void checkCurrentCanvas() {
		if(painter.getcurrentCanvas()==null)
		{
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("错误");
			alert.setHeaderText("请选中一个图层!");
			alert.showAndWait();
		}
		
	}

	private void initLayerList() {
		
		
		canvasLV.setItems(painter.getCanvasLayerList());
		canvasLV.setEditable(true);
		canvasLV.setCellFactory(param -> new CanvasLayerCell(this));
		//set the currentCanvas of painter be the selected item of list
		canvasLV.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<CanvasLayer>() {

			@Override
			public void changed(ObservableValue<? extends CanvasLayer> observable, CanvasLayer oldValue,
					CanvasLayer newValue) {
					//it's very weird when editing be commited that list will be unselect,and the newValue 
					//will be null,this only occur when list has only one child
					if(newValue==null)
					{
						if(canvasLV.getItems().size()>0)
						{
							canvasLV.getSelectionModel().select(0);
						}
					}
					painter.setCurrentCanvas(canvasLV.getSelectionModel().getSelectedItem());
					
			}
		});
		

	}

	private void initToolsPane() {
		ToggleGroup group=new ToggleGroup();
		for (ToolIcon toolIcon : ToolManager.getTools()) 
		{
			RadioButton button=(RadioButton) Tool.loadFxml("Item_Tool", null);
			ImageView imageView=(ImageView) button.getGraphic();
			imageView.setImage(toolIcon.getImage());
			button.setText(toolIcon.getName());
			button.setToggleGroup(group);
			
			
			//select first
			if(flowPane.getChildren().size()==0)
			{
				button.setSelected(true);
				changeToolSetPane(ToolManager.getTypeByName(button.getText()));
			}
			flowPane.getChildren().add(button);
			flowPane.setMargin(button, new Insets(10));
			
		}
		
		for(Toggle toggle:group.getToggles())
		{
			//because i remove the dot icon of radio button ,so it need to implement selecting behavior manually
			
			Control control=(Control) toggle;
			control.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent event) {
					
					
					for(Toggle toggle:group.getToggles())
					{
						RadioButton control=(RadioButton) toggle;
						control.setEffect(null);
						control.setSelected(false);
					}
					//remove all effect
					RadioButton button=(RadioButton) event.getSource();
					//set the effect to selected item
					button.setEffect(new DropShadow());
					button.setSelected(true);
					
					PaintType type=ToolManager.getTypeByName(button.getText());
					painter.setType(type);
					changeToolSetPane(type);
				}
			});
		
		}
		
		
		
		
		
	}

	protected void changeToolSetPane(PaintType type) {
		//base on the tool,change the tool setting
		toolSetGroup.getChildren().clear();
		Parent root=ToolManager.getToolPaneRootByType(type).getRoot();
		toolSetGroup.getChildren().add(root);
		
	}

	private void initCanvas(){
		
		//add back canvas(this will not visible in the layer list)
		createBackCanvas();
		//add default canvas
		createNormalCanvas();
		canvasLV.getSelectionModel().selectFirst();
		CanvasLayer canvasLayer=canvasLV.getSelectionModel().getSelectedItem();
		painter.setCurrentCanvas(canvasLayer);

		
		
	}

	private void createBackCanvas() {
		Canvas canvas=new Canvas();
		canvas.setWidth(1500);
		canvas.setHeight(1000);
		stackPane.getChildren().add(canvas);
		painter.addBackCanvas(canvas);
		
	}

	private void createNormalCanvas() {
		Canvas canvas=new Canvas();
		canvas.setWidth(1500);
		canvas.setHeight(1000);
		stackPane.getChildren().add(canvas);
		painter.addCanvas(canvas);
		
	}

	@FXML
	public void createNewLayer()
	{
		createNormalCanvas();
		selectLastCanvas();
		
	}
	private void selectLastCanvas() {
		canvasLV.getSelectionModel().selectLast();
		
	}

	@FXML
	public void deleteSelectedLayer()
	{
		CanvasLayer canvasLayer=canvasLV.getSelectionModel().getSelectedItem();
		deleteLayer(canvasLayer);
	}

	public void deleteLayer(CanvasLayer canvasLayer) {
		
		if(canvasLayer!=null)
		{
			
			stackPane.getChildren().remove(canvasLayer.getCanvas());
			painter.deleteCanvas(canvasLayer);
		}

	}
	
	@FXML
	public void applyShadow(MouseEvent mouseEvent)
	{
		Button button=(Button) mouseEvent.getSource();
		button.setEffect(new DropShadow());
	}
	
	@FXML
	public void removeShadow(MouseEvent mouseEvent)
	{
		Button button=(Button) mouseEvent.getSource();
		button.setEffect(null);
	}
	
	
	@FXML
	public void preferencePress(ActionEvent event)
	{
		new PopUpWindow_Preference(stage);
	}
	
}
