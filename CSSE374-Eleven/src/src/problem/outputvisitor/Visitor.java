package src.problem.outputvisitor;

import java.util.HashMap;
import java.util.Map;

public class Visitor implements IVisitor {

	Map<LookupKey, IVisitMethod> keyToVisitMethod;
	
	public Visitor() {
		this.keyToVisitMethod = new HashMap<>();
	}
	
	private void doVisit(VisitType v, ITraverser c) {
		LookupKey key = new LookupKey(v, c.getClass());
		IVisitMethod m = this.keyToVisitMethod.get(key);
		if (m != null) {
			m.execute(c);
		}
	}
	
	@Override
	public void preVisit(ITraverser c) {
		this.doVisit(VisitType.PreVisit, c);
	}

	@Override
	public void visit(ITraverser c) {
		this.doVisit(VisitType.Visit, c);
	}

	@Override
	public void postVisit(ITraverser c) {
		this.doVisit(VisitType.PostVisit, c);
	}

	@Override
	public void addVisit(VisitType type, Class<?> clazz, IVisitMethod vm) {
		LookupKey key = new LookupKey(type, clazz);
		this.keyToVisitMethod.put(key, vm);

	}

	@Override
	public void removeVisit(VisitType type, Class<?> clazz) {
		LookupKey key = new LookupKey(type, clazz);
		this.keyToVisitMethod.remove(key);
	}

}
