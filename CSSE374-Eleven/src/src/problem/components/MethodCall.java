package src.problem.components;

import java.util.ArrayList;
import java.util.List;

public class MethodCall implements IMethodCall {
	
	private String sourceClass;
	private String sourceMethod;
	
	private String destClass;
	private String destMethod;
	
	private ArrayList<String> destParameters;
	private ArrayList<String> sourceParameters;
	
	public MethodCall(String sourceClass, String sourceMethod, String destClass, String destMethod) {
		this.setSourceClass(sourceClass);
		this.setSourceMethod(sourceMethod);
		this.setDestinationClass(destClass);
		this.setDestinationMethod(destMethod);
		this.destParameters = new ArrayList<>();
		this.sourceParameters = new ArrayList<>();
	}

	@Override
	public String getSourceClass() {
		return this.sourceClass;
	}

	@Override
	public String getSourceMethod() {
		return this.sourceMethod;
	}

	@Override
	public String getDestinationClass() {
		return this.destClass;
	}

	@Override
	public String getDestinationMethod() {
		return this.destMethod;
	}

	@Override
	public List<String> getDestParameters() {
		return this.destParameters;
	}

	@Override
	public void setSourceClass(String clazz) {
		this.sourceClass = clazz;
	}

	@Override
	public void setSourceMethod(String method) {
		this.sourceMethod = method;
	}

	@Override
	public void setDestinationClass(String clazz) {
		this.destClass = clazz;
	}

	@Override
	public void setDestinationMethod(String method) {
		this.destMethod = method;
	}

	@Override
	public void addDestParameter(String p) {
		this.destParameters.add(p);

	}

	@Override
	public List<String> getSourceParameters() {
		return this.sourceParameters;
	}

	@Override
	public void addSourceParameter(String p) {
		this.sourceParameters.add(p);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((destClass == null) ? 0 : destClass.hashCode());
		result = prime * result + ((destMethod == null) ? 0 : destMethod.hashCode());
		result = prime * result + ((destParameters == null) ? 0 : destParameters.hashCode());
		result = prime * result + ((sourceClass == null) ? 0 : sourceClass.hashCode());
		result = prime * result + ((sourceMethod == null) ? 0 : sourceMethod.hashCode());
		result = prime * result + ((sourceParameters == null) ? 0 : sourceParameters.hashCode());
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
		MethodCall other = (MethodCall) obj;
		if (destClass == null) {
			if (other.destClass != null)
				return false;
		} else if (!destClass.equals(other.destClass))
			return false;
		if (destMethod == null) {
			if (other.destMethod != null)
				return false;
		} else if (!destMethod.equals(other.destMethod))
			return false;
		if (destParameters == null) {
			if (other.destParameters != null)
				return false;
		} else if (!destParameters.equals(other.destParameters))
			return false;
		if (sourceClass == null) {
			if (other.sourceClass != null)
				return false;
		} else if (!sourceClass.equals(other.sourceClass))
			return false;
		if (sourceMethod == null) {
			if (other.sourceMethod != null)
				return false;
		} else if (!sourceMethod.equals(other.sourceMethod))
			return false;
		if (sourceParameters == null) {
			if (other.sourceParameters != null)
				return false;
		} else if (!sourceParameters.equals(other.sourceParameters))
			return false;
		return true;
	}


}
