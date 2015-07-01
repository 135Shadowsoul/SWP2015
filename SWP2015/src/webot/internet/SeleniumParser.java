package webot.internet;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import com.google.common.base.Function;


public class SeleniumParser implements InterfaceHTMLParser
{

	WebDriver driver;
	private boolean closeable;




	/**
	 * 
	 */
	public SeleniumParser()
	{
		closeable = true;
	}


	/**
	 * 
	 */
	public boolean isCloseable() 
	{
		return closeable;
	}


	/**
	 * 
	 */
	public void setCloseable(boolean closeable) 
	{
		this.closeable = closeable;
	}


	/**
	 * 
	 */
	public void openBrowser()
	{
		//TODO handle browsers

		try
		{
			System.setProperty("webdriver.chrome.driver", "F:/Program Files/chromedriver/chromedriver.exe");
			this.driver = new ChromeDriver();
		} 
		catch(Exception e) 
		{
			try
			{
				System.setProperty("webdriver.ie.driver", "F:/Program Files/IEDriver/IEDriverServer.exe");
				this.driver = new InternetExplorerDriver();
			} 
			catch(Exception f) 
			{
				try
				{
					this.driver = new FirefoxDriver();
				} 
				catch(Exception g) 
				{ 

				}

			}

		}
	}










	/**
	 * 
	 * @param username
	 * @param password
	 * @throws TimeoutException 
	 * @throws NoSuchElementException 
	 */
	public void login(String username, String password) throws NoSuchElementException, TimeoutException
	{
		//		WebElement userName_editbox = driver.findElement(By.name("loginName"));
		WebElement userName_editbox = fluentWait(By.name("loginName"));
		WebElement password_editbox = driver.findElement(By.name("password"));
		userName_editbox.sendKeys(username);
		password_editbox.sendKeys(password);
		password_editbox.submit();
	}


	public void connect(String url)
	{
		driver.navigate().to(url);
	}

	/**
	 * 
	 * @param locator
	 * @return
	 */
	public WebElement fluentWait(final By locator) throws NoSuchElementException, TimeoutException
	{
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
				.withTimeout(12, TimeUnit.SECONDS)
				.pollingEvery(2, TimeUnit.SECONDS);
//				.ignoring(NoSuchElementException.class);

		WebElement foo = wait.until(new Function<WebDriver, WebElement>() 
				{
			public WebElement apply(WebDriver driver) 
			{
				WebElement foundElement = driver.findElement(locator);
				return foundElement;
			}
				});

		return  foo;
	};


	/**
	 * @throws TimeoutException 
	 * @throws NoSuchElementException 
	 * 
	 */
	public void enterText(String xpath, String input) throws NoSuchElementException, TimeoutException 
	{
		WebElement textBox = fluentWait(By.xpath(xpath));
		textBox.sendKeys(input);
	}


	/**
	 * @throws TimeoutException 
	 * @throws NoSuchElementException 
	 * 
	 */
	public void clickButton(String xpath) throws  NoSuchElementException, TimeoutException
	{
		WebElement button = fluentWait(By.xpath(xpath));

		if(button == null) 
		{
			throw new NoSuchElementException("ERROR: Element not found at click " + xpath);
		}

		button.click();
	}


	/**
	 * @throws TimeoutException 
	 * @throws NoSuchElementException 
	 * 
	 */
	public String getText(String xpath) throws NoSuchElementException, TimeoutException
	{
		return fluentWait(By.xpath(xpath)).getText();
	}


	/**
	 * @throws TimeoutException 
	 * @throws NoSuchElementException 
	 * 
	 */
	public void logout() throws NoSuchElementException, TimeoutException
	{
		WebElement logoutButton = fluentWait(By.xpath("//*[@id='loginbox']/ul/li[7]/a"));

		if(logoutButton != null)
		{
			JavascriptExecutor js = (JavascriptExecutor)driver;
			js.executeScript("arguments[0].click();", logoutButton);
		}
	}

	public void closeBrowser()
	{
		if(closeable)
		{
			driver.close();
		}
	}

}
