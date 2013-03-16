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

	public void run() throws Exception {
		List<Entity> deliberations = parser.parse();
		for (Entity deliberationEntity : deliberations) {
			Deliberation deliberation = (Deliberation) deliberationEntity;
			if (!repository.exists(deliberation)) {
				repository.save(deliberation);
				sender.send((Deliberation) deliberation);
			}
		}
	}
}
