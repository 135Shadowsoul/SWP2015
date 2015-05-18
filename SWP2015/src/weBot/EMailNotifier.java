package weBot;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


/**
 * 
 * Benötigt javax.mail.jar
 * Download von https://java.net/projects/javamail/pages/Home
 *
 */
public class EMailNotifier implements Notifier {

	static Properties mailServerProperties;
	static Session getMailSession;
	static MimeMessage generateMailMessage;

	final String username = "swp2015mail";
	final String password = "SWP-2015";

	/**
	 * Konstruktor. erstellt neuen EMailNotifier
	 */
	public EMailNotifier() {

	}

	@Override
	public String notify(String address, String message) {

		String returnString = "";

		if (address.contains("@") && address.split("@")[1].contains(".") && address.split("@")[1].split("\\.").length == 2) {

			try {

				mailServerProperties = System.getProperties();
				mailServerProperties.put("mail.smtp.port", "587");
				mailServerProperties.put("mail.smtp.auth", "true");
				mailServerProperties.put("mail.smtp.starttls.enable", "true");

				getMailSession = Session.getDefaultInstance(mailServerProperties, null);
				generateMailMessage = new MimeMessage(getMailSession);
				generateMailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(address));
				generateMailMessage.setSubject("Problem occured!!");
				generateMailMessage.setContent(message, "text/html");
				
				Transport transport = getMailSession.getTransport("smtp");

				transport.connect("smtp.gmail.com", "swp2015mail@gmail.com", "SWP-2015");
				transport.sendMessage(generateMailMessage, generateMailMessage.getAllRecipients());
				transport.close();

			} catch (Exception e) {
				returnString = "Fehler beim senden der Mail!!";
			}

		} else
			returnString = "Invalid E-Mail Address!!";
		return returnString;

	}

	public static void main(String[] args) {

		EMailNotifier not = new EMailNotifier();
		not.notify("swp2015mail@gmail.com", "Das ist eine Testmail." + "\n" + "Automatisch Gesendet!");
		System.out.println("Mail gesendet!");
	}

}
