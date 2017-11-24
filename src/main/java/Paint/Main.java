package Paint;

import java.io.IOException;

import Controller.PaintController;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application{

	
	public static void main(String[] args) throws IOException {
		launch(args);
		
		
	}
	@Override
	public void init() throws Exception {
		// TODO Auto-generated method stub
		super.init();
		//load value


	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		new PaintController(primaryStage);
	}
	
	
	@Override
	public void stop() throws Exception {
		// TODO Auto-generated method stub
		super.stop();
	}
	
}
