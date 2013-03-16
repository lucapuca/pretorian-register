import org.junit.Ignore;
import org.junit.Test;

public class MailDeliberationNotifierIntegrationTest {

	@Test
	@Ignore
	public void shouldSendMail() throws Exception {
		Deliberation deliberation = new Deliberation() {
			public String id() {
				return "id";
			};

			@Override
			public String link() {
				return "link";
			}

			@Override
			public String title() {
				return "title";
			}
		};
		MailDeliberationNotifier notifier = new MailDeliberationNotifier();
		notifier.send(deliberation);
	}
}
