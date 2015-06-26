package gui;

import javafx.application.Application;
import javafx.stage.Stage;

public class ExternalGuiStartExample extends Application {

	static Stage stage = new Stage();

	public static void main(String[] args) throws Exception {
		launch(args);
	}

	@Override
	public void start(Stage arg0) throws Exception {
		GUI gui = new GUI();
		gui.start(stage);
	}

}
