import java.util.ArrayList;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.jms.*;

public class Platform {
	//TODO: change ArrayList to something better
	//private ArrayList<Article> articles = new ArrayList<Article>(); 
	private ArrayList<Editor> editors = new ArrayList<Editor>();
	private ArrayList<Reader> readers = new ArrayList<Reader>();

	public Platform(int editors, int readers){
		initActors(editors,readers);		
	}

	public void registerEditor() throws NamingException, JMSException{
		// get the initial context
		InitialContext ctx = new InitialContext();

		// lookup the queue object
		Queue queue = (Queue) ctx.lookup("topic/news");

		// lookup the queue connection factory
		QueueConnectionFactory connFactory = (QueueConnectionFactory) ctx.lookup("queue/connectionFactory");

		// create a queue connection
		QueueConnection queueConn = connFactory.createQueueConnection();

		// create a queue session
		QueueSession queueSession = queueConn.createQueueSession(false, Session.DUPS_OK_ACKNOWLEDGE);

		// create a queue sender
		QueueSender queueSender = queueSession.createSender(queue);
		queueSender.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

		// create a simple message to say "Hello"
		TextMessage message = queueSession.createTextMessage("Hello");

		// send the message
		queueSender.send(message);

		// print what we did
		System.out.println("sent: " + message.getText());

		// close the queue connection
		queueConn.close(); 	
	}

	private void registerReader(Reader r) throws NamingException, JMSException{
		// get the initial context
		InitialContext ctx = new InitialContext();

		// lookup the queue object
		Queue queue = (Queue) ctx.lookup("queue/queue0");

		// lookup the queue connection factory
		QueueConnectionFactory connFactory = (QueueConnectionFactory) ctx.
				lookup("queue/connectionFactory");

		// create a queue connection
		QueueConnection queueConn = connFactory.createQueueConnection();

		// create a queue session
		QueueSession queueSession = queueConn.createQueueSession(false,
				Session.AUTO_ACKNOWLEDGE);

		// create a queue receiver
		QueueReceiver queueReceiver = queueSession.createReceiver(queue);

		// set an asynchronous message listener
		queueReceiver.setMessageListener(r);

		// set an asynchronous exception listener on the connection
		queueConn.setExceptionListener(r);

		// start the connection
		queueConn.start();

		// wait for messages
		System.out.print("waiting for messages");
		for (int i = 0; i < 10; i++) {
			//Thread.sleep(1000);
			System.out.print(".");
		}
		System.out.println();

		// close the queue connection
		queueConn.close();
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

	/**
       This method is called asynchronously by JMS when some error occurs.
       When using an asynchronous message listener it is recommended to use
       an exception listener also since JMS have no way to report errors
       otherwise.
       @param exception A JMS exception.
	 */
	public void onException(JMSException exception)
	{
		   System.err.println("an error occurred: " + exception);
	}
	private void initActors(int editors, int readers){
		/*Editor e;
		for(int i=0;i<editors;i++){
			e = new Editor();
			this.editors.add(e);
			e.run();
		}
		Reader r;
		for(int i=0;i<readers;i++){
			r = new Reader();
			this.readers.add(r);
			r.run();
		}*/
	}

}
