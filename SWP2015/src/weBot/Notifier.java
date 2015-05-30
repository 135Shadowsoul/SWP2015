package weBot;

public interface Notifier {

	/**
	 * Sended @message an @address (Phone, Mail...)
	 * 
	 * @param address
	 * @param message
	 * 
	 * @return String
	 * 
	 */
	public String notify(String subject, String message);

}
