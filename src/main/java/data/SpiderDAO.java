package data;

import java.io.IOException;

import model.Page;

public abstract class SpiderDAO {

	public abstract void add(Page n) throws IOException;
	
	public abstract void update(Page n) throws IOException;
	
	public abstract void close() throws IOException;
}
