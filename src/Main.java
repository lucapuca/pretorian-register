import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import org.apache.commons.io.IOUtils;

public class Main {

	public static void main(String[] args) throws Exception {
		URL url = new URL(
				"http://www.comune.milano.it/albopretorio/AlboPretorioWeb/AlboPretorio.aspx?dtid=13060");
		URLConnection con = url.openConnection();
		InputStream in = con.getInputStream();
		String encoding = con.getContentEncoding();
		encoding = encoding == null ? "UTF-8" : encoding;
		String page = IOUtils.toString(in, encoding);

		DeliberationParser parser = new DeliberationParser(page);
		List<Deliberation> deliberations = parser.parse();
		for (Deliberation deliberation : deliberations) {
			System.out.println(deliberation.toString());
		}
	}
}
