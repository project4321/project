package data;

import java.io.IOException;
import java.util.Vector;

import model.Page;

public abstract class SpiderDAO {

	public abstract Vector<Page> getAllPages() throws IOException;
	
	public abstract Page getPage(int id) throws IOException;
	
	public abstract void add(Page n) throws IOException;
	
	public abstract void update(Page n) throws IOException;
	
	public abstract void close() throws IOException;
}
