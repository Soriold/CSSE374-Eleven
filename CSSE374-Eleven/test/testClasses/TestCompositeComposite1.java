package testClasses;

import java.util.ArrayList;

public class TestCompositeComposite1 extends TestCompositeComponent {
	private ArrayList<TestCompositeComponent> children;

	public TestCompositeComposite1() {
		this.children = new ArrayList<TestCompositeComponent>();
	}
	
	@Override
	public void add(TestCompositeComponent c) {
		this.children.add(c);
	}
	
	@Override
	public void remove(TestCompositeComponent c) {
		this.children.remove(c);
	}
	
	@Override
	public TestCompositeComponent getChild(int index) {
		return this.children.get(index);
	}
}
