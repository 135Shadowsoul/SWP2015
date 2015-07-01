package webot.logic;

import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.TimeoutException;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.eclipse.emf.common.util.EList;
import org.openqa.selenium.NoSuchElementException;

import webot.internet.EMailNotifier;
import webot.internet.InterfaceHTMLParser;
import webot.internet.Notifier;
import webot.internet.SeleniumParser;

/**
 * 
 * Executing the instructions of the user input
 *
 */
public class GameLogic 
{
	//TODO logging
	private Notifier notifier;
	private InterfaceHTMLParser parser;

	private HashMap<String, String>	variables;
	private String address;

	public GameLogic()
	{
		this.notifier = new EMailNotifier();
		this.parser = new SeleniumParser();
		this.variables = new HashMap<String, String>();
	}



	/**
	 * Start at the given date (time).
	 * 
	 * @param date
	 * @  
	 */
	@SuppressWarnings("static-access")
	public void start(Date date)
	{
		while(date.after(new Date(System.currentTimeMillis())))
		{		
			try 
			{
				Thread.currentThread().sleep(date.getTime() - System.currentTimeMillis());
			} 
			catch (InterruptedException e) 
			{
				e.printStackTrace();
			}
		}

		parser.openBrowser();
	}


	/**
	 * NotifyMessage for a Bot Notification
	 * 
	 * @param message 
	 * @param address
	 * @throws MessagingException 
	 * @throws IllegalArgumentException 
	 * @throws AddressException 
	 */
	public String notifyMessage(String message) throws AddressException, MessagingException  
	{
		// constant subject Bot Notification
		String subject = "Bot Notification";

		if(address != null)
		{
			notifier.notify(address, subject, message);
			return "Notification sent!";
		}
		
		return message;
	}


	/**
	 * Set the address of the notification to send for
	 * 
	 * @param address : Email address
	 */
	public void setAddress(String address)
	{
		this.address = address;
	}



	/**
	 * Set the closeable to false that the browser does not close
	 * 
	 */
	public void noClose() 
	{
		parser.setCloseable(false);
	}


	/**
	 * 
	 * @param url
	 */
	public void connect(String url) 
	{
		parser.connect(url);
	}


	/**
	 * 
	 * @param xpath
	 * @param value
	 * @throws TimeoutException 
	 * @throws NoSuchElementException 
	 * @ 
	 */
	public void write(String xpath, String value) throws NoSuchElementException, TimeoutException   
	{
		parser.enterText(xpath, value);
	}


	/**
	 * 
	 * @param xpath
	 * @return
	 * @throws TimeoutException 
	 * @throws NoSuchElementException 
	 * @ 
	 */
	public String read(String xpath) throws NoSuchElementException, TimeoutException   
	{
		String xpathValue = parser.getText(xpath);
		return xpathValue;
	}



	/**
	 * 
	 * @param xpath
	 * @throws TimeoutException 
	 * @throws NoSuchElementException 
	 * @ 
	 */
	public void click(String xpath) throws NoSuchElementException, TimeoutException   
	{
		parser.clickButton(xpath);
	}


	/**
	 * stop and close the browser by user input "stop"
	 */
	public void stop()
	{
		parser.closeBrowser();
	}


	/**
	 * 
	 * @param varName
	 * @param value
	 */
	public void newVariable(String varName, String value) 
	{
		this.variables.put(varName, value);		
	}



	/**
	 * Executing the wait 
	 * @param millis : waiting time given in milliseconds
	 * 
	 */
	@SuppressWarnings("static-access")
	public void executeWait(int millis)
	{
		try 
		{
			Thread.currentThread().sleep(millis);
		} 
		catch (InterruptedException e) 
		{
			//			e.printStackTrace();
		}		
	}



	/**
	 * 
	 * @param names
	 * @param values
	 */
	public void showStatistc(EList<String> names, EList<String> values) 
	{
		// TODO signalize Stats to Gui		
	}






}
