package webot.internet;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

//import javax.mail.MessagingException;
//import javax.mail.internet.AddressException;

public interface Notifier {

	/**
	 * Sended @message
	 * 
	 * Konstruktor f�r jeweiligen Notifier bekommt als �bergabeparamer die
	 * Adresse, Phone-Nummer o.�.
	 * 
	 * @param address
	 * @param message
	 * 
	 * @return String
	 * @throws IllegalArgumentException 
	 * @throws MessagingException 
	 * @throws AddressException 
	 */
	public String notify(String address, String subject, String message) throws AddressException, MessagingException, IllegalArgumentException;

}
