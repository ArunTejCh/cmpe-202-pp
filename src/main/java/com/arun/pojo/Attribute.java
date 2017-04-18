package com.arun.pojo;

import java.util.EnumSet;

import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.type.Type;

public class Attribute {

	private String name;

	private Type type;

	private EnumSet<Modifier> modifiers;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public EnumSet<Modifier> getModifiers() {
		return modifiers;
	}

	public void setModifiers(EnumSet<Modifier> modifiers) {
		this.modifiers = modifiers;
	}

	@Override
	public String toString() {
		return "Attribute [name=" + name + ", type=" + type + ", modifiers=" + modifiers + "]";
	}

	
}
