package weBot;

public interface Notifier {

	/**
	 * Sended @message an @address (Phone, Mail...)
	 * 
	 * @param address
	 * @param message
	 * 
	 * @return "Message: " + message + "\n" + "Send to: " + address
	 */
	public String notify(String address, String message);

}
