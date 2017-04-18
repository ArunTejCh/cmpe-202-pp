package com.arun.pojo;

import java.util.EnumSet;

import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.type.Type;

public class Method {

	private String name;

	private Type returnType;

	private EnumSet<Modifier> modifiers;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Type getReturnType() {
		return returnType;
	}

	public void setReturnType(Type returnType) {
		this.returnType = returnType;
	}

	public EnumSet<Modifier> getModifiers() {
		return modifiers;
	}

	public void setModifiers(EnumSet<Modifier> modifiers) {
		this.modifiers = modifiers;
	}

	@Override
	public String toString() {
		return "Method [name=" + name + ", returnType=" + returnType + ", modifiers=" + modifiers + "]";
	}

}
