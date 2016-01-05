package lab13code;

import java.nio.file.Path;

public class EntryCreatedHandler implements IHandler {

	@Override
	public Process eventProcessed(String event, Path f) {
		if (event.equals("ENTRY_CREATE")) {
			System.out.println("Processing ENTRY_CREATE event for file: " + f);
		}
		return null;
	}

}
