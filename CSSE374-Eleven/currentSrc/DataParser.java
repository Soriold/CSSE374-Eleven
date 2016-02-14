package problem;

import java.util.HashMap;

public interface DataParser {

	public HashMap<String, String> parse(String s);
	
	public String getCompanyID();
}
