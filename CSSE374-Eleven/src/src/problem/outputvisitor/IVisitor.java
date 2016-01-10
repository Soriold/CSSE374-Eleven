package src.problem.outputvisitor;

public interface IVisitor {

	public void preVisit(ITraverser c);

	public void visit(ITraverser c);

	public void postVisit(ITraverser c);

	public void addVisit(VisitType type, Class<?> clazz, IVisitMethod vm);

	public void removeVisit(VisitType type, Class<?> clazz);

}
