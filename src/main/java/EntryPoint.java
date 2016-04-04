import java.io.IOException;
import java.net.UnknownHostException;

public class EntryPoint {
	
	public static void main(String args[]) throws UnknownHostException, IOException{
		String s = new String(HTTPClient.getHTMLContent("http://www.cse.ust.hk/"));
		System.out.println(s);
	}
}
