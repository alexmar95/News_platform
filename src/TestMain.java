


public class TestMain {
	private static void init1(){
		Platform p = new Platform();
		Reader r1 = new Reader(p,new String[]{"sport","politics"});
		Reader r2 = new Reader(p,new String[]{"gossip"});
		Reader r3 = new Reader(p,new String[]{"sport"});
		
		Editor e1 = new Editor("Hans", p,new String[]{"gossip","politics"});
		Editor e2 = new Editor("Christian", p,new String[]{"sport"});
		Editor e3 = new Editor("Anderson", p,new String[]{"politics"});
		r1.start();
		r2.start();
		r3.start();
		e1.start();
		e2.start();
		e3.start();
		
	}
	public static void main(String[] args) {
		System.out.println("So far so good...");
	}

}
