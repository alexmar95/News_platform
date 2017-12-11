


public class TestMain {
	private static void init1(){
		Domain.init();
		
		Platform p = new Platform(); 
		Reader r1 = new Reader("Gigi Becali",p,new Domain[]{Domain.getByName("Football"),Domain.getByName("Internal politics")});
		Reader r2 = new Reader("William Branza", p,new Domain[]{Domain.getByName("Politics")});
		Reader r3 = new Reader("Naba", p,new Domain[]{Domain.getByName("Entertainment")});
		
		Editor e1 = new Editor("Dan Capatos", p,new Domain[]{Domain.getByName("Celebrity news")});
		Editor e2 = new Editor("Big TZ", p,new Domain[]{Domain.getByName("Football"),Domain.getByName("Internal politics")});
		Editor e3 = new Editor("Anderson", p,new Domain[]{Domain.getByName("Entertainment"),Domain.getByName("Internal politics")});
		r1.start();
		r2.start();
		r3.start();
		e1.start();
		e2.start();
		e3.start();
		
	}
	public static void main(String[] args) {
		System.out.println("So far so good...");
		TestMain.init1();		
	}

}
