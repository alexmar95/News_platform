import javax.jms.*;

public class Reader extends Thread implements MessageListener, ExceptionListener{
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
