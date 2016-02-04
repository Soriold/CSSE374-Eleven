package lab1_3;

public class EntryDeleteObserver implements Observer {

	@Override
	public String update(String action, String fileName) {
		System.out.println("obs");
		if(!action.equals("ENTRY_DELETE"))
			System.out.println(fileName + " Deleted!");
		return null;
	}

}
