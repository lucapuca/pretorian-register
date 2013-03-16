import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DeliberationParser {

	private PretorianRegister register;

	public DeliberationParser(PretorianRegister register) {
		this.register = register;
	}

	public List<Deliberation> parse() throws IOException {
		List<Deliberation> deliberations = new ArrayList<Deliberation>();
		String page = register.download().replaceAll("\\r", "")
				.replaceAll("\\n", "");
		;
		String pattern = "href='showdoc.aspx.*?N\\. \\d+[^h]*";
		Pattern p = Pattern.compile(pattern);
		Matcher matcher = p.matcher(page);
		while (matcher.find()) {
			deliberations.add(new Deliberation(matcher.group()));
		}
		return deliberations;
	}

}
