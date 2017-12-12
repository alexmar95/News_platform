import java.util.ArrayList;
import java.util.Random;


public class Editor extends Thread implements EventHandler{
	
	private Platform platform;
	private Domain[] domains;
	private String name;
	private ArrayList<Article> myArticles = new ArrayList<Article>();
	private ArrayList<Integer> myIndexes = new ArrayList<Integer>();
	private Random rand = new Random(System.currentTimeMillis());
	
	public Editor(String _name,Platform p, Domain[] _domains){
		name = _name;
		platform  = p;
		domains = _domains;
	}
	
	private void createArticle(){
		int domainIndex = rand.nextInt(domains.length);
		Article ar = new Article(this,domains[domainIndex],randomString(16) , randomString(64));
		myArticles.add(ar);
		myIndexes.add(0);
		platform.publishArticle(ar,this);
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
		if(myArticles.isEmpty())
			return;
		int index = rand.nextInt(myArticles.size());
		Article a = myArticles.get(index);
		a.editContent(randomString(64));
		platform.editArticleEvent(a);
		//submit edit changes
	}
	
	public ArrayList<String> getDomains() {
		ArrayList<String> arr = new ArrayList<String>();
		for(Domain d : domains){
			arr.add(d.toString());
		}
		return arr;
	}
	
	public void run(){
		double create = 0.6;//create is the probability to create a new article instead of editing an old one
		
		double decision;
		while(true){
			decision = rand.nextDouble();
			if(decision < create){
				createArticle();
			}else{
				editArticle();
			}
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private synchronized void markArticleRead(Article a) {
		int index = myArticles.indexOf(a);
		int val = myIndexes.get(index);
		myIndexes.set(index, ++val);
		System.out.println("Article "+a.getID() +" read for "+val+" times\n\n");
		
	}
	
	@Override
	public void articleRead(Article a) {
		markArticleRead(a);
		
	}

	@Override
	public void articleEdited(String ar) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void articleCreated(String ar) {
		// TODO Auto-generated method stub
		
	}


	public String getEditorName() {
		return name;
	}
}
