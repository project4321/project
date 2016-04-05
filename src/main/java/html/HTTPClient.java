package html;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Timestamp;

import org.apache.commons.io.IOUtils;


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
	    if (date == 0) return null; // No last-modified information
	    else return new Timestamp(date);
	}

	public static String getTitle(String url) {
		
		return "no title yet";
	}
	
}
