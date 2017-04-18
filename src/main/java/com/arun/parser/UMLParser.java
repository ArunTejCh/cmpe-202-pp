package com.arun.parser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import com.arun.pojo.Attribute;
import com.arun.pojo.ClassDetails;
import com.arun.pojo.Constructor;
import com.arun.pojo.Method;
import com.arun.pojo.Relation;
import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.AccessSpecifier;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.BodyDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.body.TypeDeclaration;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.type.Type;

public class UMLParser {

	private static final String OUTPUT_FILENAME = "plantuml.txt";

	public static void main(String[] args) {

		System.out.println("Got the folder path as");

		System.out.println(args[0]);

		System.out.println("End");

		UMLParser p = new UMLParser();
		List<File> javaFiles = p.getJavaFiles(args[0]);

		// Incorrect path returning
		if (javaFiles == null) {
			return;
		}

		List<ClassDetails> parsedFiles = p.parseDetails(javaFiles);
		p.createPlantUMLInputFile(parsedFiles);

		return;
	}

	private List<File> getJavaFiles(String folderPath) {

		File folder = new File(folderPath);

		if (!folder.exists()) {
			System.out.println("Given path doesnt exist!!!");
			return null;
		}

		List<File> retList = new ArrayList<File>();
		File[] fileList = folder.listFiles();

		for (File f : fileList) {

			System.out.println(f.getName());
			String extension = "";
			String fileName = f.getName();
			int i = fileName.lastIndexOf('.');
			if (i > 0) {
				extension = fileName.substring(i + 1);
			}
			if (extension.equalsIgnoreCase("java"))
				retList.add(f);
		}

		return retList;
	}

	private List<ClassDetails> parseDetails(List<File> javaFiles) {


		List<ClassDetails> retList = new ArrayList<ClassDetails>();

		for (File sourceFile : javaFiles) {
			ClassDetails cur = new ClassDetails();

			try {
				CompilationUnit compilationUnit = JavaParser.parse(sourceFile);
				List<TypeDeclaration<?>> types = compilationUnit.getTypes();

				for (TypeDeclaration<?> type : types) {
					if (type instanceof ClassOrInterfaceDeclaration) {
						ClassOrInterfaceDeclaration classDec = (ClassOrInterfaceDeclaration) type;
						cur.setClassDet(classDec);
						NodeList<ClassOrInterfaceType> interfaces = classDec.getImplementedTypes();
						NodeList<ClassOrInterfaceType> parent = classDec.getExtendedTypes();
						
						for(ClassOrInterfaceType imp : interfaces){
							String nme = imp.getNameAsString();
							Relation r = new Relation();
							r.setSource(classDec.getNameAsString());
							r.setDest(nme);
							r.setType(Relation.RelationType.REALIZATION);
							
							cur.getRelations().add(r);
						}
						
						for(ClassOrInterfaceType imp : parent){
							String nme = imp.getNameAsString();
							Relation r = new Relation();
							r.setSource(classDec.getNameAsString());
							r.setDest(nme);
							r.setType(Relation.RelationType.GENERALIZATION);
							
							cur.getRelations().add(r);
						}
					}

					List<BodyDeclaration<?>> members = type.getMembers();

					for (BodyDeclaration<?> member : members) {
						if (member instanceof MethodDeclaration) {
							MethodDeclaration mem = ((MethodDeclaration) member);
							String name = mem.getName().toString();
							Type ret = mem.getType();
							NodeList<Parameter> params = mem.getParameters();
							EnumSet<Modifier> mods = mem.getModifiers();
							Method met = new Method();
							met.setModifiers(mods);
							met.setName(name);
							met.setParams(params);
							met.setReturnType(ret);
							cur.getMethods().add(met);

						} else if (member instanceof FieldDeclaration) {
							FieldDeclaration mem = ((FieldDeclaration) member);
							String var = mem.getVariables().get(0).getName().toString();
							Type t = mem.getElementType();
							EnumSet<Modifier> mods = mem.getModifiers();
							if (!mem.isPrivate() && !mem.isPublic() && !mem.isProtected()) {

							}
							Attribute att = new Attribute();
							att.setName(var);
							att.setType(t);
							att.setModifiers(mods);

							cur.getAttributes().add(att);
						} else if (member instanceof ConstructorDeclaration) {
							ConstructorDeclaration mem = ((ConstructorDeclaration) member);
							NodeList<Parameter> params = mem.getParameters();
							EnumSet<Modifier> mods = mem.getModifiers();
							Constructor con = new Constructor();
							con.setModifiers(mods);
							con.setParams(params);

							cur.getConstructors().add(con);

						}
					}
				}

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			retList.add(cur);
		}

		return retList;
	}

	private String createPlantUMLInputFile(List<ClassDetails> parsedFiles) {
		try {
			PrintWriter writer = new PrintWriter(OUTPUT_FILENAME, "UTF-8");
			writer.println("@startuml");
			writer.println("skinparam classAttributeIconSize 0");
			for (ClassDetails det : parsedFiles) {

				String classPreface = det.getClassDet().getNameAsString() + " : ";

				if (det.getClassDet().isInterface()) {
					writer.println("Interface " + det.getClassDet().getNameAsString());
				} else if (det.getClassDet().isAbstract()) {
					writer.println("Abstract " + det.getClassDet().getNameAsString());
				} else {
					writer.println("Class " + det.getClassDet().getNameAsString());
				}
				
				//Print Relations
				for(Relation r : det.getRelations()){
					
					String relation = null;
					
					switch(r.getType()){
					case GENERALIZATION:
						relation = "<|--";
						break;
					case REALIZATION:
						relation = "<|..";
						break;
					default:
						relation = "--";
						break;
					}
					writer.println(r.getDest() + " " + relation + " " + r.getSource());
					
				}
				
				
				
				// Print the attributes
				for (Attribute attr : det.getAttributes()) {

					StringBuilder temp = new StringBuilder();
					temp.append(classPreface);
					EnumSet<Modifier> mods = attr.getModifiers();
					this.printModifier(temp, mods);
					temp.append(attr.getName().toString() + " : ");
					temp.append(attr.getType());
					writer.println(temp.toString());
				}

				// Print the constructors
				for (Constructor con : det.getConstructors()) {
					StringBuilder temp = new StringBuilder();
					temp.append(classPreface);
					EnumSet<Modifier> mods = con.getModifiers();
					this.printModifier(temp, mods);
					temp.append(det.getClassDet().getNameAsString() + "(");
					for (Parameter param : con.getParams()) {
						temp.append(param.getNameAsString() + " : " + param.getType().toString());
					}

					temp.append(")");
					writer.println(temp.toString());
				}

				// Print the methods
				for (Method met : det.getMethods()) {

					StringBuilder temp = new StringBuilder();
					temp.append(classPreface);
					EnumSet<Modifier> mods = met.getModifiers();
					this.printModifier(temp, mods);
					temp.append(met.getName().toString() + "(");

					for (Parameter param : met.getParams()) {
						temp.append(param.getNameAsString() + " : " + param.getType().toString());
					}

					temp.append(") : " + met.getReturnType());
					writer.println(temp.toString());
				}

			}

			writer.println("@enduml");
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	private void printModifier(StringBuilder temp, EnumSet<Modifier> mods) {

		for (Modifier mod : mods) {
			switch (mod) {
			case PRIVATE:
				temp.append("- ");
				break;
			case PUBLIC:
				temp.append("+ ");
				break;
			case PROTECTED:
				temp.append("# ");
				break;
			case STATIC:
				temp.append("{static}");
				break;
			}
		}

		if (Modifier.getAccessSpecifier(mods) == AccessSpecifier.DEFAULT) {
			temp.append("~ ");
		}
	}
}
