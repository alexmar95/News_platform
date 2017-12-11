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
	private Random rand = new Random(System.currentTimeMillis());
	private InitialContext ctx;
	
/*	public Platform(int editors, int readers){
		//initActors(editors,readers);		
	}
*/
	public Platform(){
		try {
			ctx = new InitialContext();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void registerEditor(Editor e) throws NamingException, JMSException{
	}

	public void registerReader(Reader r) throws NamingException, JMSException{
		// get the initial context

		ArrayList<String> topics = r.getDomains();
		
		for(String topic : topics)
		{
			subscribeToEvent(r, "domain", topic);

			// wait for messages
			/*System.out.print("waiting for messages");
		for (int i = 0; i < 10; i++) {
			//Thread.sleep(1000);
			System.out.print(".");
		}*/
			//System.out.println();

			// close the queue connection
			//queueConn.close();
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
		try {
			subscribeToEvent(e, "articles", "/"+a.getID());
			generateEvent("domains", a.getDomain());
		} catch (NamingException | JMSException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	public String readRandomArticle(){
		int index = rand.nextInt(articles.size());
		Article a = articles.get(index);
		try {
			generateEvent("articles",""+a.getID());
		} catch (NamingException | JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return a.toString();
	}

	private void generateEvent(String root,String subdom) throws NamingException, JMSException{
		//InitialContext ctx = new InitialContext();
		
		Queue queue = (Queue) ctx.lookup(root + "/" + subdom);

		// lookup the queue connection factory
		QueueConnectionFactory connFactory = (QueueConnectionFactory) ctx.lookup(root + "/connectionFactory");

		// create a queue connection
		QueueConnection queueConn = connFactory.createQueueConnection();

		// create a queue session
		QueueSession queueSession = queueConn.createQueueSession(false, Session.DUPS_OK_ACKNOWLEDGE);

		// create a queue sender
		QueueSender queueSender = queueSession.createSender(queue);
		queueSender.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

		// create a simple message to say "Hello"
		TextMessage message = queueSession.createTextMessage(subdom);

		// send the message
		queueSender.send(message);

		// print what we did
		System.out.println("sent: " + message.getText());

		// close the queue connection
		queueConn.close(); 	
		//generate event that it was read "stire citita + ID"`	
	}
	
	private void subscribeToEvent(Object subscriber, String root, String subdom) throws NamingException, JMSException{
		
		Queue queue = (Queue) ctx.lookup("root" + subdom);
		// lookup the queue connection factory
		QueueConnectionFactory connFactory = (QueueConnectionFactory) ctx.
				lookup(root +"/connectionFactory");

		// create a queue connection
		QueueConnection queueConn = connFactory.createQueueConnection();

		// create a queue session
		QueueSession queueSession = queueConn.createQueueSession(false,
				Session.AUTO_ACKNOWLEDGE);

		// create a queue receiver
		QueueReceiver queueReceiver = queueSession.createReceiver(queue);

		// set an asynchronous message listener
		queueReceiver.setMessageListener((MessageListener) subscriber);

		// set an asynchronous exception listener on the connection
		queueConn.setExceptionListener((ExceptionListener) subscriber);

		// start the connection
		queueConn.start();
	}
	
}
