package lmc;

import java.io.IOException;

public class NetworkProgram {

	/**
	 * @param args
	 */
	public static void main(String[] args) {


		try {
			Client c = new Client("barton.cs.pdx.edu","80","lame","buh");
//			c.send("offer W", true);
			
			c.offer('W');
			Client c2 = new Client("barton.cs.pdx.edu","80","lame2","buh");
//			c.send("offer W", true);
			
			c2.accept("4", 'B');
		} catch (IOException e) {
			e.printStackTrace();
		}
		

	}

}
