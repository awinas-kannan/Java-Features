package com.awinas.learning.oop.accessmodifier;

import com.awinas.learning.oop.accessmodifier.AccessModifiers;

public class AccessModifierSamePackage {

	public static void main(String[] args) {

		AccessModifiers am = new AccessModifiers();

		// private variable Can be accessed only in same class
		// String privateModifier = am.privateModifier ;

		// default variable / method can be accessed in same package
		// default variable / method cannot be accessed in other package
		String protectedModifier = am.protectedModifier;

		// default variable / method cannot be accessed in other package
		// It can be accessed only in same package
		String defaultModifier = am.defaultModifier;

		// Public variable can be accessed
		String publicMofifier = am.publicMofifier;

		// Defalut class can be extended and used in same package
		// It cant be extended or used in other package
		DefaultClass dc = new DefaultClass();
	}

}
