package gui;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ExternalGuiStartExample extends Application {

	public static void main(String[] args) throws Exception {
		launch(args);
	}

	@Override
	public void start(Stage arg0) throws Exception {

		/**
		 * Loading Screen
		 */
		Stage stage = new Stage();
		Pane pane = new Pane();
		Text text = new Text("Loading, please wait...");
		text.setLayoutY(40);
		pane.getChildren().add(text);
		Scene scene = new Scene(pane);
		stage.setTitle("We-B-Bot Loading...");
		stage.centerOnScreen();
		stage.setHeight(100);
		stage.setWidth(200);
		stage.setResizable(false);
		stage.setScene(scene);
		stage.show();
		// Ende

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
		stage.close();
	}

}
