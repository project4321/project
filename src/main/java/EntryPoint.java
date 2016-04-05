import java.io.IOException;

import model.Page;

import org.htmlparser.util.ParserException;

import data.SpiderDAO;
import data.JDBMSpiderDAO;

public class EntryPoint {
	
	public static void main(String args[]) throws IOException, ParserException{
		
//		new Spider("http://www.cse.ust.hk/");
//		new Spider("http://ihome.ust.hk/~hlchanad");
		SpiderDAO dao = new JDBMSpiderDAO();
		dao.add(new Page());
		dao.add(new Page());
		System.out.println(dao);
	}
}
