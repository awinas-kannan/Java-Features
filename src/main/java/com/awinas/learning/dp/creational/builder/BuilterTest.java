package com.awinas.learning.dp.creational.builder;

public class BuilterTest {
	public static void main(String[] args) {
		// Using builder to get the object in a single line of code and
		// without any inconsistent state or arguments management issues
		Computer comp = new Computer.ComputerBuilder("500 GB", "2 GB").
							setBluetoothEnabled(true)
						    .setGraphicsCardEnabled(true).build();
		System.out.println(comp.toString());
		
		
		Computer comp1 = new Computer.ComputerBuilder("100 GB", "4 GB").build();
		System.out.println(comp1.toString());
	}
}
