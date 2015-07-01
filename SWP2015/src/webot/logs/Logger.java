package webot.logs;

import java.util.ArrayList;

public class Logger {
	ArrayList<Log> logs = new ArrayList<Log>();
	LogWriter writer = new LogWriter();

	/**
	 * Konstruktor ohne Werte
	 */
	public Logger() {

	}

	/**
	 * Schreibt log in List und gibt den Logeintrag an den LogWriter weiter
	 * 
	 * @param action
	 */
	public void log(String action) {
		Log log = new Log(action);
		logs.add(log);
		writer.writeLog(log);
	}

	/**
	 * F�gt der Log-Liste einen Eintrag hinzu
	 * 
	 * @param action
	 */
	public void addLog(String action) {
		Log log = new Log(action);
		logs.add(log);
	}

	/**
	 * @return logs
	 */
	public ArrayList<Log> getLogs() {
		return this.logs;
	}

}
