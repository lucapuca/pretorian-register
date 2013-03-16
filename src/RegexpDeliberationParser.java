import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexpDeliberationParser implements DeliberationParser {

	private PretorianRegister register;

	public RegexpDeliberationParser(PretorianRegister register) {
		this.register = register;
	}

	public List<Entity> parse() {
		List<Entity> deliberations = new ArrayList<Entity>();
		String page;
		try {
			page = register.download().replaceAll("\\r", "").replaceAll("\\n", "");
			;
			String pattern = "href='showdoc.aspx.*?N\\. \\d+[^h]*";
			Pattern p = Pattern.compile(pattern);
			Matcher matcher = p.matcher(page);
			while (matcher.find()) {
				deliberations.add(new Deliberation(matcher.group()));
			}
			return deliberations;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
