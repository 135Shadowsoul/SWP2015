package webot.internet;

import java.util.concurrent.TimeoutException;
import org.openqa.selenium.NoSuchElementException;

public interface InterfaceHTMLParser 
{	
	public void openBrowser();
	
	public void enterText(String xpath, String input) throws NoSuchElementException, TimeoutException;
	
	public void clickButton(String xpath) throws NoSuchElementException, TimeoutException;
	
	public String getText(String xpath) throws NoSuchElementException, TimeoutException;
	
	public void closeBrowser();
	
	public boolean isCloseable();

	public void setCloseable(boolean closeable);
	
	public void connect(String url);
	
}
