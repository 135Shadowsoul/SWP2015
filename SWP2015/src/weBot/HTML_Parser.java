package weBot;

public interface HTML_Parser {

	public void openBrowser() throws Exception;

	public void enterText(String xpath, String input) throws Exception;

	public void clickButton(String xpath) throws Exception;

	public String getText(String xpath) throws Exception;

	public void closeBrowser();

}
