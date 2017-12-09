import java.util.ArrayList;

public class Platform {
	//TODO: change ArrayList to something better
	//private ArrayList<Article> articles = new ArrayList<Article>(); 
	private ArrayList<Editor> editors = new ArrayList<Editor>();
	private ArrayList<Reader> readers = new ArrayList<Reader>();
	
	public Platform(int editors, int readers){
		initActors(editors,readers);		
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
