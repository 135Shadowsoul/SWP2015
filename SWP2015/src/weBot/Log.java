package weBot;

import java.text.SimpleDateFormat;

public final class Log {

	private final String text;
	private final String time;

	SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy hh:mm:ss");

	/**
	 * Konstruktor für Log. Erstellt mit Übergebenem Text und hängt Zeitstempel
	 * dran
	 * 
	 * @param text
	 */
	public Log(final String text) {
		this.text = text;
		this.time = sdf.format(System.currentTimeMillis());
	}

	/**
	 * @return String des Logs im Format 01.01.2000 13:30, Login erfolgreich
	 */
	public String toString() {
		return time + ", " + text;

	}

}
