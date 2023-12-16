package com.awinas.learning.interview.optum;
import java.util.HashSet;
import java.util.Set;

public class MainMethod {

	public static void main(String[] args) {

		try {
//			A a = new A();
//			A a1 = new A();
//			B b = new B();
//			B b1 = new B();
//			C c = new C();
//			C c1 = new C();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println(planet.mer == planet.mer);
		System.out.println(planet.mer.equals(planet.mer));
		
		Set<Integer> s = new HashSet<>();
		s.add(new Integer(1));
		s.add(new Integer(1));
		System.out.println(s.size());
	}

}

enum planet{
	mer ,cur;
}
