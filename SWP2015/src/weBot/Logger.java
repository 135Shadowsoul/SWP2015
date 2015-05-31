package weBot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class Logger {
	
	List<String> logs = new ArrayList<String>();
	SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy hh:mm:ss");
	LogWriter writer = new LogWriter();
	
	
	/**
	 * Konstruktor ohne Werte
	 */
	public Logger(){		
	}
	
	
	/**
	 * Schreibt log in List und gibt den Logeintrag an den LogWriter weiter
	 * 
	 * @param action
	 */
	public void log(String action){		
		String time = sdf.format(System.currentTimeMillis());		
		String log = time + ": " + action;
		logs.add(log);
		
		writer.writeLog(log);		
	}
	
	/**
	 * @return logs
	 */
	public List<String> getLogs(){
		return this.logs;
	}
	
	
//	public static void main(String[] args){
//		Logger logger = new Logger();
//		LogWriter lw = new LogWriter();
//		
//		logger.log("Login");
//		 long millisToWait = 1000;
//	        long millis = System.currentTimeMillis();
//	        while((System.currentTimeMillis() - millis) < millisToWait) {
//	            // Do nothing
//	        }  
//		logger.log("Angriff");
//		 long millisToWait2 = 1000;
//	        long millis2 = System.currentTimeMillis();
//	        while((System.currentTimeMillis() - millis2) < millisToWait2) {
//	            // Do nothing
//	        }  
//		logger.log("ERROR!!");
//		 long millisToWait3 = 1000;
//	        long millis3 = System.currentTimeMillis();
//	        while((System.currentTimeMillis() - millis3) < millisToWait3) {
//	            // Do nothing
//	        }  
//		logger.log("Angriff");
//		 long millisToWait4 = 1000;
//	        long millis4 = System.currentTimeMillis();
//	        while((System.currentTimeMillis() - millis4) < millisToWait4) {
//	            // Do nothing
//	        }  
//		
//		logger.log("Logout");
//		 long millisToWait5 = 1000;
//	        long millis5 = System.currentTimeMillis();
//	        while((System.currentTimeMillis() - millis5) < millisToWait5) {
//	            // Do nothing
//	        }  
//		
//		System.out.println(logger.getLogs());
//		lw.writeLog(logger.getLogs());
//	}

}
