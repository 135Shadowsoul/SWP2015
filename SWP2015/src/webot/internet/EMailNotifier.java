package webot.internet;

import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.sun.mail.smtp.SMTPTransport;

/**
 * 
 * Needs javax.mail.jar download from
 * https://java.net/projects/javamail/pages/Home
 *
 */
public class EMailNotifier implements Notifier {

	/**
	 * Constructor
	 */
	public EMailNotifier() {
		super();
	}

	public String notify(String address, String subject, String message) throws AddressException, MessagingException, IllegalArgumentException {

		if (address.contains("@") && address.split("@")[1].contains(".") && address.split("@")[1].split("\\.").length >= 2 || address == null) {

		} else {
			throw new IllegalArgumentException("Ungueltige Mailaddresse!!");
		}

		String returnString = "";

		Properties props = System.getProperties();
		props.put("mail.smtps.host", "smtp.mailgun.org");
		props.put("mail.smtps.auth", "true");

		Session session = Session.getInstance(props, null);
		Message msg = new MimeMessage(session);

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

		return returnString;
	}

}
