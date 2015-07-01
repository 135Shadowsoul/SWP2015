package webot.logs;

import java.text.SimpleDateFormat;

import javafx.beans.property.SimpleStringProperty;



public final class Log
{	
	private final String text;
	private final String time;
	private final SimpleStringProperty textProperty;
	private final SimpleStringProperty dateProperty;

	SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy hh:mm:ss");

	/**
	 * Konstruktor f�r Log. Erstellt mit �bergebenem Text und h�ngt Zeitstempel
	 * dran
	 * 
	 * @param text
	 */
	public Log(final String text)
	{
		this.text = text;
		this.time = sdf.format(System.currentTimeMillis());
		
		this.textProperty = new SimpleStringProperty(text);
		this.dateProperty = new SimpleStringProperty(time);
	}

	/**
	 * @return String des Logs im Format 01.01.2000 13:30, Login erfolgreich
	 */
	public String toString() 
	{
		return time + ", " + text;
	}
	
	public SimpleStringProperty getTextProperty()
	{
		return this.textProperty;
	}
	
	public SimpleStringProperty getDateProperty(){
		return this.dateProperty;
	}	
}
