package model;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Vector;


public class Page implements Serializable { 
	
	private static final long serialVersionUID = -8544223146268759686L;

	private static int idcount = 1;
	
	private int id;
	private String url, title;
	private Vector<String> parentLinks, childLinks;
	private Timestamp lastMod;
	private String htmlContent;
	
	public Page(){
		id = idcount++;
		this.url = "";
		this.title = "";
		this.parentLinks = new Vector<String>();
		this.childLinks = new Vector<String>();
		this.lastMod = new Timestamp(0);
		this.htmlContent = "";
	}
	
	public Page(String url, String title, Vector<String> parentLinks, Vector<String> childLinks, Timestamp lastMod, String htmlContent){
		id = idcount++;
		this.url = url;
		this.title = title;
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
				+ "\ttitle: " + title + ", \n"
				+ "\tparentLinks:" + parentLinks.toString() + ", \n"
				+ "\tchildLinks: " + childLinks.toString() + ", \n"
				+ "\tlastMod: " + lastMod + "\n"
//				+ "\thtmlContent: " + htmlContent + "\n"
				+ "}";
	}
	
	public String getURL() { return this.url; }
}
