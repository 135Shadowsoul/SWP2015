package gui;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;

public class ExternalGuiStartExample extends Application {

	public static void main(String[] args) throws Exception {
		launch(args);
	}

	@Override
	public void start(Stage arg0) throws Exception {

		GUI gui = new GUI();
		gui.start(arg0);
		gui.startButton().setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				gui.startPressed();
				// TODO
				// Extern einzustellen!!
				// Startet den Bot
			}
		});
	}

}
