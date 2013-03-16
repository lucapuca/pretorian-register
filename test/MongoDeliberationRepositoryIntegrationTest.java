import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class MongoDeliberationRepositoryIntegrationTest {

	private DeliberationRepository repository = new MongoDeliberationRepository();

	@Test
	public void shouldNotExist() throws Exception {
		final String id = "not existent";
		Deliberation deliberation = new Deliberation() {
			@Override
			public String id() {
				return id;
			}
		};

		assertFalse(repository.exists(deliberation));
	}

	@Test
	public void shouldExists() throws Exception {
		final String id = "1";
		Deliberation deliberation = new Deliberation() {
			@Override
			public String id() {
				return id;
			}
		};

		repository.save(deliberation);

		assertTrue(repository.exists(deliberation));
	}

}
