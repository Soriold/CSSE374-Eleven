package lab1_3;

public class HtmlCreateObserver implements Observer {

	@Override
	public String update(String action, String fileName) {
		if(!action.equals("ENTRY_CREATE")) {
			return null;
		}
		if(fileName.endsWith(".html") || fileName.endsWith(".htm")) {
			// For Mac, use the "open" command instead of "explorer"
			return "explorer";
		}
		return null;
	}

}
