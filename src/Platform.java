import java.util.ArrayList;
import java.util.Random;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.jms.*;

public class Platform {
	//TODO: change ArrayList to something better
	private ArrayList<Article> articles = new ArrayList<Article>(); 
	private ArrayList<Editor> editors = new ArrayList<Editor>();
	private ArrayList<Reader> readers = new ArrayList<Reader>();
	//private ArrayList<String[]> domains = new ArrayList<String[]>();
	private Random rand = new Random(System.currentTimeMillis());
	private InitialContext ctx;
	

	public Platform(){
		
	}
	
	public void  subscribeReader(Reader r) {
		readers.add(r);
	}
	
	public void editArticleEvent(Article a) {
		String domain = a.getDomain();
		for(Reader r : readers) {
			for(String d : r.getDomains()) {
				if(d.contains(domain)) {
					if(r.filter(a))
						r.articleEdited(a.toString());
					break;
				}
			}
		}
	}
	
	public void generateArticleCreateEvent(Article a) {
		String domain = a.getDomain();
		for(Reader r : readers) {
			for(String d : r.getDomains()) {
				if(d.contains(domain)) {
					if(r.filter(a))
						r.articleCreated(a.toString());
					a.notifyAllHandlers();
					break;
				}
			}
		}
	}
	
	public void onMessage(Message message)
	{
		TextMessage msg = (TextMessage) message;
		try {
			System.out.println("received: " + msg.getText());
		} catch (JMSException ex) {
			ex.printStackTrace();
		}
	}

	public void onException(JMSException exception)
	{
		   System.err.println("an error occurred: " + exception);
	}
	
	private synchronized void addArticle(Article arr){
		articles.add(arr);		
	}
	
	private synchronized void removeArticle(Article arr){
		articles.remove(arr);
	}
	
	public void deleteArticle( Article a, Editor e){
		removeArticle(a);
		//unsubscribe for read events
	}
	
	public void publishArticle( Article a, Editor e){
		addArticle(a);
		generateArticleCreateEvent(a);
	}
	
	public String readRandomArticle(){
		if(articles.size()==0) {
			return "";
		}
		int index = rand.nextInt(articles.size());
		Article a = articles.get(index);
		a.notifyAllHandlers();
		
		return a.toString();
	}

}
