import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class JSoupDeliberationParser implements DeliberationParser {

	private String url;
	private File file;
	private int timeout;
	public static final int def_timeout = 10000;
	
	public JSoupDeliberationParser(String url, int timeout){
		this.url = url;
		this.file = null;
		this.timeout = timeout;
	}
	public JSoupDeliberationParser(String url){
		this(url,def_timeout);
		this.file = null;
	}
	
	public JSoupDeliberationParser(File file){
		this.url = null;
		this.file = file;
	}
	
	@Override
	public List<Entity> parse() {
		List<Entity> deliberations = new ArrayList<Entity>();
		try {
			Document doc = null;
			if(this.url != null){
				Connection con = Jsoup.connect(this.url);
				con.timeout(timeout);
				doc = con.get();
			}else if (this.file != null){ 
				doc = Jsoup.parse(this.file,null);
			}else{
				throw new RuntimeException("You must set a valid url or file");
			}
			Elements rows =	doc.select("tr.item0"); //All rows with class='item0'
			rows.addAll(doc.select("tr.item1")); //All rows wuth class='item1'
			for(Element row : rows){
				deliberations.add(new Deliberation(row));
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return deliberations;
	}

}
