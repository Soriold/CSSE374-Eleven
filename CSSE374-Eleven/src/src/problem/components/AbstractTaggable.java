package src.problem.components;

import java.util.HashSet;
import java.util.Set;

public abstract class AbstractTaggable implements ITaggable {

	private Set<String> tags;
	static Set<String> tagTypes;

	@Override
	public void addTag(String tag) {
		if (tagTypes.contains(tag)) {
			if (tags == null) {
				tags = new HashSet<String>();
			}
			tags.add(tag);
		}
	}

	@Override
	public Set<String> getTags() {
		return tags;
	}

}
