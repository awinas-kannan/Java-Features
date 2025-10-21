package com.awinas.learning.SerializationAndDeserialization;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Base64;

//Generate a serialization string using SerializationUtility class
//Use the string here to deserilize... 


//For InvalidClassException Exception.... 
//Create serilized string with a serialVersionUID inAppleProduct
//Then while deserilization , use someother serialVersionUID.. InvalidClassException is thrown
//Error .....
//Exception in thread "main" java.io.InvalidClassException: com.awinas.learning.SerializationAndDeserialization.AppleProduct;
//local class incompatible: stream classdesc serialVersionUID = 1234567, local class serialVersionUID = 12345678

public class DeserializationUtility {

	public static void main(String[] args) throws ClassNotFoundException, IOException {

		String serializedObj = "rO0ABXNyAEBjb20uYXdpbmFzLmxlYXJuaW5nLlNlcmlhbGl6YXRpb25BbmREZXNlcmlhbGl6YXRpb24uQXBwbGVQcm9kdWN0AAAAAAC8YU4CAANMAA1oZWFkcGhvbmVQb3J0dAASTGphdmEvbGFuZy9TdHJpbmc7TAANbGlnaHRuaW5nUG9ydHEAfgABTAAPdGh1bmRlcmJvbHRQb3J0cQB+AAF4cHQAEWhlYWRwaG9uZVBvcnQyMDIwcHQAE3RodW5kZXJib2x0UG9ydDIwMjA=";
		System.out.println("Deserializing AppleProduct...");

		AppleProduct deserializedObj = (AppleProduct) deSerializeObjectFromString(serializedObj);

		System.out.println("Headphone port of AppleProduct:" + deserializedObj.getHeadphonePort());
		System.out.println("Thunderbolt port of AppleProduct:" + deserializedObj.getThunderboltPort());
		System.out.println("Lightning Port of AppleProduct:" + deserializedObj.getLightningPort());
	}

	public static Object deSerializeObjectFromString(String s) throws IOException, ClassNotFoundException {

		byte[] data = Base64.getDecoder().decode(s);
		ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data));
		Object o = ois.readObject();
		ois.close();
		return o;
	}
}