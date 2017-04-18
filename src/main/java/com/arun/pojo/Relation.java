package com.arun.pojo;

public class Relation {

	private String source;
	
	private String dest;
	
	private String type;
	
	private String sourceMultiplicity;
	
	private String destMultiplicity;

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

	public String getType() {
		return type;
	}

	public void setType(String type) {
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
	
	
	
	
}
