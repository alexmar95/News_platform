import javax.jms.*;

public class Editor extends Thread implements MessageListener, ExceptionListener{
	
	private final Platform platform;
	
	
	public Editor(Platform p){
		platform  = p;
	}
	
	private void postArticle(Article a){
		
	}

	@Override
	public void onException(JMSException exception) {
		System.err.println("an error occurred: " + exception);
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
	
}
