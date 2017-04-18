package com.arun.pojo;

import java.util.EnumSet;

import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.Parameter;

public class Constructor {

	private EnumSet<Modifier> modifiers;

	private NodeList<Parameter> params;

	public EnumSet<Modifier> getModifiers() {
		return modifiers;
	}

	public void setModifiers(EnumSet<Modifier> modifiers) {
		this.modifiers = modifiers;
	}

	public NodeList<Parameter> getParams() {
		return params;
	}

	public void setParams(NodeList<Parameter> params) {
		this.params = params;
	}

	@Override
	public String toString() {
		return "Constructor [modifiers=" + modifiers + ", params=" + params + "]";
	}
	
}
