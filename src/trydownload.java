import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.jsoup.Connection;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;


public class trydownload {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
		String url="http://www.comune.milano.it/albopretorio/AlboPretorioWeb/showdoc.aspx?procid=52537";
		String url2="http://www.comune.milano.it/albopretorio/AlboPretorioWeb/savep7m.aspx";
		Connection con = Jsoup.connect(url);
		con.timeout(10000);
		Map<String, String> cookies = new HashMap<String, String>();

		// First request.
		Connection connection1 = Jsoup.connect(url);
		for (Entry<String, String> cookie : cookies.entrySet()) {
		    connection1.cookie(cookie.getKey(), cookie.getValue());
		}
		Response response1 = connection1.execute();
		cookies.putAll(response1.cookies());
		Document document1 = response1.parse();
		// Second request.
		Connection connection2 = Jsoup.connect(url2);
		for (Entry<String, String> cookie : cookies.entrySet()) {
		    connection2.cookie(cookie.getKey(), cookie.getValue());
		}
		Response response2 = connection2.ignoreContentType(true).execute();
		cookies.putAll(response2.cookies());
		FileOutputStream out = (new FileOutputStream(new java.io.File("test.pdf")));
	    out.write(response2.bodyAsBytes());
	    out.close();
		


	}

}
