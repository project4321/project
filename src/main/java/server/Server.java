package server;

import java.util.Arrays;
import java.util.Collection;
import java.util.Vector;

import org.springframework.boot.*;
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
    public Vector<String> search(@RequestParam(required=true) String q){
    	
    	System.out.println(q);
    	String[] keywords = q.split(" ");
    	
    	Vector<String> results = new Vector<String>();
    	results.addAll(Arrays.asList(keywords));
    	
		return results;
    	
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Server.class, args);
    }
}