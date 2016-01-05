package lab1_3;

public class TxtCreateObserver implements Observer {

	@Override
	public String update(String action, String fileName) {
		if(!action.equals("ENTRY_CREATE")) {
			
			return null;
		}
		if(fileName.endsWith(".txt")) {
			return "Notepad";
		}
		return null;
	}

}
