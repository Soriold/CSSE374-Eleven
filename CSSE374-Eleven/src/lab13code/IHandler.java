package lab13code;

import java.io.File;
import java.nio.file.Path;

public interface IHandler {
	public Process eventProcessed(String event, Path f);
}
