import java.io.FileInputStream;
import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;

public class MailDeliberationNotifier implements DeliberationNotifier {

	@Override
	public void send(Deliberation deliberation) throws Exception {
		Properties properties = new Properties();

		properties.load(new FileInputStream("etc/config.properties"));

		final String username = properties.getProperty("mail.user");
		final String password = properties.getProperty("mail.password");
		final String destination = properties.getProperty("mail.destination");

		Properties mailProperties = new Properties();
		mailProperties.put("mail.smtp.auth", "true");
		mailProperties.put("mail.smtp.starttls.enable", "true");
		mailProperties.put("mail.smtp.host", "smtp.gmail.com");
		mailProperties.put("mail.smtp.port", "587");

		mailProperties.put("mail.from", username);

		Session session = Session.getInstance(mailProperties, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		MimeMessage msg = new MimeMessage(session);
		msg.setFrom();
		msg.setRecipients(Message.RecipientType.TO, destination);
		msg.setSubject("Nuova delibera dall'albo pretorio: " + deliberation.id());
		msg.setSentDate(new Date());
		msg.setText(deliberation.title() + " - " + deliberation.link());
		Transport.send(msg);
	}

}
