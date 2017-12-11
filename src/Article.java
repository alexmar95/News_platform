import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Article {
	private static final DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
	private final String publishDate = formatter.format(Calendar.getInstance().getTime());
	private String lastModified;
	private String source;
	private String author;
	private String content;
	
	public Article(String _source, String _author, String _content)
	{
		source = _source;
		author = _author;
		content = _content;
		setLastModified();
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
