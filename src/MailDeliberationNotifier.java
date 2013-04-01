import java.util.Date;
import java.util.Properties;
import java.util.logging.Logger;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;

public class MailDeliberationNotifier implements DeliberationNotifier {
	private static final Logger logger = Logger.getLogger(Main.class.getName());

	private MailDeliberationNotifier(String destination, String subject) {
		super();
		this.destination = destination;
		this.subject = subject;
	}

	private MailDeliberationNotifier(String destination) {
		this(destination, def_subject);
	}

	private static Properties mailProperties = null;

	private static String from_user = null;
	private static String pwd = null;

	private final String destination;

	private final static String def_subject = "Nuova delibera dall'albo pretorio: ";
	private String subject;

	private MimeMessage buildMessage(Session session, Deliberation deliberation)
			throws MessagingException {
		MimeMessage msg = new MimeMessage(session);
		msg.setFrom();
		msg.setRecipients(Message.RecipientType.TO, destination);
		msg.setSubject(subject + deliberation.id());
		msg.setSentDate(new Date());
		msg.setText(deliberation.title() + " - " + deliberation.link());
		return msg;
	}

	@Override
	public void send(Deliberation deliberation) throws MessagingException {
		Session session = Session.getInstance(mailProperties,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(from_user, pwd);
					}
				});
		MimeMessage msg = buildMessage(session, deliberation);

		logger.info("Sending mail from <" + from_user + "> to <" + destination
				+ "> [sbj:" + msg.getSubject() + "]");

		Transport.send(msg);
	}

	public static synchronized void setUpFactory(String from, String pwd,
			Properties mailProperties) {
		MailDeliberationNotifier.mailProperties = (Properties) mailProperties
				.clone();
		MailDeliberationNotifier.from_user = from;
		MailDeliberationNotifier.pwd = pwd;
	}

	public static synchronized MailDeliberationNotifier getNotifier(
			String destination, String subject) throws Exception {
		checkConfiguration();
		return new MailDeliberationNotifier(destination, subject);
	}
	
	public static synchronized MailDeliberationNotifier getNotifier(
			String destination) throws Exception {
		return getNotifier(destination,def_subject);
	}

	private static void checkConfiguration() throws Exception {
		if (MailDeliberationNotifier.mailProperties == null
				|| MailDeliberationNotifier.from_user == null
				|| MailDeliberationNotifier.pwd == null) {
			throw new Exception(
					"You must configure the factory by setUpFactory() method before");
		}
	}

	public static synchronized String getFrom_user() {
		return from_user;
	}

	public String getDestination() {
		return destination;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

}
