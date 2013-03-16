import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DeliberationParser {

	private String page;
	private List<Deliberation> deliberations;

	public DeliberationParser(String page) {
		this.page = page.replaceAll("\\r", "").replaceAll("\\n", "");
		this.deliberations = new ArrayList<Deliberation>();
	}

	public List<Deliberation> parse() {
		String pattern = "href='showdoc.aspx.*?N\\. \\d+[^h]*";
		Pattern p = Pattern.compile(pattern);
		Matcher matcher = p.matcher(page);
		while (matcher.find()) {
			deliberations.add(new Deliberation(matcher.group()));
		}
		return deliberations;
	}

	public int size() {
		return deliberations.size();
	}

	public Deliberation get(int index) {
		return deliberations.get(0);
	}

}
