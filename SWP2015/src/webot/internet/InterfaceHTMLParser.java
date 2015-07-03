package webot.internet;

import java.util.concurrent.TimeoutException;
import org.openqa.selenium.NoSuchElementException;

public interface InterfaceHTMLParser 
{	
	/**
	 * Open the browser
	 */
	public void openBrowser();
	
	/**
	 * Write the text to the specific delivered xpath
	 * 
	 * @throws TimeoutException 
	 * @throws NoSuchElementException 
	 * 
	 */
	public void enterText(String xpath, String input) throws NoSuchElementException, TimeoutException;
	
	/**
	 * Execute a click at the delivered xpath
	 * 
	 * @throws TimeoutException 
	 * @throws NoSuchElementException 
	 * 
	 */
	public void clickButton(String xpath) throws NoSuchElementException, TimeoutException;
	
	/**
	 * Read out the value of the delivered xpath
	 * 
	 * @param xpath : Path to read at
	 * 
	 * @return String
	 * 
	 * @throws NoSuchElementException
	 * @throws TimeoutException
	 */
	public String getText(String xpath) throws NoSuchElementException, TimeoutException;
	
	/**
	 * Close the browser
	 */
	public void closeBrowser();
	
	/**
	 * Method to decide the cloasable of a Browser
	 * 
	 * @return : True if the browser is closabel, else false
	 */
	public boolean isCloseable();

	/**
	 * Sets the Closeable boolean
	 */
	public void setCloseable(boolean closeable);
	
	/**
	 * Connect to the delivered url
	 */
	public void connect(String url);
	
}
