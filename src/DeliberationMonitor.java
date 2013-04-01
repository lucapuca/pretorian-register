import java.util.List;

public class DeliberationMonitor {

	private DeliberationParser parser;
	private DeliberationRepository repository;
	private DeliberationNotifier sender;
	private final String name;
	private final String description;

	public DeliberationMonitor(String name, String description,
			DeliberationParser parser, DeliberationRepository repository,
			DeliberationNotifier sender) {
		this.name = name;
		this.description = description;
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

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}
}
