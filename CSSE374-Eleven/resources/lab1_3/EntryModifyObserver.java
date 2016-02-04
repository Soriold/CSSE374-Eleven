package lab1_3;

public class EntryModifyObserver implements Observer {

	@Override
	public String update(String action, String fileName) {
		if(!action.equals("ENTRY_MODIFY"))
			System.out.println(fileName + " Modified!");
		return null;
	}


}
