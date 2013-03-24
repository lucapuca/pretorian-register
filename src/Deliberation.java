import java.util.logging.Logger;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Deliberation implements Entity {
	private static final Logger logger = Logger.getLogger("Deliberation");
	private static final String BASE_URL = "http://www.comune.milano.it/albopretorio/AlboPretorioWeb/";
	private String title;
	private String link;
	private String id;
	private String year;

	public Deliberation() {
	}

	public Deliberation(String deliberation) {
		this.year = "2013";
		this.title = extractBetween(deliberation, ">", "</a>");
		this.link = BASE_URL + extractBetween(deliberation, "'", "' title");
		String id = "N"
				+ extractBetween(deliberation, "N. ", this.year + "</td>")
				+ this.year;
		this.id = id.replace("<br> ", "");
		this.fix_year();
		this.logEntry();
	}

	public Deliberation(Element row){
		this.title = row.select("a[title=Visualizza Documento]").first().text().trim();
		this.link = BASE_URL + row.select("a[title=Visualizza Documento]").first().attr("href");
		Elements infos = row.select("td");
		String z = infos.get(1).text().trim();
		this.id = infos.get(2).text().trim();
		this.fix_year();
		this.logEntry();
	}
	
	private void fix_year(){
		this.year = this.id.split(" ")[2].split("/")[1];
	}
	
	private void logEntry(){
		logger.info("Title : " + this.title);
		logger.info("link : " + this.link);
		logger.info("id : " + this.id);
		logger.info("year : " + this.year);
	}

	private String extractBetween(String deliberation, String start, String end) {
		return deliberation.substring(deliberation.indexOf(start) + 1,
				deliberation.indexOf(end)).trim();
	}

	public String title() {
		return title;
	}

	public String link() {
		return link;
	}

	public String id() {
		return id;
	}

	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer("");
		return buffer.append(id).append(" ").append(title).append(" ")
				.append(link).toString();
	}
}
