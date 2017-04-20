package com.arun.pojo;

public class Relation {

	private String source;
	
	private String dest;
	
	private RelationType type;
	
	private String sourceMultiplicity;
	
	private String destMultiplicity;
	
	public enum RelationType {
		GENERALIZATION, COMPOSITION, ASSOCIATION, DEPENDENCY, AGGREGATION, REALIZATION
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getDest() {
		return dest;
	}

	public void setDest(String dest) {
		this.dest = dest;
	}

	public RelationType getType() {
		return type;
	}

	public void setType(RelationType type) {
		this.type = type;
	}

	public String getSourceMultiplicity() {
		return sourceMultiplicity;
	}

	public void setSourceMultiplicity(String sourceMultiplicity) {
		this.sourceMultiplicity = sourceMultiplicity;
	}

	public String getDestMultiplicity() {
		return destMultiplicity;
	}

	public void setDestMultiplicity(String destMultiplicity) {
		this.destMultiplicity = destMultiplicity;
	}

	@Override
	public String toString() {
		return "Relation [source=" + source + ", dest=" + dest + ", type=" + type + ", sourceMultiplicity="
				+ sourceMultiplicity + ", destMultiplicity=" + destMultiplicity + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dest == null) ? 0 : dest.hashCode());
		result = prime * result + ((destMultiplicity == null) ? 0 : destMultiplicity.hashCode());
		result = prime * result + ((source == null) ? 0 : source.hashCode());
		result = prime * result + ((sourceMultiplicity == null) ? 0 : sourceMultiplicity.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		Relation other = (Relation) obj;
		if (dest == null) {
			if (other.dest != null)
				return false;
		} else if (!dest.equals(other.dest))
			return false;
		if (destMultiplicity == null) {
			if (other.destMultiplicity != null)
				return false;
		} else if (!destMultiplicity.equals(other.destMultiplicity))
			return false;
		if (source == null) {
			if (other.source != null)
				return false;
		} else if (!source.equals(other.source))
			return false;
		if (sourceMultiplicity == null) {
			if (other.sourceMultiplicity != null)
				return false;
		} else if (!sourceMultiplicity.equals(other.sourceMultiplicity))
			return false;
		if (type != other.type)
			return false;
		return true;
	}
	
	
	
	
}
