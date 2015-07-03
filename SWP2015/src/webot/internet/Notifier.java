package webot.internet;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

//import javax.mail.MessagingException;
//import javax.mail.internet.AddressException;

public interface Notifier 
{

	/**
	 * Sended @message
	 * 
	 * Constructor for the respective Notifiers.
	 * Gets the following parameters:
	 * 
	 * @param address : The user given email address
	 * @param subject : The subject of the email
	 * @param message : The message of the email
	 * 
	 * @return : String
	 * 
	 * @throws AddressException
	 * @throws MessagingException
	 * @throws IllegalArgumentException
	 */
	public String notify(String address, String subject, String message) 
			throws AddressException, MessagingException, IllegalArgumentException;

}
