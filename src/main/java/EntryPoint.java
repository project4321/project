import java.io.IOException;
import java.sql.Timestamp;
import java.util.Vector;

import model.Page;

import org.htmlparser.util.ParserException;


public class EntryPoint {
	
	public static void main(String args[]) throws IOException, ParserException{
		Spider spider = new Spider("http://www.cse.ust.hk");
//		Spider spider = new Spider("http://ihome.ust.hk/~hlchanad");
		spider.crawl();
		
		Indexer indexer = new Indexer();
		indexer.indexPage(spider.getNewPages());
		
//		Vector<Page> list = new Vector<Page>();
//		Page page1 = new Page("http://google.com", "I am useful title", new Vector<String>(), new Vector<String>(), new Timestamp(0), "For a list of Wikipedia contents, see Portal:Contents. For a listing of Wikipedia's directories and indexes, see Wikipedia:Directory.");
//		Page page2 = new Page("http://yahoo.com", "I am yahoo and today is thursday", new Vector<String>(), new Vector<String>(), new Timestamp(0), "where base is the URL of the current visited webpage.");
//		list.add(page1);
//		list.add(page2);
		
//		indexer.indexPage(list);
		indexer.printAll();
		indexer.close();
	}
}
