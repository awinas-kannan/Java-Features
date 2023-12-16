package com.awinas.learning.SerializationAndDeserialization;

import java.io.Serializable;

public class AppleProduct implements Serializable {

	private static final long serialVersionUID = 12345678L;

	public String headphonePort;
	public String thunderboltPort;
	public String lightningPort;

	public String getLightningPort() {
		return lightningPort;
	}

	public String getHeadphonePort() {
		return headphonePort;
	}

	public String getThunderboltPort() {
		return thunderboltPort;
	}

	
}