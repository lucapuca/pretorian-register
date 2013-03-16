import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.apache.commons.io.IOUtils;

public class PretorianRegister {

	private URL url;

	public PretorianRegister(String url) throws MalformedURLException {
		this.url = new URL(url);
	}

	public String download() throws IOException {
		URLConnection con = url.openConnection();
		InputStream in = con.getInputStream();
		String encoding = con.getContentEncoding();
		encoding = encoding == null ? "UTF-8" : encoding;
		String page = IOUtils.toString(in, encoding);
		return page;
	}

}
