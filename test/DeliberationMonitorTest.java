import java.util.Arrays;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Test;

public class DeliberationMonitorTest {

	Mockery context = new Mockery();

	final DeliberationParser parser = context.mock(DeliberationParser.class);
	final DeliberationRepository repository = context.mock(DeliberationRepository.class);
	final DeliberationNotifier sender = context.mock(DeliberationNotifier.class);
	final Deliberation deliberation = new Deliberation();

	final String id = "id";
	final String link = "link";
	final String title = "title";

	@Test
	public void shouldSendNewDeliberation() throws Exception {
		context.checking(new Expectations() {
			{
				oneOf(parser).parse();
				will(returnValue(Arrays.asList(deliberation)));

				oneOf(repository).exists(deliberation);
				will(returnValue(false));

				oneOf(sender).send(deliberation);
			}
		});

		DeliberationMonitor monitor = new DeliberationMonitor(parser, repository, sender);
		monitor.run();
		context.assertIsSatisfied();
	}

	@Test
	public void shouldNotNotifyTwiceTheSameDeliberation() throws Exception {
		context.checking(new Expectations() {
			{
				oneOf(parser).parse();
				will(returnValue(Arrays.asList(deliberation)));

				oneOf(repository).exists(deliberation);
				will(returnValue(true));

				never(sender).send(deliberation);
			}
		});

		DeliberationMonitor monitor = new DeliberationMonitor(parser, repository, sender);
		monitor.run();
		context.assertIsSatisfied();
	}

}
