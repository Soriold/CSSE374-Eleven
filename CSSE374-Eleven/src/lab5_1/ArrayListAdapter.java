package lab5_1;

import java.util.Enumeration;
import java.util.Iterator;

public class ArrayListAdapter <E> implements Enumeration<E> {

	private Iterator<E> iterator;
	
	public ArrayListAdapter(Iterator<E> iterator) {
		this.iterator = iterator;
	}
	
	@Override
	public boolean hasMoreElements() {
		return this.iterator.hasNext();
	}

	@Override
	public E nextElement() {
		return this.iterator.next();
	}

}
