package webot.logic;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeoutException;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.apache.commons.lang3.math.NumberUtils;
import org.eclipse.emf.common.util.EList;
import org.openqa.selenium.NoSuchElementException;

import webot.internet.EMailNotifier;
import webot.internet.InterfaceHTMLParser;
import webot.internet.Notifier;
import webot.internet.SeleniumParser;
import webot.watchValue.WatchValue;

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
	 * Shows statistic values or values that should be observed
	 * 
	 * @param names
	 * @param values
	 * @throws TimeoutException 
	 * @throws NoSuchElementException 
	 */
	public List<WatchValue> showStatistc(EList<String> names, EList<String> values) throws NoSuchElementException, TimeoutException 
	{
		List<WatchValue> list = new LinkedList<WatchValue>();
		
		for (int i = 0; i < values.size(); i++) 
		{
			String stringValue = this.read(values.get(i));
			String stringName = names.get(i);
			
			list.add(new WatchValue(stringName, stringValue));
		}
		
		
		return list ;	
	}


	/**
	 * 
	 * @param name
	 * @return
	 */
	public String readVariable(String name)
	{
		return variables.get(name);
	}



	public boolean rateIf(String left, String right, String operant) {
		
		if (operant.equals("==") || operant.equals("="))
		{
			return identical(left, right);
		}
		
		if (operant.equals("!="))
		{
			return !identical(left, right);
		}
		
		if (operant.equals("<"))
		{
			return lowerThan(left, right);
		}
		
		if (operant.equals("<="))
		{
			return lowerThan(left, right) || identical(left, right);
		}
		
		if (operant.equals(">="))
		{
			return lowerThan(right, left) || identical(left, right);
		}
		
		if (operant.equals(">"))
		{
			return lowerThan(right, left);
		}
		
		return false;
	}



	/**
	 * Decide if teh left String is equals or identical the right String
	 * 
	 * @param left
	 * @param right
	 * @return
	 */
	private boolean identical(String left, String right) 
	{
		if(left.equals(right))
		{
			return true;
		}
		
		double leftNum = 0;
		double rightNum = 0;
		
		try
		{
			leftNum = findNumeric(left);
			rightNum = findNumeric(right);			
		}
		catch (NumberFormatException e)
		{
			return false;
		}
		
		return leftNum == rightNum;
	}



	/**
	 * Compares whether the numbers given within left is lower than the numbers given within right
	 * 
	 * @param leftString
	 * @param rightString
	 * @return
	 * @throws NumberFormatException
	 */
	private boolean lowerThan(String leftString, String rightString) throws NumberFormatException
	{
		double left = 0;
		double right = 0 ;
		
		left = findNumeric(leftString);
		right = findNumeric(rightString);
		
		return left < right;
	}



	private static double findNumeric(String dirtyValue) throws NumberFormatException
	{
		String cleanValue = "";
		
		for(int i = 0; i < dirtyValue.length(); i++)
		{
			String lookAt = dirtyValue.substring(i, i + 1);
			
			if(NumberUtils.isNumber(lookAt))
			{
				cleanValue += dirtyValue.substring(i, i + 1);
			}
			
			if(dirtyValue.substring(i, i + 1).equals(".") || dirtyValue.substring(i, i + 1).equals(","))
			{
				if(!(cleanValue.isEmpty()) && !(cleanValue.contains(".")))
				{
					cleanValue += ".";					
				}
			}
		}
		
		return Double.parseDouble(cleanValue);
	}
	
	
}
