import java.io.IOException;
import java.util.Queue;
import java.util.Vector;
import java.util.concurrent.LinkedBlockingQueue;

import org.htmlparser.util.ParserException;


public class Spider {
	
	String url;
	
	public Spider(String rootURL) throws IOException, ParserException{
		url = rootURL;
		
		int count = 0;
		Queue<String> linksQ = new LinkedBlockingQueue<String>();
		LinkExtractor le = new LinkExtractor(url);
		
		linksQ.addAll(le.extractLinks());
		
		while (linksQ.size() > 0 && count < 20) {
			System.out.println("size: " + linksQ.size() + " " + linksQ.poll());
			count ++;
		}
	}
}
