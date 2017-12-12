import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.Semaphore;

public class Article {
	private static final DateFormat formatter = new SimpleDateFormat("E, dd/MM/yyyy 'at' h:m:ss");
	private final String publishDate = formatter.format(Calendar.getInstance().getTime());
	private Domain domain;
	private String lastModified;
	private String source;
	private String author;
	private String content;
	private int id;
	private static int ID = 0;
	private static Object sema = new Object();
	private ArrayList<EventHandler> handlers = new ArrayList<EventHandler>();
	
	public Article(Editor _author,Domain _domain, String _source, String _content)
	{
		synchronized(sema){
			id = ID++;
		}
		handlers.add(_author);
		domain = _domain;
		source = _source;
		author = _author.getEditorName();
		content = _content;
		setLastModified();
	}
	public String getAuthor() {
		return author;
	}
	
	public int getID(){
		return id;
	}
	public String getDomain(){
		return domain.toString();
	}

	public String getContent()
	{
		return this.content;
	}
	
	public String getSource()
	{
		return this.source;
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
		return "Author: " + author + "\nSource: " + source + "\nPublishing date: "  + publishDate + "\nLast modification date: "
				+ lastModified + "\n" + content;                                             
	}
	
	public void notifyAllHandlers() {
		for(EventHandler e : handlers) {
			e.articleRead(this);
		}
	}
	
}
