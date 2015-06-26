package gui;

import java.io.File;
import java.util.List;
import weBot.Log;
import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.util.Callback;

public class GUI extends Application {

	private List<Object> instructionList = null;
	private File logicFile = null;
	boolean browserChosen = false;
	boolean logicAdded = false;

	private final Label browserLabel = new Label("Choose your Browser:");
	private ComboBox<String> browserBox = new ComboBox<String>();
	private TextField browserPath = new TextField();
	private Button chooseBrowser = new Button("Choose Browser");
	private Button discardBrowser = new Button("Discard Browser");
	private Label chosenBrowser = new Label("Chosen Browser: none");

	private final Label chooseLogic = new Label("Choose your Logic:");
	private final Label chosenLogic = new Label("Chosen Logic-File: none");
	private final Button loadLogic = new Button("Load Logic-File");
	private final Button discardLogic = new Button("Discard Logic");

	private final Button startButton = new Button("Start");
	private final Button stopButton = new Button("Stop");
	
	private Label scoreLabel = new Label("No Score");
	private Label scoreText = new Label("Nothing to Watch");
	
	private TableView<Log> logTable = new TableView<Log>();
	private TableColumn<Log, String> logColumn = new TableColumn<Log, String>();
	private ObservableList<Log> logList = FXCollections.observableArrayList(new Log("Programm Started"));

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
				Log log = new Log("Test");
				addLog(log);
				if (browserBox.getValue().equals("FireFox")) {
					browserPath.setDisable(true);
					chooseBrowser.setDisable(true);
					browserChosen = true;
					chosenBrowser.setText("Chosen Browser: FireFox");
					chosenBrowser.setTextFill(Color.BLACK);
					browserBox.setDisable(true);
					discardBrowser.setDisable(false);
					if (!logicAdded)
						statusText.setText("Waiting for Logic");
					else
						statusText.setText("Ready");
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
						browserChosen = true;
						if (!logicAdded)
							statusText.setText("Waiting for Logic");
						else
							statusText.setText("Ready");
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
				if (!browserBox.getValue().equals("FireFox"))
					browserPath.setDisable(false);
				browserChosen = false;
				if (logicAdded)
					statusText.setText("Waiting for Browser");
				else
					statusText.setText("Waiting for Inputs");
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
				ExtensionFilter filter = new ExtensionFilter("BotLanguage (*.bla)", "*.bla");

				fileChooser.getExtensionFilters().add(filter);
				logicFile = fileChooser.showOpenDialog(arg0);
				if (logicFile != null && logicFile.getName().endsWith(".bla")) {
					logicAdded = true;
					discardLogic.setDisable(false);
					chosenLogic.setTextFill(Color.BLACK);
					chosenLogic.setText("Chosen Logic-file: " + logicFile.getName());
					chosenLogic.setVisible(true);
					if (browserChosen) {
						statusText.setText("Ready");
					} else
						statusText.setText("Waiting for Browser");
					loadLogic.setDisable(true);
				} else {
					statusText.setText("Invalid file for Logic! Excepting *.bla!");
					chosenLogic.setText("Invalid file for Logic! Excepting *.bla!");
					chosenLogic.setTextFill(Color.RED);
					chosenLogic.setVisible(true);
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
					chosenLogic.setText("Chosen Logic-File: none");
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
					startButton.setDisable(true);
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
				stop("Stopped by Operator!");

			}
		});
		
		scoreLabel.setLayoutX(10);
		scoreLabel.setLayoutY(250);
		scoreText.setLayoutX(10);
		scoreText.setLayoutY(270);
		
		logTable.setLayoutX(10);
		logTable.setLayoutY(300);
		logTable.setMaxHeight(150);
		logTable.setMaxWidth(570);
		logTable.setMinWidth(570);
		logColumn.setMinWidth(568);
		logColumn.setMaxWidth(568);
		logColumn.setText("Logs");
		logColumn.setCellValueFactory(new Callback<CellDataFeatures<Log, String>, ObservableValue<String>>() {
			public ObservableValue<String> call(CellDataFeatures<Log, String> p) {
				return p.getValue().getLogProperty();
			}
		});	
		

		logTable.setItems(logList);
		logTable.getColumns().add(logColumn);	

		statusLabel.setLayoutX(10);
		statusLabel.setLayoutY(500);
		statusText.setLayoutX(50);
		statusText.setLayoutY(500);

		pane.getChildren().addAll(chooseLogic, loadLogic, discardLogic, chosenLogic, browserBox, browserLabel, browserPath, chooseBrowser, discardBrowser, chosenBrowser,
				startButton, stopButton, scoreLabel, scoreText, logTable, statusLabel, statusText);
		
		

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

	public void stop(String message) {
		discardLogic.setDisable(false);
		discardBrowser.setDisable(false);
		chooseBrowser.setDisable(true);
		stopButton.setDisable(true);
		startButton.setDisable(false);
		statusText.setText(message);
	}
	
	public void setScore(String name, String message){
		scoreLabel.setText(name + ":");
		scoreText.setText(message);
	}
	
	public void addLog(Log log){
		logList.add(0, log);
	}
		

}
