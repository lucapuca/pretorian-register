import java.util.logging.Logger;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Deliberation implements Entity {
	private static final Logger logger = Logger.getLogger(Deliberation.class.getName());
	private static final String BASE_URL = "http://www.comune.milano.it/albopretorio/AlboPretorioWeb/";
	private String title;
	private String link;
	private String id;
	private String sector;
	private String year;

	public Deliberation() {
	}

	public Deliberation(String deliberation) {
		year = "2013";
		title = extractBetween(deliberation, ">", "</a>");
		link = BASE_URL + extractBetween(deliberation, "'", "' title");
		String id = "N"
				+ extractBetween(deliberation, "N. ", year + "</td>")
				+ year;
		sector = "Not Implemented";
		id = id.replace("<br> ", "");
		fix_year();
		logEntry();
	}

	public Deliberation(Element row){
		title = row.select("a[title=Visualizza Documento]").first().text().trim();
		link = BASE_URL + row.select("a[title=Visualizza Documento]").first().attr("href");
		Elements infos = row.select("td");
		sector = infos.get(1).text().trim();
		id = infos.get(2).text().trim();
		fix_year();
		logEntry();
	}
	
	private void fix_year(){
		String els[] = id.split(" ");
		if (els[2].equals("del")){
			//N. 18927/2013 del 20/02/2013
			year = els[1].split("/")[1];
		}else if (els[3].equals("del")){
			//N. 45 210121/2013 del 19/02/2013
			year = els[2].split("/")[1];
		}else{
			logger.warning("Cannot recognize year in id form \"" + id + "\"");
		}
	}
	
	private void logEntry(){
		logger.info("Title : " + title);
		logger.info("link : " + link);
		logger.info("settore : " + sector);
		logger.info("id : " + id);
		logger.info("year : " + year);
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

	public String year() {
		return year;
	}
	
	public String sector() {
		return sector;
	}

	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer("");
		return buffer.append(id).append(" ").append(title).append(" ")
				.append(link).toString();
	}
}
