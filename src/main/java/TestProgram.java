
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Vector;

import jdbm.RecordManager;
import jdbm.RecordManagerFactory;
import jdbm.htree.HTree;
import model.Page;
import jdbm.helper.FastIterator;

public class TestProgram {
	private RecordManager recman;
	private HTree graphHashTable, invertedContentHashTable, invertedTitleHashTable, pageWordHashTable;;
	
	TestProgram(String recordmanager) throws IOException{
		recman = RecordManagerFactory.createRecordManager(recordmanager);

		long graph = recman.getNamedObject("graph");
		long invertedContent = recman.getNamedObject("invertedContent");
		long invertedTitle = recman.getNamedObject("invertedTitle");
		long pageWord = recman.getNamedObject("pageWord");
		
		graphHashTable = HTree.load(recman, graph);
		invertedContentHashTable = HTree.load(recman, invertedContent);
		invertedTitleHashTable = HTree.load(recman, invertedTitle);
		pageWordHashTable = HTree.load(recman, pageWord);
	}
	
	public void print() throws IOException {
		File file = new File("spider_result.txt");

	    // Create a file
	    PrintWriter output = new PrintWriter(file);
	    FastIterator iter = graphHashTable.keys();
	    Object key;
        while( (key = iter.next())!=null){
        	int int_key = (Integer) key;
        	Page page;
        	page = (Page) graphHashTable.get(int_key);
        	output.print("Page title: ");
        	output.println(page.getTitle());
        	output.print("URL: ");
    	    output.println(page.getURL());
    	    output.print("Last modification date: ");
    	    output.print(page.getLastMod());
    	    output.print(", size of page: ");
    	    output.println(page.getHTMLContent().length());
    	    
    	    Vector<String> keywords = (Vector<String>) pageWordHashTable.get(int_key);
    	    for (int i = 0; i < keywords.size(); i++){
    	    	HashMap<Integer, Vector<Integer>> posting = (HashMap<Integer, Vector<Integer>>) invertedContentHashTable.get(keywords);
    	    	Vector<Integer> freq = posting.get(int_key);
    	    	
    	    	posting = (HashMap<Integer, Vector<Integer>>) invertedTitleHashTable.get(keywords);
    	    	Vector<Integer> freq2 = posting.get(int_key);
    	    	
    	    	output.print(keywords.get(i) + " " + (freq.size()+freq2.size()) + ";");
    	    }
    	    output.println();
    	    
    	    Vector<String> parentLinks = page.getparentLinks();
    	    for (int i = 0; i < parentLinks.size(); i++){
    	    	output.print("Parent Link" + (i+1) + ": ");
        	    output.println(parentLinks.get(i));
    	    }
    	    Vector<String> childLinks = page.getchildLinks();
    	    for (int i = 0; i < childLinks.size(); i++){
    	    	output.print("Child Link" + (i+1) + ": ");
        	    output.println(childLinks.get(i));
    	    }
        	output.println("-------------------------------------------------------------------------------------------");
        }
        
        // Close the file
	    output.close();
	}
	
	public void finalize() throws IOException {
		recman.commit();
		recman.close();
	} 
	
	public static void main(String[] args) throws IOException {
		TestProgram test = new TestProgram("spider");
		test.print();
		test.finalize();
	}
}
