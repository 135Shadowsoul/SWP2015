package weBot;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

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
	 * @throws MessagingException 
	 * @throws AddressException 
	 */
	public String notify(String subject, String message) throws Exception;

}
