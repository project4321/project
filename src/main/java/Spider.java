import html.HTTPClient;
import html.LinkExtractor;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.Vector;
import java.util.concurrent.LinkedBlockingQueue;

import model.Page;

import org.htmlparser.util.ParserException;

import data.SpiderDAO;
import data.JDBMSpiderDAO;


public class Spider {
	
	String url;
	SpiderDAO dao = new JDBMSpiderDAO();
	
	public Spider(String rootURL) throws IOException, ParserException{
		url = rootURL;
		
		int count = 0;
		Queue<String> queue = new LinkedBlockingQueue<String>(); // Queue for BFS
		Set<String> finishedSet = new HashSet<String>(); // collects all links that processed
		Set<String> processingSet = new HashSet<String>(); // collects all links that are processing
		Map<String, Vector<String>> parents = new HashMap<String, Vector<String>>();
		
		queue.add(url);
		processingSet.add(url);
		
		while (queue.size() > 0 && count < 20){
			String url = queue.poll();
			
			Vector<String> children = (new LinkExtractor(url)).extractLinks();
			String htmlContent = HTTPClient.getHTMLContent(url);
			Timestamp lastMod = HTTPClient.getLastMod(url);
			String title = HTTPClient.getTitle(url);
			
			Page n = new Page(url, title, new Vector<String>(), children, lastMod, htmlContent);
			dao.add(n);
			
			for (String child: children){
				if (!parents.containsKey(child)) parents.put(child, new Vector<String>());
				parents.get(child).add(url);
				
				// if not processed && not processing -> not yet process -> add in Queue
				if (!finishedSet.contains(child) && !processingSet.contains(child)){
					processingSet.add(child);
					queue.add(child);
				}
			}
			
			processingSet.remove(url);
			finishedSet.add(url); // finish process 
			count ++;
		}
		
		System.out.println("objects in db :");
		System.out.println(dao.toString());
		
		dao.close();
		
//		// for loop get all from db then put parents link
//		for (String key : parents.keySet()){
//			System.out.println("\"" + key + "\": [");
//			for (int i=0; i<parents.get(key).size(); i++){
//				System.out.print("\t\"" + parents.get(key).get(i) + "\"");
//				if (i < parents.get(key).size()-1) System.out.println(",");
//			}
//			System.out.println("\n]");
//			
//		}
	}
}
