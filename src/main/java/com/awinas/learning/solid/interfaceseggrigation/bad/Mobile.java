package com.awinas.learning.solid.interfaceseggrigation.bad;

public class Mobile implements IComponent {

	@Override
	public void mouseOver(String event) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void touch(String event) {

	}

	@Override
	public void swipe(String event) {

	}

	@Override
	public void validate(String event) {

	}

}
