import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/*
 * Sistem de stiri
 * 
 * Aplicatia implementeaza un sistem de stiri orientat pe evenimente. Un
 * eveniment este aparitia, modificarea sau stergerea unei stiri, iar
 * stirile sunt organizate in domenii si un numar arbitrar de subdomenii.
 * Stirile au si alte atribute cum ar fi: data primei publicari, data
 * ultimei modificari, sursa de informatie, autorul articolului etc.
 * 
 * Actorii din sistem sunt de doua tipuri: editori de stiri si cititori.
 * Editorii trebuie sa poata afla in timp real care este numarul de
 * cititori pentru stirile de interes. Pentru aceasta, ei se pot declara
 * interesati de aparitia unui eveniment gen "stire citita". Cititorii se
 * pot abona la una sau mai multe stiri, specificand domeniile de interes
 * si alte atribute (data, sursa etc.).
 */
public class Article {
	private static final DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
	private final String publishDate = formatter.format(Calendar.getInstance().getTime());
	private final Domain domain;//don't allow domain change for the now
	private String lastModified;
	private String source;
	private String author;
	private String content;
	
	public Article(Domain d,String _source, String _author, String _content)
	{
		domain = d;
		source = _source;
		author = _author;
		content = _content;
		setLastModified();
	}
	
	private void setLastModified(){
		lastModified = formatter.format(Calendar.getInstance().getTime());
	}
	
	public void changeSmething(){
		
	}
	
}
