package weBot;

public class WeBot extends Thread{

	private Selenium_Parser parser;
	private EMailNotifier eNotify;
	private GUI gui;
	
	public static void main(String[] args) {
		WeBot bot = new WeBot();
		bot.start();
	}
	
	public void run()
	{
		try{
			Selenium_Parser parser = new Selenium_Parser();
			EMailNotifier eNotify = new EMailNotifier("swp2015mail@gmail.com");
			gui = new GUI();
			gui.setVisible(true);
//			exceptionTest();
		} catch (Exception e)
		{
			handleExceptions(e.getMessage());
		}
	}
	
	public void handleExceptions(String errorMessage)
	{
		System.out.println(errorMessage);
		gui.writeToStatusArea("Error: " + errorMessage + "\n");
	}

	public void exceptionTest() throws Exception
	{
		throw new Exception("Exception erkannt");
	}
}
