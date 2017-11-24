package Controller;


import Bean.CanvasLayer;
import Bean.ToolIcon;
import Cell.CanvasLayerCell;
import Controller.Component.StageComponent;
import Manager.EffectFactory;
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
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
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
	private final Stage stage;

	@FXML
	private Pane colorChooserPane;
	
	public PaintController(Stage primaryStage) {
		
		stage = primaryStage;
		Parent root = loadFxml("Main");
		createWindow(primaryStage, root, "Paint");
		initStage();
		initView();
	
	}


	
	private void initStage() {
		StageComponent stageComponent = new StageComponent(this, stage);
		stageComponent.initDragAndDrop(url->addImageToNewCanvasByUrl(url));
		stageComponent.initToggleFullScreen();
		stageComponent.setOnCloseListener(event -> {
            //deal with unsaved change

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
		initMouseEvent();
		initContextMenu();
		initColorChooser();
	}

	private void initColorChooser() {
		Parent parent=Tool.loadFxml("chooseColor", null);
		colorChooserPane.getChildren().add(parent);
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

	private void save() {
		// TODO Auto-generated method stub
		
	}

	private void clearCurrentCanvas() {
		painter.clearCurrentCanvas();
		
	}

	private void initMouseEvent() {

		stackPane.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> {
            checkCurrentCanvas();
            painter.mousePress(event.getX(), event.getY());
        });
		stackPane.addEventFilter(MouseEvent.MOUSE_MOVED, event -> painter.mouseMove(event.getX(), event.getY()));		
		stackPane.addEventFilter(MouseEvent.MOUSE_DRAGGED, event -> painter.mouseDrag(event.getX(), event.getY()));
		stackPane.addEventFilter(MouseEvent.MOUSE_RELEASED, event -> painter.mouseUp(event.getX(), event.getY()));
		
	}

	private void checkCurrentCanvas() {
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
		canvasLV.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
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

        });
		

	}

	private void initToolsPane() {
		ToggleGroup group=new ToggleGroup();
		//load icon
		for (ToolIcon toolIcon : ToolManager.getTools()) 
		{
			RadioButton button=(RadioButton) Tool.loadFxml("Item_Tool", null);
			ImageView imageView=(ImageView) button.getGraphic();
			imageView.setImage(toolIcon.getImage());
			button.setText(toolIcon.getName());
			button.setToggleGroup(group);
			flowPane.getChildren().add(button);
			FlowPane.setMargin(button, new Insets(10));
			
		}
		
		//add change behavior
		for(Toggle toggle:group.getToggles())
		{
			//because i remove the dot icon of radio button ,so it need to implement selecting behavior manually
			
			Control control=(Control) toggle;
			control.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {


                for(Toggle toggle1 :group.getToggles())
                {
                    RadioButton control1 =(RadioButton) toggle1;
                    control1.setEffect(null);
                    control1.setSelected(false);
                }
                //remove all effect
                RadioButton button=(RadioButton) event.getSource();
                //set the effect to selected item
                button.setEffect(EffectFactory.getDrapShadow(Color.WHITE));
                button.setSelected(true);

                PaintType type=ToolManager.getTypeByName(button.getText());
                painter.setType(type);
                changeToolSetPane(type);
            });
		
		}
		
		//if has at least one tool,select first
		if(group.getToggles().size()>0)
		{
			RadioButton button=(RadioButton) group.getToggles().get(0);
			button.setSelected(true);
			button.setEffect(EffectFactory.getDrapShadow(Color.WHITE));
			changeToolSetPane(ToolManager.getTypeByName(button.getText()));
		}
		
		
		
	}

	private void changeToolSetPane(PaintType type) {
		//base on the tool,change the tool setting
		toolSetGroup.getChildren().clear();
		Parent root=ToolManager.getToolPaneRootByType(type);
		toolSetGroup.getChildren().add(root);
		
	}

	private void initCanvas(){
		
		//add back canvas(this will not visible in the layer list)
		painter.setBackCanvas(createCanvasAndAddToPane());
		//add default canvas
		painter.addCanvas(createCanvasAndAddToPane());
		canvasLV.getSelectionModel().selectFirst();
		CanvasLayer canvasLayer=canvasLV.getSelectionModel().getSelectedItem();
		painter.setCurrentCanvas(canvasLayer);
		//create temp canvas
		painter.setTempCanvas(createCanvasAndAddToPane());
		
		
	}

	

	private Canvas createCanvasAndAddToPane() {
		Canvas canvas=new Canvas();
		canvas.setWidth(1500);
		canvas.setHeight(1000);
		stackPane.getChildren().add(canvas);
		return canvas;
	}

	

	

	@FXML
	private void createNewLayer()
	{
		painter.addCanvas(createCanvasAndAddToPane());
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
