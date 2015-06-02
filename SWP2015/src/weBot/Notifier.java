package weBot;

public interface Notifier {

	/**
	 * Sended @message
	 * 
	 * Konstruktor für jeweiligen Notifier bekommt als Übergabeparamer die
	 * Adresse, Phone-Nummer o.ä.
	 * 
	 * @param address
	 * @param message
	 * 
	 * @return String
	 */
	public String notify(String subject, String message);

}
