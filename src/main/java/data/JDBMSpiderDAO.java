package data;

import java.io.IOException;
import java.util.Properties;

import model.Page;
import jdbm.RecordManager;
import jdbm.RecordManagerFactory;
import jdbm.helper.FastIterator;
import jdbm.htree.HTree;

public class JDBMSpiderDAO extends SpiderDAO {

	private RecordManager recman;
    private HTree hashtable;
	
	public JDBMSpiderDAO() throws IOException{
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
	
	public void add(Page n) throws IOException {
//		System.out.println(n);
		hashtable.put("key", "hello");
		recman.commit();
	}
	
	public void update(Page n){
		
	}
	
	public void close() throws IOException{
		recman.close();
	}
	
	@Override
	public String toString() {
		
		String result = null;
		
		try {
			result = new String("***");
			FastIterator iter = hashtable.keys();
			String url;
			result.concat("$$$");
			while ( (url = (String) iter.next()) != null ) {
				result.concat(hashtable.get(url).toString() + "\n");
				result.concat("@@@");
			}
			result.concat("###");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
	
}
