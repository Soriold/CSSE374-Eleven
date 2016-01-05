package lab1_3;

import java.util.ArrayList;

public interface Subject {
	void registerObserver(Observer o);
	void removeObserver(Observer o);
	ArrayList<String> notifyObservers(String action, String fileName);
}
