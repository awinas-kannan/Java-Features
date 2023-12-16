package com.awinas.learning.solid.interfaceseggrigation.good;

public interface TouchUiComponent extends UiComponent {
	void touch(String event);

	void swipe(String event);
}
