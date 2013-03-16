import static org.junit.Assert.assertEquals;

import java.io.FileReader;

import org.junit.Test;

public class DeliberationParserTest {

	@Test
	public void shouldParseDeliberations() throws Exception {
		String page = webPageContent();
		DeliberationParser deliberationParser = new DeliberationParser(page);
		deliberationParser.parse();

		assertEquals(9, deliberationParser.size());
		assertEquals("PARERE SUL PERMESSO DI COSTRUIRE IN VIA PERTICARI 23.",
				deliberationParser.get(0).title());
	}

	private String webPageContent() throws Exception {
		return readFile("test/fixtures/deliberations.txt");
	}

	private String readFile(String fileName) throws Exception {
		FileReader in = new FileReader(fileName);
		StringBuilder contents = new StringBuilder();
		char[] buffer = new char[4096];
		int read = 0;
		do {
			contents.append(buffer, 0, read);
			read = in.read(buffer);
		} while (read >= 0);
		in.close();
		return contents.toString();
	}

}
