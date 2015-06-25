package gui;

import java.io.File;
import java.util.List;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class GUI extends Application {

	private List<Object> instructionList = null;
	private File logicFile = null;

	boolean browserChosen = false;
	boolean logicAdded = false;

	private final Label chooseLogic = new Label("Choose your Logic:");
	private final Label chosenLogic = new Label("Chosen Logic-File: none");
	private final Button loadLogic = new Button("Load Logic-File");
	private final Button discardLogic = new Button("Discard Logic");

	private final Button startButton = new Button("Start");
	private final Button stopButton = new Button("Stop");

	private final Label browserLabel = new Label("Choose your Browser:");
	private ComboBox<String> browserBox = new ComboBox<String>();
	private TextField browserPath = new TextField();
	private Button chooseBrowser = new Button("Choose Browser");
	private Button discardBrowser = new Button("Discard Browser");
	private Label chosenBrowser = new Label("Chosen Browser: none");

	private final Label statusLabel = new Label("Status:");
	private Label statusText = new Label("Waiting for Operator...");

	@Override
	public void start(Stage arg0) throws Exception {

		Pane pane = new Pane();

		browserLabel.setLayoutX(70);
		browserLabel.setLayoutY(10);

		browserBox.setLayoutX(10);
		browserBox.setLayoutY(35);
		browserBox.setMinWidth(100);
		browserBox.getItems().addAll("FireFox", "Internet Explorer", "Chrome");
		browserBox.setValue("FireFox");

		browserPath.setLayoutX(150);
		browserPath.setLayoutY(35);
		browserPath.setPromptText("Enter Browser Path");
		browserPath.setDisable(true);
		browserBox.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				String browser = browserBox.getValue();
				if (browser.equals("FireFox")) {
					browserPath.setDisable(true);
					browserPath.setText("");
				} else {
					browserPath.setDisable(false);
					browserChosen = false;
					browserPath.setText("");
				}
			}
		});

		chooseBrowser.setLayoutX(10);
		chooseBrowser.setLayoutY(65);
		chooseBrowser.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(final ActionEvent e) {
				if (browserBox.getValue().equals("FireFox")) {
					browserPath.setDisable(true);
					chooseBrowser.setDisable(true);
					browserChosen = true;
					chosenBrowser.setText("Chosen Browser: FireFox");
					chosenBrowser.setTextFill(Color.BLACK);
					browserBox.setDisable(true);
					discardBrowser.setDisable(false);
				} else if (browserBox.getValue().equals("Chrome") || browserBox.getValue().equals("Internet Explorer")) {
					if (browserPath.getText().equals("")) {
						chosenBrowser.setText("Invalid Path");
						chosenBrowser.setTextFill(Color.RED);
					} else {
						chosenBrowser.setText("Chosen Browser: " + browserBox.getValue() + " at " + browserPath.getText());
						chosenBrowser.setTextFill(Color.BLACK);
						discardBrowser.setDisable(false);
						browserBox.setDisable(true);
						discardBrowser.setDisable(false);
						browserPath.setDisable(true);
						chooseBrowser.setDisable(true);

					}
				}
			}
		});

		discardBrowser.setLayoutX(115);
		discardBrowser.setLayoutY(65);
		discardBrowser.setDisable(true);
		discardBrowser.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				chosenBrowser.setText("Chosen Browser: none");
				browserPath.setPromptText("Enter Browser Path");
				discardBrowser.setDisable(true);
				browserBox.setDisable(false);
				chooseBrowser.setDisable(false);
				browserPath.setDisable(false);
				browserChosen = false;
			}
		});

		chosenBrowser.setLayoutX(10);
		chosenBrowser.setLayoutY(95);

		chooseLogic.setLayoutX(440);
		chooseLogic.setLayoutY(10);

		loadLogic.setLayoutX(390);
		loadLogic.setLayoutY(35);
		loadLogic.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(final ActionEvent e) {
				FileChooser fileChooser = new FileChooser();
				logicFile = fileChooser.showOpenDialog(arg0);
				if (logicFile != null && logicFile.getName().endsWith(".bla")) {
					logicAdded = true;
					discardLogic.setDisable(false);
					chosenLogic.setTextFill(Color.BLACK);
					chosenLogic.setText("Chosen Logic-file: " + logicFile.getName());
					chosenLogic.setVisible(true);
					if (browserChosen) {
						statusText.setText("Waiting for Start");
					} else
						statusText.setText("Waiting for Browser");
					loadLogic.setDisable(true);
				} else {
					statusText.setText("Invalid file for Logic! Excepting *.bla!");
					chosenLogic.setText("Invalid file for Logic! Excepting *.bla!");
					chosenLogic.setTextFill(Color.RED);
				}

			}
		});

		discardLogic.setLayoutX(490);
		discardLogic.setLayoutY(35);
		discardLogic.setDisable(true);
		discardLogic.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(final ActionEvent e) {
				if (logicFile != null) {
					logicFile = null;
					discardLogic.setDisable(true);
					loadLogic.setDisable(false);
					logicAdded = false;
					chosenLogic.setText("");
					chosenLogic.setVisible(false);
					if (browserChosen)
						statusText.setText("Waiting for Logic");
					else
						statusText.setText("Waiting for Input");
				} else
					discardLogic.setDisable(true);
			}
		});

		chosenLogic.setLayoutX(390);
		chosenLogic.setLayoutY(65);

		startButton.setLayoutX(100);
		startButton.setLayoutY(150);
		startButton.setMinSize(100, 50);
		startButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				if (browserChosen && logicAdded) {
					discardLogic.setDisable(true);
					discardBrowser.setDisable(true);
					statusText.setText("Starting...");
					stopButton.setDisable(false);
					// TODO
					// Ruft den Converter auf und übergibt die Liste an den
					// We-B-Ot
				} else if (browserChosen)
					statusText.setText("Missing Logic!");
				else if (logicAdded)
					statusText.setText("Missing Browser!");
				else
					statusText.setText("Missing Browser and Logic!");
			}
		});

		stopButton.setLayoutX(400);
		stopButton.setLayoutY(150);
		stopButton.setMinSize(100, 50);
		stopButton.setDisable(true);
		stopButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				// TODO
				// Stoppt das Programm
				discardLogic.setDisable(false);
				discardBrowser.setDisable(false);
				browserBox.setDisable(false);
				statusText.setText("Stopped by Operator");

			}
		});

		statusLabel.setLayoutX(10);
		statusLabel.setLayoutY(500);
		statusText.setLayoutX(50);
		statusText.setLayoutY(500);

		pane.getChildren().addAll(chooseLogic, loadLogic, discardLogic, chosenLogic, browserBox, browserLabel, browserPath, chooseBrowser, discardBrowser, chosenBrowser,
				startButton, stopButton, statusLabel, statusText);

		Scene scene = new Scene(pane);
		Stage stage = new Stage();
		stage.setTitle("We-B-Bot");
		stage.centerOnScreen();
		stage.setHeight(550);
		stage.setWidth(600);
		stage.setResizable(false);
		stage.setScene(scene);
		stage.show();

	}

	public static void main(String[] args) {
		launch(args);
	}

	public List<Object> getInstructions() {
		return this.instructionList;
	}

	public void setStatus(String message) {
		statusText.setText(message);
	}

}
