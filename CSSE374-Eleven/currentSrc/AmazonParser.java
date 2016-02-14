package problem;

import java.util.HashMap;

public class AmazonParser implements DataParser {

	@Override
	public HashMap<String, String> parse(String s) {
		HashMap<String, String> ret = new HashMap<>();
		String[] split = s.split(",");
		String[] l = split[0].split(" ttl ");
		String[] r = split[1].split(" ttl ");
		ret.put(r[0].trim(), r[1].trim());
		ret.put(l[0].trim(), l[1].trim());
		return ret;
	}

	@Override
	public String getCompanyID() {
		return "Amazon";
	}

}
