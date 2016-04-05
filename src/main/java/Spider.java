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
	
	private final int maxCount = 20;
	private String url;
	private SpiderDAO dao;
	
	public Spider(String rootURL) throws IOException{
		url = rootURL;
		dao = new JDBMSpiderDAO(url);
	}
	
	public void crawl() throws IOException, ParserException {
		
		int count = 0;
		Queue<String> queue = new LinkedBlockingQueue<String>(); // Queue for BFS
		Set<String> finishedSet = new HashSet<String>(); // collects all links that processed
		Set<String> processingSet = new HashSet<String>(); // collects all links that are processing
		Map<String, Vector<String>> parents = new HashMap<String, Vector<String>>();
		
		queue.add(url);
		processingSet.add(url);
		
		while (queue.size() > 0 && count < maxCount){
			String url = queue.poll();
			
			Vector<String> children = (new LinkExtractor(url)).extractLinks();
			String htmlContent = HTTPClient.getHTMLContent(url);
			Timestamp lastMod = HTTPClient.getLastMod(url);
			String title = HTTPClient.getTitle(htmlContent);
			
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
		
		for (Page page : dao.getAllPages()){
			if (parents.containsKey(page.getURL()))
				page.setParentLinks(parents.get(page.getURL()));
			dao.update(page);
		}
		
		System.out.println("\nobjects in db :");
		System.out.println(dao.getAllPages());
		System.out.println("(id:1): " + dao.getPage(1));
		
		dao.close();
	}
}
