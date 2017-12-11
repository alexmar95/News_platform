import javax.jms.*;
import javax.naming.NamingException;

public class Reader extends Thread implements MessageListener, ExceptionListener{
	private final Platform platform;
	private String[] topics;
	
	public Reader(Platform p, String[] _topics){
		platform  = p;
		topics = _topics;
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
		TextMessage msg = (TextMessage) message;
	       try {
	          System.out.println("received: " + msg.getText());
	       } catch (JMSException ex) {
	          ex.printStackTrace();
	       }
	}
	public String[] getTopics() {
		return topics;
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
		}
	}
}
