package lab5_1;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Vector;

public class App {
	public static void main(String[] args) throws Exception {
		// We want to use ArrayList instead of Vector here, i.e.,
		ArrayList<String> vect = new ArrayList<String>();
		//Vector<String> vect = new Vector<String>();
		for(int i = 1; i <= 3; ++i) {
			vect.add("Tick Tick " + i);
		}

		//Enumeration<String> etion = vect.elements();
		Enumeration<String> etion = new ArrayListAdapter<String>(vect.iterator());
		LinearTransformer<String> lT = new LinearTransformer<String>(etion);
		lT.transform(System.out);
	}
}
