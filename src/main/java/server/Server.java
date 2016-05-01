package server;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Vector;

import model.Page;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.jsoup.Jsoup;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

import data.JDBMIndexerDAO;
import data.JDBMSpiderDAO;

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
    public Vector<Map<String, Object>> search(@RequestParam(required=true) String q) throws IOException{
    	
    	String[] query = q.split(" ");

    	int[] ids = {1, 2, 3}; // get from retrieval function
    	
    	Vector<Map<String, Object>> results = new Vector<Map<String,Object>>();
    	
    	for (int id : ids){
    		Page p = (new JDBMSpiderDAO()).getPageById(id);
    		
    		String summary = Jsoup.parse(p.getHTMLContent()).text();
    		summary = summary.substring(0, summary.indexOf(" ", 200));
    		Vector<String> words = getMostSimilar(id);
    		
    		Map<String, Object> map = new HashMap<String, Object>();
    		map.put("title", p.getTitle());
    		map.put("url", p.getURL());
    		map.put("caption", summary);
    		map.put("keywords", words);
    		
    		results.add(map);
    	}
    	
    	return results;
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
    }
}