package webot.internet;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import com.google.common.base.Function;

public class SeleniumParser implements InterfaceHTMLParser {

	WebDriver driver;
	private boolean closeable;

	/**
	 * Constructor
	 */
	public SeleniumParser() {
		closeable = true;
	}

	/**
	 * Method to decide the cloasable of a Browser
	 */
	public boolean isCloseable() {
		return closeable;
	}

	/**
	 * Sets the Closeable boolean
	 */
	public void setCloseable(boolean closeable) {
		this.closeable = closeable;
	}

	/**
	 * Open the browser
	 */
	public void openBrowser(String browser, String path) {

		if (browser.equals("Internet Explorer")) {
			try {
				System.setProperty("webdriver.ie.driver", path);
				this.driver = new InternetExplorerDriver();
			} catch (Exception e) {
				throw new IllegalArgumentException();
			}
		} else if (browser.equals("Chrome")) {
			try {
				System.setProperty("webdriver.chrome.driver", path);
				this.driver = new ChromeDriver();
			} catch (Exception e) {
				throw new IllegalArgumentException();
			}
		} else {
			try {
				this.driver = new FirefoxDriver();
			} catch (Exception g) {
			}
		}
	}

	/**
	 * Connect to the delivered url
	 */
	public void connect(String url) {
		driver.navigate().to(url);
	}

	/**
	 * FluentWait for the time out of the browser. Timed Out after 30 seconds
	 * Polling every 5 seconds
	 * 
	 * @param locator
	 * @return : WebElement
	 */
	public WebElement fluentWait(final By locator) throws NoSuchElementException, TimeoutException {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(30, TimeUnit.SECONDS).pollingEvery(5, TimeUnit.SECONDS);
		// .ignoring(NoSuchElementException.class);

		WebElement foo = wait.until(new Function<WebDriver, WebElement>() {
			public WebElement apply(WebDriver driver) {
				WebElement foundElement = driver.findElement(locator);
				return foundElement;
			}
		});

		return foo;
	};

	/**
	 * Write the text to the specific delivered xpath
	 * 
	 * @throws TimeoutException
	 * @throws NoSuchElementException
	 * 
	 */
	public void enterText(String xpath, String input) throws NoSuchElementException {
		try {
			WebElement textBox = fluentWait(By.xpath(xpath));
			textBox.sendKeys(input);
		} catch (Exception e) {
			throw new NoSuchElementException("ERROR: Element not found at write to: " + xpath);
		}
	}

	/**
	 * Execute a click at the delivered xpath
	 * 
	 * @throws TimeoutException
	 * @throws NoSuchElementException
	 * 
	 */
	public void clickButton(String xpath) throws NoSuchElementException {

		try {
			WebElement button = fluentWait(By.xpath(xpath));
			button.click();
		} catch (Exception e) {
			throw new NoSuchElementException("ERROR: Element not found at click " + xpath);
		}

	}

	/**
	 * Read out the value of the delivered xpath
	 * 
	 * @param xpath
	 *            : Path to read at
	 * 
	 * @return String
	 * 
	 * @throws NoSuchElementException
	 * @throws TimeoutException
	 */
	public String getText(String xpath) throws NoSuchElementException, TimeoutException {
		String out = "";
		try {
			out = fluentWait(By.xpath(xpath)).getText();
		} catch (Exception e) {
			throw new NoSuchElementException("ERROR: Element not found at read " + xpath);
		}
		return out;
	}

	/**
	 * Close the browser
	 */
	public void closeBrowser() {
		if (closeable) {
			try {
				driver.close();
			} catch (Exception e) {
			}
		}
	}

}
