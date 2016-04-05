package data;

import java.io.IOException;
import java.util.Properties;
import java.util.Vector;

import model.Page;
import jdbm.RecordManager;
import jdbm.RecordManagerFactory;
import jdbm.helper.FastIterator;
import jdbm.htree.HTree;

public class JDBMSpiderDAO extends SpiderDAO {

	private RecordManager recman;
    private HTree hashtable;
	
	public JDBMSpiderDAO(String rootURL) throws IOException{
		// create or open spider record manager
        recman = RecordManagerFactory.createRecordManager("spider", new Properties());

        // create or load 
        long recid = recman.getNamedObject("graph");
        if (recid != 0) {
            System.out.println("Reloading existing graph...");
            hashtable = HTree.load(recman, recid);
            System.out.println(this.toString());
        } 
        else {
            System.out.println("Creating new graph...");
            hashtable = HTree.createInstance(recman);
            recman.setNamedObject("graph", hashtable.getRecid());
        }
	}
	
	@Override
	public Vector<Page> getAllPages() throws IOException {
		Vector<Page> pages = new Vector<Page>();
		
		FastIterator iter = hashtable.keys();
		Integer id;
		while ( (id = (Integer) iter.next()) != null ) {
			pages.add((Page) hashtable.get(id));
		}

		return pages;
	}

	@Override
	public Page getPage(int id) throws IOException {
		return (Page) hashtable.get(id);
	}
	
	@Override
	public void add(Page n) throws IOException {
		hashtable.put(n.getId(), n);
		recman.commit();
	}
	
	@Override
	public void update(Page n) throws IOException{
		hashtable.put(n.getId(), n);
		recman.commit();
	}
	
	@Override
	public void close() throws IOException{
		recman.close();
	}
	
	@Override
	public String toString() {
		String result = null;
		
		try {
			result = "";
			FastIterator iter = hashtable.keys();
			String url;
			while ( (url = (String) iter.next()) != null ) {
				result = result + hashtable.get(url) + "\n";
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
	
}
