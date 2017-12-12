import java.util.ArrayList;
import java.util.concurrent.Callable;

import javax.jms.*;
import javax.naming.NamingException;

public class Reader extends Thread implements MessageListener, ExceptionListener, EventHandler{
	private final Platform platform;
	private Domain[] domains;
	private String name;
	//private Callable<Boolean> filter;
	private String authorFilter = "";
	
	public Reader(String _name, Platform p, Domain[] _topics){
		name = _name;
		platform  = p;
		domains = _topics;
	}
	
	@Override
	public void onException(JMSException exception) {
		System.err.println("an error occurred: " + exception);
	}

	public void setFilter(String author) {
		authorFilter = author;
	}
	
	public boolean filter(Article a){
		boolean b = true;
		if(authorFilter.isEmpty())
			return true;
		b&=a.getAuthor().equals(authorFilter);
		
		return b;
	}
	
	@Override
	public void onMessage(Message message) {
		System.out.println(name + " a citit articolul: " + message.toString());
	}
	
	public ArrayList<String> getDomains() {
		ArrayList<String> arr = new ArrayList<String>();
		for(Domain d : domains){
			arr.add(d.toString());
		}
		return arr;
	}
	
	private void readRandomArticle(){
		String ar = platform.readRandomArticle();
		if(ar.isEmpty())
			System.out.println("N-am ce sa citesc...\n\n");
		else
			System.out.println(this.getReaderName() + ": Am citit(ca stiu cum): " + ar+"\n\n");
	}
	
	public void run(){
		platform.subscribeReader(this);
		while(true){
			readRandomArticle();
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public String getReaderName()
	{
		return name;
	}

	@Override
	public void articleRead(Article a) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void articleEdited(String ar) {
		System.out.println("Article just edited:\n"+ar + "\n\n");
		
	}

	@Override
	public void articleCreated(String ar) {
		System.out.println("Article created:\n"+ar + "\n\n");
		
	}
}
