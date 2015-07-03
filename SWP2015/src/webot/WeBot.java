package webot;

import java.io.File;
import java.util.List;

import javafx.application.Application;
import javafx.stage.Stage;
import webot.gui.GUI;
import webot.internet.EMailNotifier;
import webot.internet.Notifier;
import webot.logic.InterfaceLogicManager;
import webot.logic.LogicManager;
import webot.logs.Log;
import webot.watchValue.WatchValue;
 

public class WeBot extends Application
{
	private InterfaceLogicManager ilm;
	private Notifier notifier;
	private GUI gui;

	/**
	 * 
	 */
	public WeBot()
	{
		this.ilm = new LogicManager(this);
		this.notifier = new EMailNotifier();
	}
	
	
	/**
	 * Setting the values of the user wanted to show in the gui.
	 * 
	 * @param watchValues
	 */
	public void setWatchValues(List<WatchValue> watchValues) 
	{
		gui.setWatchValues(watchValues);
	}


	/**
	 * Lauchning the GUI
	 * 
	 * @param args
	 */
	public static void main(String[] args) 
	{
		launch(args);
	}
	


	/**
	 * Starts the Gui
	 */
	@Override
	public void start(Stage arg0) throws Exception 
	{
		this.gui = new GUI(this);
		gui.start(arg0);
	}
	
	
	/**
	 * Invoke all nececcary methods to execute the script
	 * 
	 */
	public void startPlaying()
	{		
		ilm.playGameLogic();
	}
	
	
	/**
	 * Invoke all nececcary methods to stop the script
	 * 
	 */
	public void stopPlaying()
	{
		
		ilm.stopPlaying();
	}
	

	/**
	 * Invoke all necessary methods to signalize that there is a new logic file chosen
	 * 
	 * @param logic File that was chosen
	 */
	public void logicChosen(File logic)
	{ 
		ilm.newLogicChosen(logic);
	}
	
	
	/**
	 * Invoke all necessary methods to signalize that there was a reset of the logic 
	 * 
	 */
	public void logicDiscarted()
	{
		ilm.reset();
	}

	
	/**
	 * Adding log to the gui
	 * 
	 * @param log : status message
	 */
	public void addLog(Log log) 
	{
		gui.addLog(log);
	}
}

