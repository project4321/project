import java.sql.Timestamp;
import java.util.Vector;


public class Node {
	
	private static int idcount = 1;
	
	private int id;
	private String url;
	private Vector<String> parentLinks, childLinks;
	private Timestamp lastMod;
	private String htmlContent;
	
	public Node (){
		id = idcount++;
		url = "";
		parentLinks = new Vector<String>();
		childLinks = new Vector<String>();
		lastMod = new Timestamp(0);
		htmlContent = "";
	}
	
	public Node(String url, Vector<String> parentLinks, Vector<String> childLinks, Timestamp lastMod, String htmlContent ){
		id = idcount++;
		this.url = url;
		this.parentLinks = parentLinks;
		this.childLinks = childLinks;
		this.lastMod = lastMod;
		this.htmlContent = htmlContent;
	}
	
	@Override
	public String toString(){
		return "{\n"
				+ "\tid: " + id + ", \n"
				+ "\turl: " + url + ", \n"
				+ "\tparentLinks:" + parentLinks.toString() + ", \n"
				+ "\tchildLinks: " + childLinks.toString() + ", \n"
				+ "\tlastMod: " + lastMod + "\n"
//				+ "\thtmlContent: " + htmlContent + "\n"
				+ "}";
	}
}
