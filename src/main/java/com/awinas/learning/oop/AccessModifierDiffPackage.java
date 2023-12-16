package com.awinas.learning.oop;

import com.awinas.learning.oop.accessmodifier.AccessModifiers;

public class AccessModifierDiffPackage {

	public static void main(String[] args) {

		AccessModifiers am = new AccessModifiers();

		// private variable Cannot be accessed
		// String privateModifier = am.privateModifier ;

		//Protected variable / method cannot be accessed in other package 
		//it can be accessed if the AccessModifierDiffPackage extends Employee
		//String protectedModifier = am.protectedModifier;  
		//String protectedModifier = protectedModifier;
		
		//default variable / method cannot be accessed in other package
		//It can be accessed only in same package
		//String defaultModifier = am.defaultModifier;
	
		
		// Public variable can be accessed
		String name = am.publicMofifier;
		

	}

}
