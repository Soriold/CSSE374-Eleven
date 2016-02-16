package src.problem.components;

import java.util.List;
import java.util.Set;

import src.problem.outputvisitor.ITraverser;

public interface IModel extends ITraverser {
	public List<IClass> getClasses();
	public Set<IRelation> getRelations();
	public void addClass(IClass clazz);
	public void addRelation(IRelation relation);
	public void validateRelations();
}
