import java.io.IOException;

import org.htmlparser.util.ParserException;


public class EntryPoint {
	
	public static void main(String args[]) throws IOException, ParserException{
		
		System.out.println("Crawling ...");
		
		Spider spider = new Spider("http://www.cse.ust.hk");
		spider.crawl();
		
		System.out.println("Indexing ...");
		
		Indexer indexer = new Indexer();
		indexer.indexPage(spider.getNewPages());
		indexer.close();
		
		System.out.println("Done");
	}
}
