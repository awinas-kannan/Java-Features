package com.awinas.learning.coreconcepts.innerclass;

public class MethodLocalInnerClass {
	
	public static void main(String[] args) {

		new printer().display();
	}
	
}

class printer {

	private int x = 24;

	void display()

	{

		class print {

			void print() {
				System.out.println(x);
			}
		}

		print p = new print();
		p.print();
	}
}
