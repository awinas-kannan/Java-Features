package com.awinas.learning.Java10;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class LVTI {

	static {
		var x = "Hi there";
		System.out.println(x);
	}
	   
	public static void main(String[] args) {

		int x = 10;
		var xVar = 10;

		List<String> str = Arrays.asList("1", "2");
		var strVar = Arrays.asList("1", "2");
		var strVarOf = List.of("1", "2");

		Map<String, String> map = Map.of("one", "1", "two", "2");
		var mapVar = Map.of("one", "1", "two", "2");

		System.out.println(x);
		System.out.println(xVar);
		System.out.println(str);
		System.out.println(strVar);
		System.out.println(strVarOf.getClass());
		System.out.println(strVar.getClass());
		System.out.println(map);
		System.out.println(mapVar);
		System.out.println(mapVar.getClass());

		/*
		 * Usage allowed as: 
		 * Local variables with initializers 
		 * Indexes in the enhanced for-loop 
		 * Locals declared in a traditional for-loop
		 * In a static/instance initialization block
		 * 
		 */

		var blogName = "howtodoinjava.com"; // Local variables with initializer
		for (var object : str) { // index in enhanced for-loop
			System.out.println(object);
		}
		for (var i = 0; i < str.size(); i++) { // local variables in for-loop
			System.out.println(str.get(i));
		}
		
		/*
		 * Usage NOT allowed as: 
		 * Global variable 
		 * Method parameters 
		 * Constructor parameters
		 * Method return types 
		 * Class fields 
		 * Catch formals (or any other kind of variable declaration)
		 */

		//var firstName;	//Not allowed as class fields

		//public LVTI(var param){ }	//Not allowed as parameter 

		//try{ } catch(var ex){	} //Not allowed as catch formal 

		//public var demoMethod(){ return null; } //Not allowed in method return type

		//public Integer demoMethod2( var input ){ return null;	} //Not allowed in method parameters
		
	}

}
