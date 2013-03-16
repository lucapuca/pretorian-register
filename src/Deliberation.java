public class Deliberation {

	private static final String BASE_URL = "http://www.comune.milano.it/albopretorio/AlboPretorioWeb/";
	private String title;
	private String link;
	private String id;

	public Deliberation(String deliberation) {
		this.title = extractBetween(deliberation, ">", "</a>");
		this.link = BASE_URL + extractBetween(deliberation, "'", "' title");
		String id = "N" + extractBetween(deliberation, "N. ", "2013</td>")
				+ "2013";
		this.id = id.replace("<br> ", "");
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
