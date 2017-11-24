package Controller.Component;

import java.util.function.Consumer;

import Controller.PaintController;
import javafx.event.EventHandler;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.TransferMode;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class StageComponent extends BaseComponent {

	private final Stage stage;

	public StageComponent(PaintController paintController, Stage stage) {
		super(paintController);
		this.stage = stage;
	}

	public void initDragAndDrop(Consumer<String> consumer) {
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
					consumer.accept(db.getUrl());
				
				} else {
				event.consume();
				}
				});
		
	}

	public void initToggleFullScreen() {
		
		
		stage.setFullScreenExitHint("按G键切换全屏模式");
		stage.setFullScreen(true);
		stage.addEventFilter(KeyEvent.KEY_RELEASED, event -> {

            if(event.getCode().equals(KeyCode.G))
            {
                if(stage.fullScreenProperty().get())
                {

                    stage.setFullScreen(false);
                }
                else
                {
                    stage.setFullScreen(true);
                }
            }
            event.consume();

        });
		
	}

	public void setOnCloseListener(EventHandler<WindowEvent> eventHandler) {
		stage.setOnCloseRequest(eventHandler);
		
	}

}
