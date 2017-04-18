package com.arun.pojo;

import java.util.ArrayList;
import java.util.List;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;

public class ClassDetails {

	private ClassOrInterfaceDeclaration classDet;
	
	private List<Attribute> attributes;
	
	private List<Method> methods;
	
	private List<Relation> relations;
	
	private List<Constructor> constructors;

	public ClassDetails() {
		attributes = new ArrayList<Attribute>();
		methods = new ArrayList<Method>();
		relations = new ArrayList<Relation>();
		constructors = new ArrayList<Constructor>();
	}

	public List<Constructor> getConstructors() {
		return constructors;
	}

	public void setConstructors(List<Constructor> constructors) {
		this.constructors = constructors;
	}

	public ClassOrInterfaceDeclaration getClassDet() {
		return classDet;
	}

	public void setClassDet(ClassOrInterfaceDeclaration classDet) {
		this.classDet = classDet;
	}

	public List<Attribute> getAttributes() {
		return attributes;
	}

	public void setAttributes(List<Attribute> attributes) {
		this.attributes = attributes;
	}

	public List<Method> getMethods() {
		return methods;
	}

	public void setMethods(List<Method> methods) {
		this.methods = methods;
	}

	public List<Relation> getRelations() {
		return relations;
	}

	public void setRelations(List<Relation> relations) {
		this.relations = relations;
	}

	@Override
	public String toString() {
		return "ClassDetails [classDet=" + classDet + ", attributes=" + attributes + ", methods=" + methods
				+ ", relations=" + relations + ", constructors=" + constructors + "]";
	}

}
