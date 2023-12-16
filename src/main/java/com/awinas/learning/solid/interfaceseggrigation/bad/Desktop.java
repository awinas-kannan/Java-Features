package com.awinas.learning.solid.interfaceseggrigation.bad;

public class Desktop implements IComponent {

	@Override
	public void mouseOver(String event) {

	}

	@Override
	public void touch(String event) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void swipe(String event) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void validate(String event) {

	}

}
