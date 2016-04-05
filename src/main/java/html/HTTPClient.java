package html;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Timestamp;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.IOUtils;
import org.htmlparser.util.ParserException;


public class HTTPClient {

	public static String getHTMLContent(String urlstring) throws IOException{
		
		URL url = new URL(urlstring);
		URLConnection con = url.openConnection();
		InputStream in = con.getInputStream();
		String encoding = con.getContentEncoding();
		encoding = encoding == null ? "UTF-8" : encoding;
		
		return IOUtils.toString(in, encoding);
	}
	
	public static Timestamp getLastMod(String urlstring) throws IOException {
		
		URL url = new URL(urlstring);
	    HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();

	    long date = httpCon.getLastModified();
	    if (date == 0) return new Timestamp((new Date()).getTime()); // No last-modified information -> assume current
	    else return new Timestamp(date);
	}

	public static String getTitle(String htmlContent) throws ParserException {

		htmlContent = htmlContent.replaceAll("\\s+", " ");
	    Pattern p = Pattern.compile("<title>(.*?)</title>");
	    Matcher m = p.matcher(htmlContent);
	    
	    if (m.find() == true) { return m.group(1).trim(); }
	    else { return ""; }
		
//		Parser parser = new Parser(url);
//		NodeList nodeList = parser.parse(new NodeFilter() {
//			public boolean accept(Node node) {
//				return node.getText().equalsIgnoreCase("title");
//			}
//		});
//		System.out.println(nodeList.elementAt(0).getText());
	}
	
}
