import java.util.ArrayList;

import javax.jms.*;
import javax.naming.NamingException;

public class Reader extends Thread implements MessageListener, ExceptionListener{
	private final Platform platform;
	private Domain[] domains;
	private String name;
	
	public Reader(String _name, Platform p, Domain[] _topics){
		name = _name;
		platform  = p;
		domains = _topics;
	}
	
	@Override
	public void onException(JMSException exception) {
		System.err.println("an error occurred: " + exception);
	}

	private boolean filter(Message m){
		
		return true;
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
		System.out.println("Am citit(ca stiu cum): " + platform.readRandomArticle());
	}
	
	public void run(){
		try {
			platform.registerReader(this);
		} catch (NamingException | JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while(true){
			readRandomArticle();
			try {
				Thread.sleep(3);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
