package src.problem.outputvisitor;

class LookupKey {
	private VisitType visitType;
	private Class<?> clazz;
	
	public LookupKey(VisitType type, Class<?> clazz) {
		this.clazz = clazz;
		this.visitType = type;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((clazz == null) ? 0 : clazz.hashCode());
		result = prime * result + ((visitType == null) ? 0 : visitType.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LookupKey other = (LookupKey) obj;
		if (clazz == null) {
			if (other.clazz != null)
				return false;
		} else if (!clazz.equals(other.clazz))
			return false;
		if (visitType != other.visitType)
			return false;
		if (!other.clazz.isAssignableFrom(this.clazz))
			return false;
			
		return true;
	}
	
}
