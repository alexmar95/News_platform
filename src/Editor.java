import java.util.ArrayList;
import java.util.Random;

import javax.jms.*;
import javax.naming.NamingException;

public class Editor extends Thread implements MessageListener, ExceptionListener{
	
	private Platform platform;
	private String[] topics;
	private String name;
	private ArrayList<Article> myArticles = new ArrayList<Article>(); 
	private Random rand = new Random(System.currentTimeMillis());
	
	public Editor(String _name,Platform p, String[] _topics){
		name = _name;
		platform  = p;
		topics = _topics;
	}
	
	private void createArticle(){
		Article ar = new Article(randomString(16), name, randomString(64));
		platform.publishArticle(this,ar);
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
	
	private String randomString(int size){
		String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random(System.currentTimeMillis());
        while (salt.length() < size) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;
	}
	
	private void editArticle(){
		int index = rand.nextInt(myArticles.size());
		Article a = myArticles.get(index);
		a.editContent(randomString(64));
	}
	
	public String[] getTopics() {
		return topics;
	}
	
	public void run(){
		double create = 0.2;//1-create is the probability to create a new article instead of editing an old one
		try {
			platform.registerEditor(this);
		} catch (NamingException | JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		double decision;
		while(true){
			decision = rand.nextDouble();
			if(decision < create){
				createArticle();
			}else{
				editArticle();
			}
			try {
				Thread.sleep(2);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}
