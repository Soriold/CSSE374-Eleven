package problem;

import java.util.HashMap;

public class MicrosoftParser implements DataParser {

	@Override
	public HashMap<String, String> parse(String s) {
		HashMap<String, String> ret = new HashMap<>();
		String[] split = s.split(",");
		ret.put(split[0].trim(), split[1].trim());
		return ret;
	}

	@Override
	public String getCompanyID() {
		return "Microsoft";
	}

}
