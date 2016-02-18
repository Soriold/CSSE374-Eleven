package src.problem.components;

import java.util.Set;

public interface ITaggable {

	public void addTag(String tag);

	public Set<String> getTags();

	public static void setTagTypes(Set<String> tagTypes) {
		AbstractTaggable.tagTypes = tagTypes;
	}
}
