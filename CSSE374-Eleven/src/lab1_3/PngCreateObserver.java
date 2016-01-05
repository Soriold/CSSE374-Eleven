package lab1_3;

public class PngCreateObserver implements Observer {

	@Override
	public String update(String action, String fileName) {
		if(!action.equals("ENTRY_CREATE")) {
			
			return null;
		}
		if(fileName.endsWith(".PNG") || fileName.endsWith(".png")) {
			return "Explorer";
		}
		return null;
	}

}
