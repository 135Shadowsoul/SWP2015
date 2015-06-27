package gui;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Properties;

import weBot.Log;
import weBot.WatchValue;
import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Separator;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.RectangleBuilder;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

public class GUI extends Application {

	public GUI() {

	}

	private File configFile = new File(System.getProperty("user.dir") + System.getProperty("file.separator") + "Bot.conf");
	private Properties configProps = new Properties();

	private File logicFile = null;
	private boolean browserChosen = false;
	private boolean logicAdded = false;

	private MenuBar menubar = new MenuBar();

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

	private final Label logLabel = new Label("Logs");
	private TableView<Log> logTable = new TableView<Log>();
	private TableColumn<Log, String> messageColumn = new TableColumn<Log, String>();
	private TableColumn<Log, String> dateColumn = new TableColumn<Log, String>();
	private ObservableList<Log> logList = FXCollections.observableArrayList(new Log("Programm Started"));

	private final Label scoreLabel = new Label("Values to watch on");
	private TableView<WatchValue> scoreTable = new TableView<WatchValue>();
	private TableColumn<WatchValue, String> scoreNameColumn = new TableColumn<WatchValue, String>();
	private TableColumn<WatchValue, String> scoreValueColumn = new TableColumn<WatchValue, String>();
	private ObservableList<WatchValue> scoreList = FXCollections.observableArrayList();

	private final Label statusLabel = new Label("Status:");
	private Label statusText = new Label("Waiting for Operator...");

	private Separator browser_logic = new Separator();
	private Separator top_buttons = new Separator();
	private Separator under_buttons = new Separator();
	private Separator top_Log = new Separator();

	private Rectangle statusBar;

	private Menu menu = new Menu("Menu");
	private Menu browserMenu = new Menu("Browser");
	private Menu logicMenu = new Menu("Logic");
	private Menu help = new Menu("Help");

	private MenuItem save = new MenuItem("Save as Default");
	private MenuItem exit = new MenuItem("Exit We-B-Ot");

	private MenuItem firefox = new MenuItem("FireFox");
	private MenuItem explorer = new MenuItem("Internet Explorer");
	private MenuItem chrome = new MenuItem("Chrome");
	private MenuItem discardBrowserItem = new MenuItem("Discard Browser");

	private MenuItem discardLogicItem = new MenuItem("Discard Logic");
	private MenuItem loadLogicItem = new MenuItem("Load Logic");

	MenuItem about = new MenuItem("About");

	@Override
	public void start(Stage arg0) throws Exception {

		// configFile = new File(System.getProperty("user.dir") + );

		Stage stage = new Stage();
		Pane pane = new Pane();
		statusBar = RectangleBuilder.create().width(600).height(30).x(0).y(545).fill(Color.LIGHTGREY).stroke(Color.DIMGREY).build();

		menu.getItems().addAll(save, new SeparatorMenuItem(), exit);

		save.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				if (!System.getProperty("os.name").contains("Windows")) {
					statusText.setText("Windows required for this feature");
				} else if (!logicAdded && !browserChosen) {
					statusText.setText("Nothing to save!");
				} else if (!logicAdded) {
					statusText.setText("Missing Logic!");
				} else if (!browserChosen) {
					statusText.setText("Missing Browser!");
				} else
					try {
						saveDefault();
						statusText.setText("Done saving Config");
					} catch (IOException e) {
						e.printStackTrace();
						statusText.setText("Error while saving configuration");
					}
			}
		});

		exit.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				stage.close();
			}
		});

		discardBrowserItem.setDisable(true);

		firefox.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				discardBrowserItem.setDisable(false);
				chrome.setDisable(true);
				explorer.setDisable(true);
				browserBox.setValue("FireFox");
				browserPath.setDisable(true);
				chooseBrowser.setDisable(true);
				browserChosen = true;
				chosenBrowser.setText("Chosen Browser: FireFox");
				chosenBrowser.setTextFill(Color.BLACK);
				browserPath.setPromptText("Enter Browser Path");
				firefox.setDisable(true);
				statusBar.setFill(Color.LIGHTGREY);
				browserBox.setDisable(true);
				discardBrowser.setDisable(false);
				if (!logicAdded) {
					statusText.setText("Waiting for Logic");
					statusBar.setFill(Color.LIGHTGREY);
				} else {
					statusText.setText("Ready");
					statusBar.setFill(Color.LIGHTGREY);
				}
			}
		});
		explorer.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				discardBrowserItem.setDisable(false);
				browserBox.setValue("Internet Explorer");
				browserPath.setDisable(false);
				chosenBrowser.setText("Enter Path");
				browserBox.setDisable(true);
				discardBrowser.setDisable(false);
				explorer.setDisable(true);
				firefox.setDisable(false);
				chrome.setDisable(false);
			}
		});
		chrome.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {

				discardBrowserItem.setDisable(false);
				browserBox.setValue("Chrome");
				browserPath.setDisable(false);
				chosenBrowser.setText("Enter Path");
				browserBox.setDisable(true);
				discardBrowser.setDisable(false);
				chrome.setDisable(true);
				firefox.setDisable(false);
				explorer.setDisable(false);
			}
		});
		discardBrowserItem.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				browserMenu.setDisable(false);
				chosenBrowser.setText("Chosen Browser: none");
				browserPath.setPromptText("Enter Browser Path");
				discardBrowser.setDisable(true);
				browserBox.setDisable(false);
				chooseBrowser.setDisable(false);
				firefox.setDisable(false);
				explorer.setDisable(false);
				chrome.setDisable(false);
				if (!browserBox.getValue().equals("FireFox"))
					browserPath.setDisable(false);
				browserChosen = false;
				if (logicAdded) {
					statusText.setText("Waiting for Browser");
					statusBar.setFill(Color.LIGHTGREY);
				} else {
					statusText.setText("Waiting for Inputs");
					statusBar.setFill(Color.LIGHTGREY);
				}
			}
		});

		browserMenu.getItems().addAll(firefox, explorer, chrome, new SeparatorMenuItem(), discardBrowserItem);

		loadLogicItem.setOnAction(new EventHandler<ActionEvent>() {
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
					discardLogicItem.setDisable(false);
					loadLogicItem.setDisable(true);
					discardLogic.setDisable(false);
					if (browserChosen) {
						statusText.setText("Ready");
						statusBar.setFill(Color.LIGHTGREY);
					} else {
						statusText.setText("Waiting for Browser");
						statusBar.setFill(Color.LIGHTGREY);
					}
					loadLogic.setDisable(true);
				} else {
					statusText.setText("Invalid file for Logic! Excepting *.bla!");
					chosenLogic.setText("Invalid file for Logic! Excepting *.bla!");
					chosenLogic.setTextFill(Color.RED);
					chosenLogic.setVisible(true);
				}

			}
		});

		discardLogicItem.setDisable(true);
		discardLogicItem.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(final ActionEvent e) {
				if (logicFile != null) {
					logicFile = null;
					discardLogic.setDisable(true);
					loadLogic.setDisable(false);
					logicAdded = false;
					chosenLogic.setText("Chosen Logic-File: none");
					chosenLogic.setVisible(false);
					loadLogicItem.setDisable(false);
					discardLogicItem.setDisable(true);
					if (browserChosen)
						statusText.setText("Waiting for Logic");
					else
						statusText.setText("Waiting for Input");
				} else
					discardLogic.setDisable(true);
			}
		});
		logicMenu.getItems().addAll(loadLogicItem, discardLogicItem);

		about.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				final Stage dialog = new Stage();
				dialog.initModality(Modality.APPLICATION_MODAL);
				dialog.initOwner(arg0);
				Button ok = new Button("OK");
				ok.setMinWidth(100);
				ok.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						dialog.close();
					}
				});
				Label thanks = new Label("Thanks for using We-B-Ot!");
				Label copyright = new Label("\u00a9 Andreas Knapp, Stefan Off, Julian Tritschner");
				Label warning = new Label("Warning: Most Games banish player using bots!");
				Label warning2 = new Label("Use at your own risk!");
				Pane pane = new Pane();
				warning.setTextFill(Color.RED);
				warning2.setTextFill(Color.RED);
				thanks.setLayoutY(20);
				thanks.setLayoutX(80);
				copyright.setLayoutY(60);
				copyright.setLayoutX(30);
				warning.setLayoutY(120);
				warning.setLayoutX(25);
				warning2.setLayoutY(140);
				warning2.setLayoutX(100);
				ok.setLayoutY(225);
				ok.setLayoutX(100);
				pane.getChildren().addAll(thanks, copyright, warning, warning2, ok);
				Scene dialogScene = new Scene(pane, 300, 250);
				dialog.setTitle("About We-B-Ot");
				dialog.setResizable(false);
				dialog.setScene(dialogScene);
				dialog.show();
			}
		});

		help.getItems().add(about);

		menubar.getMenus().addAll(menu, browserMenu, logicMenu, help);
		menubar.setMinWidth(600);

		browserLabel.setLayoutX(70);
		browserLabel.setLayoutY(30);

		browserBox.setLayoutX(10);
		browserBox.setLayoutY(55);
		browserBox.setMinWidth(100);
		browserBox.getItems().addAll("FireFox", "Internet Explorer", "Chrome");
		browserBox.setValue("FireFox");

		browserPath.setLayoutX(150);
		browserPath.setLayoutY(55);
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
		chooseBrowser.setLayoutY(85);
		chooseBrowser.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(final ActionEvent e) {
				if (browserBox.getValue().equals("FireFox")) {
					chrome.setDisable(true);
					firefox.setDisable(true);
					explorer.setDisable(true);
					browserPath.setDisable(true);
					chooseBrowser.setDisable(true);
					browserChosen = true;
					chosenBrowser.setText("Chosen Browser: FireFox");
					chosenBrowser.setTextFill(Color.BLACK);
					statusBar.setFill(Color.LIGHTGREY);
					browserBox.setDisable(true);
					discardBrowser.setDisable(false);
					if (!logicAdded) {
						statusText.setText("Waiting for Logic");
						statusBar.setFill(Color.LIGHTGREY);
					} else {
						statusText.setText("Ready");
						statusBar.setFill(Color.LIGHTGREY);
					}
				} else if (browserBox.getValue().equals("Chrome") || browserBox.getValue().equals("Internet Explorer")) {
					if (browserPath.getText().equals("")) {
						chosenBrowser.setText("Invalid Path");
						chosenBrowser.setTextFill(Color.RED);
					} else {
						chrome.setDisable(true);
						firefox.setDisable(true);
						explorer.setDisable(true);
						chosenBrowser.setText("Chosen Browser: " + browserBox.getValue());
						chosenBrowser.setTextFill(Color.BLACK);
						discardBrowser.setDisable(false);
						browserBox.setDisable(true);
						discardBrowser.setDisable(false);
						browserPath.setDisable(true);
						chooseBrowser.setDisable(true);
						statusBar.setFill(Color.LIGHTGREY);
						browserChosen = true;
						if (!logicAdded) {
							statusText.setText("Waiting for Logic");
							statusBar.setFill(Color.LIGHTGREY);
						} else {
							statusText.setText("Ready");
							statusBar.setFill(Color.LIGHTGREY);
						}
					}
				}
			}
		});

		discardBrowser.setLayoutX(115);
		discardBrowser.setLayoutY(85);
		discardBrowser.setDisable(true);
		discardBrowser.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				firefox.setDisable(false);
				chrome.setDisable(false);
				explorer.setDisable(false);
				discardBrowserItem.setDisable(true);
				chosenBrowser.setText("Chosen Browser: none");
				browserPath.setPromptText("Enter Browser Path");
				discardBrowser.setDisable(true);
				browserBox.setDisable(false);
				chooseBrowser.setDisable(false);
				if (!browserBox.getValue().equals("FireFox"))
					browserPath.setDisable(false);
				browserChosen = false;
				if (logicAdded) {
					statusText.setText("Waiting for Browser");
					statusBar.setFill(Color.LIGHTGREY);
				} else {
					statusText.setText("Waiting for Inputs");
					statusBar.setFill(Color.LIGHTGREY);
				}
			}
		});

		chosenBrowser.setLayoutX(10);
		chosenBrowser.setLayoutY(115);

		chooseLogic.setLayoutX(440);
		chooseLogic.setLayoutY(30);

		loadLogic.setLayoutX(390);
		loadLogic.setLayoutY(55);
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
					loadLogicItem.setDisable(true);
					if (browserChosen) {
						statusText.setText("Ready");
						statusBar.setFill(Color.LIGHTGREY);
					} else {
						statusText.setText("Waiting for Browser");
						statusBar.setFill(Color.LIGHTGREY);
					}
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
		discardLogic.setLayoutY(55);
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
					discardLogic.setDisable(true);
					discardLogicItem.setDisable(true);
					discardLogicItem.setDisable(false);
					if (browserChosen)
						statusText.setText("Waiting for Logic");
					else
						statusText.setText("Waiting for Input");
				} else
					discardLogic.setDisable(true);
			}
		});

		chosenLogic.setLayoutX(390);
		chosenLogic.setLayoutY(85);

		startButton.setLayoutX(100);
		startButton.setLayoutY(150);
		startButton.setMinSize(100, 50);
		startButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				startPressed();
				// TODO
				// Extern einzustellen!!
				// Startet den Bot
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
				// Extern einzustellen!!
				// Stoppt den Bot
				stopPressed();
			}
		});

		logLabel.setLayoutX(270);
		logLabel.setLayoutY(350);
		logLabel.setFont(new Font("Arial", 20));
		logTable.setLayoutX(10);
		logTable.setLayoutY(380);
		logTable.setMaxHeight(150);
		logTable.setMaxWidth(570);
		logTable.setMinWidth(570);

		dateColumn.setCellValueFactory(new Callback<CellDataFeatures<Log, String>, ObservableValue<String>>() {
			public ObservableValue<String> call(CellDataFeatures<Log, String> p) {
				return p.getValue().getDateProperty();
			}
		});
		dateColumn.setMinWidth(118);
		dateColumn.setText("Timestamp");
		messageColumn.setCellValueFactory(new Callback<CellDataFeatures<Log, String>, ObservableValue<String>>() {
			public ObservableValue<String> call(CellDataFeatures<Log, String> p) {
				return p.getValue().getTextProperty();
			}
		});

		messageColumn.setMinWidth(450);
		messageColumn.setText("Message");

		logTable.setItems(logList);
		logTable.getColumns().add(dateColumn);
		logTable.getColumns().add(messageColumn);

		scoreLabel.setLayoutX(230);
		scoreLabel.setLayoutY(220);
		scoreLabel.setFont(new Font("Arial", 15));
		scoreTable.setLayoutX(10);
		scoreTable.setLayoutY(240);
		scoreTable.setMaxWidth(570);
		scoreTable.setMinWidth(570);
		scoreTable.setMaxHeight(85);
		scoreTable.setItems(scoreList);
		scoreTable.getColumns().add(scoreNameColumn);
		scoreTable.getColumns().add(scoreValueColumn);
		scoreTable.setVisible(false);
		scoreLabel.setVisible(false);

		statusLabel.setLayoutX(10);
		statusLabel.setLayoutY(550);
		statusText.setLayoutX(50);
		statusText.setLayoutY(550);

		top_buttons.setLayoutY(140);
		top_buttons.setMinWidth(600);
		under_buttons.setLayoutY(210);
		under_buttons.setMinWidth(600);
		top_Log.setLayoutY(340);
		top_Log.setMinWidth(600);
		browser_logic.setOrientation(Orientation.VERTICAL);
		browser_logic.setLayoutX(350);
		browser_logic.setMinHeight(140);

		pane.getChildren().addAll(chooseLogic, loadLogic, discardLogic, chosenLogic, browserBox, browserLabel, browserPath, chooseBrowser, discardBrowser, chosenBrowser,
				startButton, stopButton, logLabel, logTable, statusBar, statusLabel, statusText, top_buttons, top_Log, under_buttons, browser_logic, menubar, scoreLabel,
				scoreTable);

		try {
			loadDefault();
		} catch (Exception e) {

		}

		Scene scene = new Scene(pane);

		stage.setTitle("We-B-Bot");
		stage.centerOnScreen();
		stage.setHeight(600);
		stage.setWidth(600);
		stage.setResizable(false);
		stage.setScene(scene);
		stage.show();

	}

	public static void main(String[] args) {
		launch(args);
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
		discardLogicItem.setDisable(false);
		statusText.setText(message);
		Log log = new Log(message);
		addLog(log);
	}

	public void setWatchValues(List<WatchValue> watchValues) {
		scoreTable.getColumns().clear();
		scoreList.clear();
		scoreList.addAll(watchValues);
		scoreNameColumn.setCellValueFactory(new Callback<CellDataFeatures<WatchValue, String>, ObservableValue<String>>() {
			public ObservableValue<String> call(CellDataFeatures<WatchValue, String> p) {
				return p.getValue().getNameProperty();
			}
		});
		scoreNameColumn.setMinWidth(119);
		scoreNameColumn.setText("Name");

		scoreValueColumn.setCellValueFactory(new Callback<CellDataFeatures<WatchValue, String>, ObservableValue<String>>() {
			public ObservableValue<String> call(CellDataFeatures<WatchValue, String> p) {
				return p.getValue().getValueProperty();
			}
		});
		scoreValueColumn.setMinWidth(450);
		scoreValueColumn.setText("Value");
		scoreTable.getColumns().add(scoreNameColumn);
		scoreTable.getColumns().add(scoreValueColumn);

		if (watchValues.size() == 1)
			scoreTable.setMaxHeight(65);
		else
			scoreTable.setMaxHeight(90);

		scoreTable.setVisible(true);
		scoreLabel.setVisible(true);
	}

	public void addLog(Log log) {
		logList.add(0, log);
	}

	public Button startButton() {
		return this.startButton;
	}

	public Button stopButton() {
		return this.stopButton;
	}

	public String getBrowser() {
		return browserBox.getValue();
	}

	public String getBrowserPath() {
		return browserPath.getText();
	}

	public File getLogic() {
		return this.logicFile;
	}

	public void startPressed() {
		if (browserChosen && !logicAdded) {
			statusText.setText("Missing Logic!");
			statusBar.setFill(Color.RED);
		} else if (!browserChosen && logicAdded) {
			statusText.setText("Missing Browser!");
			statusBar.setFill(Color.RED);
		} else if (!browserChosen && !logicAdded) {
			statusText.setText("Missing Browser and Logic!");
			statusBar.setFill(Color.RED);
		} else {
			discardLogic.setDisable(true);
			discardBrowser.setDisable(true);
			startButton.setDisable(true);
			statusText.setText("Starting...");
			statusBar.setFill(Color.LIGHTGREY);
			Log log = new Log("Start running...");
			addLog(log);
			stopButton.setDisable(false);
		}

	}

	public void stopPressed() {
		stop("Stopped by Operator!");
	}

	private void loadDefault() throws Exception {

		// loads properties from file
		InputStream inputStream = new FileInputStream((System.getProperty("user.dir") + System.getProperty("file.separator") + "Bot.conf"));
		configProps.load(inputStream);
		inputStream.close();

		// setting up loaded props
		browserBox.setValue(configProps.getProperty("Browser"));
		browserPath.setText(configProps.getProperty("BrowserPath"));
		chooseBrowser.fire();
		logicFile = new File(configProps.getProperty("LogicFile"));
		logicAdded = true;
		discardLogic.setDisable(false);
		loadLogic.setDisable(true);
		chosenLogic.setTextFill(Color.BLACK);
		chosenLogic.setText("Chosen Logic-file: " + logicFile.getName());
		chosenLogic.setVisible(true);
		loadLogicItem.setDisable(true);
		statusText.setText("Loaded Default... Read to start");

	}

	private void saveDefault() throws IOException {

		configProps.setProperty("Browser", browserBox.getValue());
		configProps.setProperty("BrowserPath", browserPath.getText());
		configProps.setProperty("LogicFile", logicFile.getPath());

		OutputStream outputStream = new FileOutputStream(configFile);
		configProps.store(outputStream, "We-B-ot Settings");
		outputStream.close();

	}

}
