package html;
import java.net.URL;
import java.util.Vector;

import org.htmlparser.beans.LinkBean;
import org.htmlparser.util.ParserException;

public class LinkExtractor {
	
	private String link = "";
	
	public LinkExtractor(String url){
		link = url;
	}
	
	public Vector<String> extractLinks() throws ParserException {
	    Vector<String> v_link = new Vector<String>();
	    LinkBean lb = new LinkBean();
	    lb.setURL(link);
	    URL[] URL_array = lb.getLinks();
	    for (int i=0; i<URL_array.length; i++){
	    	v_link.add(URL_array[i].toString());
	    }
	    return v_link;
	}
}