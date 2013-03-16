import java.io.IOException;
import java.util.List;

public class DeliberationMonitor {

	private DeliberationParser parser;
	private DeliberationRepository repository;
	private DeliberationNotifier sender;

	public DeliberationMonitor(DeliberationParser parser, DeliberationRepository repository, DeliberationNotifier sender) {
		this.parser = parser;
		this.repository = repository;
		this.sender = sender;
	}

	public void run() throws IOException {
		List<Entity> deliberations = parser.parse();
		for (Entity deliberation : deliberations) {
			if (!repository.exists(deliberation)) {
				sender.send((Deliberation) deliberation);
			}
			System.out.println(deliberation.toString());
		}
	}
}
