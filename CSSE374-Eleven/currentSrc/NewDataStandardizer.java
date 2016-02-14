package problem;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

public class NewDataStandardizer {
	
	private static final DataParser[] parsers = {new GoogleParser(), new MicrosoftParser(), new AmazonParser()};
	
	public String standardize(String path) {
		Charset charset = Charset.forName("US-ASCII");
		try (BufferedReader reader = Files.newBufferedReader(Paths.get(path), charset)) {

			// First line represents the name of a company
			String company = reader.readLine();
			for(DataParser p : parsers){
				if(p.getCompanyID().equalsIgnoreCase(company)){
					StringBuffer buffer = new StringBuffer();
				    String line = null;
				    while ((line = reader.readLine()) != null) {
				    	HashMap<String, String> kvs = p.parse(line);
				    	for(String key : kvs.keySet()){
				    		buffer.append(key.trim() + " : " + kvs.get(key).trim());
				    		buffer.append("\n");
				    	}
				    }
				    return buffer.toString();
				}
			}
			return "Company not defined";
		} catch (IOException x) {
		    System.err.format("IOException: %s%n", x);
		    return x.getMessage();
		}	
	}
	public String getCompany(String path) {
		Charset charset = Charset.forName("US-ASCII");
		try (BufferedReader reader = Files.newBufferedReader(Paths.get(path), charset)) {
			String company = reader.readLine();
			return company;
		} catch (IOException x) {
		    System.err.format("IOException: %s%n", x);
		    return x.getMessage();
		}	
	}
}
