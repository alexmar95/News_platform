import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.Semaphore;

public class Article {
	private static final DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
	private final String publishDate = formatter.format(Calendar.getInstance().getTime());
	private Domain domain;
	private String lastModified;
	private String source;
	private String author;
	private String content;
	private int id;
	private static int ID = 0;
	private static Object sema = new Object();
	
	public Article(String _author,Domain _domain, String _source, String _content)
	{
		synchronized(sema){
			id = ID++;
		}
		domain = _domain;
		source = _source;
		author = _author;
		content = _content;
		setLastModified();
	}
	
	public int getID(){
		return id;
	}
	public String getDomain(){
		return domain.toString();
	}
	private void setLastModified(){
		lastModified = formatter.format(Calendar.getInstance().getTime());
	}
	
	public void editContent(String s){
		setLastModified();
		content = s;
	}
	
	public String toString()
	{
		return "Author: " + author + "\nPublishing date: "  + publishDate + "\nLast modification date: "
				+ lastModified + "\n" + content;                                             
	}
	
}
