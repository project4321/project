package hk.ust.comp4321.server;

import hk.ust.comp4321.data.JDBMIndexerDAO;
import hk.ust.comp4321.data.JDBMSpiderDAO;
import hk.ust.comp4321.demo.MyClass;
import hk.ust.comp4321.model.Page;
import hk.ust.comp4321.tools.Retrieval;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import org.apache.commons.lang3.tuple.Pair;
import org.jsoup.Jsoup;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

@Controller
@EnableAutoConfiguration
public class Server {

    @RequestMapping("/")
    @ResponseBody
    public String home() {
        return "Hello World!";
    }
    
    @RequestMapping("/search")
    @ResponseBody
    public Vector<Map<String, Object>> search(@RequestParam(required=true) String[] qs) throws IOException{
    	
    	for (String q : qs ){ System.out.println(q); }
    	
    	int[] ids = (new Retrieval("spider")).search(qs);
    	
    	Vector<Map<String, Object>> results = new Vector<Map<String,Object>>();
    	
    	for (int id : ids){
    		
    		System.out.print("id: " + id);
    		
    		Page p = (new JDBMSpiderDAO()).getPageById(id);
    		
    		Vector<String> words = getMostSimilar(id);
    		
    		Map<String, Object> map = new HashMap<String, Object>();
    		map.put("page", p);
    		map.put("score", 0);
    		map.put("keywords", words);
    		
    		results.add(map);
    	}
    	
    	return results;
    }
    
    @RequestMapping("/pages")
    @ResponseBody
    public Vector<Page> getPages() throws IOException{
    	return (new JDBMSpiderDAO()).getAllPages();
    }
    
    @RequestMapping("/words")
    @ResponseBody
    public String[] getAllWords() throws IOException{
    	
    	Vector<String> words = (new JDBMIndexerDAO()).getAllWords();
    	Collections.sort(words);

    	return words.toArray(new String[words.size()]);
    }
    
    private Vector<String> getMostSimilar(int pageId) throws IOException{
        	
    	Vector<Pair<String, Integer>> frequency = (new JDBMIndexerDAO()).getWordFrequencyByPageId(pageId);
    	System.out.println(frequency.toString());
    	
    	Vector<String> result = new Vector<String>();
    	for (int i=0; i<frequency.size() && i<5; i++){
    		result.add(frequency.get(i).getLeft());
    	}
    	return result;
    }
    
    public static void main(String[] args) throws Exception {
        SpringApplication.run(Server.class, args);
//    	System.out.println((new MyClass()).testMethod());
//    	
//
//    	String[] words = (new Server()).getAllWords();
//    	for (String word : words){
////    		System.out.println(word);
//    	}
    }
}