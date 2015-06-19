package weBot;

import java.util.concurrent.TimeUnit;

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

public class Selenium_Parser implements HTML_Parser {

	WebDriver driver;
	Logger logger;

	public static void main(String[] args) {
		Selenium_Parser parser = new Selenium_Parser();
		try {
			parser.openBrowser();
		} catch (Exception e) {
			System.out.println("no browser found");
		}
		try {
			parser.openTestSite();
			parser.login("swp2015", "SWP-2015");
			System.out.println(parser.getText("//*[@id='FAZPartnerContentInner']/div[2]/div[2]/table/tbody/tr[4]/td[2]/span"));
			parser.logout();
		} catch (Exception e) {

		} finally {
			parser.closeBrowser();
		}

	}

	public Selenium_Parser() {
		logger = new Logger();
	}

	public void openBrowser() throws Exception {
		// Internet Explorer
		// System.setProperty("webdriver.ie.driver",
		// "F:/Program Files/IEDriver/IEDriverServer.exe");
		// this.driver = new InternetExplorerDriver();
		// driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);

		// Google Chrome
		// System.setProperty("webdriver.chrome.driver",
		// "F:/Program Files/chromedriver/chromedriver.exe");
		// this.driver = new ChromeDriver();

		// Firefox
		// this.driver = new FirefoxDriver();

		try {
			System.setProperty("webdriver.chrome.driver", "F:/Program Files/chromedriver/chromedriver.exe");
			this.driver = new ChromeDriver();
		} catch (Exception e) {
			try {
				System.setProperty("webdriver.ie.driver", "F:/Program Files/IEDriver/IEDriverServer.exe");
				this.driver = new InternetExplorerDriver();
			} catch (Exception f) {
				try {
					this.driver = new FirefoxDriver();
				} catch (Exception g) {
				}
			}
		}
	}

	public void openTestSite() {
		driver.navigate().to("https://www.faz.net/mein-faz-net/?redirectUrl=$boersens$/a/depot.cgi");
	}

	public void login(String username, String password) {
		// WebElement userName_editbox =
		// driver.findElement(By.name("loginName"));
		WebElement userName_editbox = fluentWait(By.name("loginName"));
		WebElement password_editbox = driver.findElement(By.name("password"));
		userName_editbox.sendKeys(username);
		password_editbox.sendKeys(password);
		password_editbox.submit();
	}

	public WebElement fluentWait(final By locator) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(30, TimeUnit.SECONDS).pollingEvery(5, TimeUnit.SECONDS).ignoring(NoSuchElementException.class);

		WebElement foo = wait.until(new Function<WebDriver, WebElement>() {
			public WebElement apply(WebDriver driver) {
				logger.addLog("Reached text box content ");
				return driver.findElement(locator);
			}
		});

		return foo;
	};

	public void enterText(String xpath, String input) throws Exception {
		WebElement textBox = fluentWait(By.xpath(xpath));
		textBox.sendKeys(input);
		logger.addLog("Wrote: " + input + " to text box ");
	}

	public void clickButton(String xpath) throws Exception {
		WebElement button = fluentWait(By.xpath(xpath));
		button.click();
		logger.addLog("Clicked button ");
	}

	public String getText(String xpath) throws Exception {
		return fluentWait(By.xpath(xpath)).getText();
	}

	public void logout() {
		WebElement logoutButton = fluentWait(By.xpath("//*[@id='loginbox']/ul/li[7]/a"));
		if (logoutButton != null) {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].click();", logoutButton);
		}
	}

	public void closeBrowser() {
		driver.close();
		logger.addLog("Browser closed ");
	}

}
