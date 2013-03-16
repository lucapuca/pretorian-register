import java.io.IOException;
import java.util.List;

public class DeliberationNotifier {

	private DeliberationParser parser;

	public DeliberationNotifier(DeliberationParser parser) {
		this.parser = parser;
	}

	public void run() throws IOException {
		List<Deliberation> deliberations = parser.parse();
		for (Deliberation deliberation : deliberations) {
			System.out.println(deliberation.toString());
		}
	}

}
