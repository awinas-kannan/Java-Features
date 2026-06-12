package com.awinas.learning.dp.structural.adapter.journaldev;

public class AdapterTest {

	public static void main(String[] args) {

		Socket soc3v = new Socket3v();
		Mobile redmi = new SocketObjectAdapter(soc3v);
		System.out.println(redmi.getDesiredVolts());

		Socket soc12v = new Socket12v();
		Mobile onePlus = new SocketObjectAdapter(soc12v);
		System.out.println(onePlus.getDesiredVolts());
	}

}
