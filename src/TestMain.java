


public class TestMain {
	private static void init1(){
		Domain.init();
		
		Platform p = new Platform(); 
		
		Filter f1 = new Filter() {
						public boolean match(Article ar) {
							String auth = ar.getAuthor();
							String content = ar.getContent();
							return ((auth == "Dan Capatos") || (auth == "Big TZ")) &&
									content.contains("H") && content.contains("A");
						}
					};
		Filter f2 = new Filter() {
						public boolean match(Article ar) {
							String source = ar.getSource();
							return source == "Antena3" || source == "OTV";
						}
					};
		
		Reader r1 = new Reader("Gigi Becali",p,
								new Domain[]{Domain.getByName("Football"),Domain.getByName("Internal politics")},
								f1);
		Reader r2 = new Reader("William Branza", p,
								new Domain[]{Domain.getByName("Politics")},
								f1);
		Reader r3 = new Reader("Naba", p,
								new Domain[]{Domain.getByName("Entertainment")},
								f2);
		
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
