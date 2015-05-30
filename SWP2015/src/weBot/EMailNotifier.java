package weBot;

import java.util.Properties;
import java.util.Date;
import javax.mail.*;
import javax.mail.internet.*;
import com.sun.mail.smtp.*;

/**
 * 
 * Benötigt javax.mail.jar Download von
 * https://java.net/projects/javamail/pages/Home
 *
 */
public class EMailNotifier implements Notifier {

	// static Properties mailServerProperties;
	// static Session getMailSession;
	// static MimeMessage generateMailMessage;
	//
	// final String username = "swp2015mail";
	// final String password = "SWP-2015";

	String address;

	/**
	 * Konstruktor. erstellt neuen EMailNotifier
	 */
	public EMailNotifier(String address) {
		if (address.contains("@") && address.split("@")[1].contains(".") && address.split("@")[1].split("\\.").length >= 2 || address == null) {
			this.address = address;
		} else
			throw new IllegalArgumentException("Ungültige Mailaddresse!!");
	}

	// @Override
	// public String notify(String address, String subject, String message) {
	//
	// String returnString = "";
	//
	// if (address.contains("@") && address.split("@")[1].contains(".") &&
	// address.split("@")[1].split("\\.").length >= 2) {
	//
	// try {
	//
	// mailServerProperties = System.getProperties();
	// mailServerProperties.put("mail.smtp.port", "587");
	// mailServerProperties.put("mail.smtp.auth", "true");
	// mailServerProperties.put("mail.smtp.starttls.enable", "true");
	//
	// getMailSession = Session.getDefaultInstance(mailServerProperties, null);
	// generateMailMessage = new MimeMessage(getMailSession);
	// generateMailMessage.addRecipient(Message.RecipientType.TO, new
	// InternetAddress(address));
	// generateMailMessage.setSubject(subject);
	// generateMailMessage.setContent(message, "text/html");
	//
	// Transport transport = getMailSession.getTransport("smtp");
	//
	// transport.connect("smtp.gmail.com", "swp2015mail@gmail.com", "SWP-2015");
	// transport.sendMessage(generateMailMessage,
	// generateMailMessage.getAllRecipients());
	// transport.close();
	//
	// } catch (Exception e) {
	// returnString = "Fehler beim senden der Mail!!";
	// }
	//
	// } else
	// returnString = "Invalid E-Mail Address!!";
	// return returnString;
	//
	// }

	@Override
	public String notify(String subject, String message) {

		String returnString = "";

		Properties props = System.getProperties();
		props.put("mail.smtps.host", "smtp.mailgun.org");
		props.put("mail.smtps.auth", "true");
		Session session = Session.getInstance(props, null);
		Message msg = new MimeMessage(session);
		try {
			msg.setFrom(new InternetAddress("swp2015mail@gmail.com"));
			msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(address, false));
			msg.setSubject(subject);
			msg.setText(message);
			msg.setSentDate(new Date());
			SMTPTransport t = (SMTPTransport) session.getTransport("smtps");
			t.connect("smtp.mailgun.com", "postmaster@sandbox5d14333a2cff4996a9647455f8d86212.mailgun.org", "03b80f5bb1843397fedb3f8a3f8c527f");
			t.sendMessage(msg, msg.getAllRecipients());
			t.close();
			returnString = "Mail erfolgreich an " + address + " gesendet.";
		} catch (Exception e) {
			returnString = "Fehler beim Versenden der Nachricht!!";
		}
		return returnString;
	}

	public static void main(String[] args) {
		EMailNotifier not = new EMailNotifier("andreas.knapp.135@gmail.com");
		System.out.println(not.notify("Test", "Das ist eine Testmail." + "\n" + "Automatisch Gesendet!"));
	}

}
