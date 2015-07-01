package webot.logs;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class LogWriter
{

	private static final String LINE_SEPARATOR = System.getProperty("line.separator");
	SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");

	/**
	 * Konstruktor ohne Werte
	 */
	public LogWriter() 
	{
		super();
	}

	/**
	 * Schreibt ein Log in eine Datei
	 * 
	 * @param log
	 */
	public void writeLog(Log log)
	{

		String date = sdf.format(System.currentTimeMillis());
		String fileName = "WeBot_Log_" + date + ".txt";
		File file = new File(fileName);

		try 
		{
			FileWriter writer = new FileWriter(file, true);
			writer.write(log.toString());
			writer.write(LINE_SEPARATOR);
			writer.flush();
			writer.close();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}

	}

	
	/**
	 * Schreibt komplette Logfile, z.B. am Ende
	 * 
	 * @param logs
	 */
	public void writeLog(ArrayList<Log> logs) 
	{
		for (Log log : logs) {
			writeLog(log);
		}
	}

}

