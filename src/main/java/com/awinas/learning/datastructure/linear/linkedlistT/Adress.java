package com.awinas.learning.datastructure.linear.linkedlistT;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "ADDRESS")
@XmlAccessorType(XmlAccessType.FIELD)
public class Adress {

	@XmlElement(name = "city", nillable = true, required = true)
	String city;

	@XmlElement(name = "state", nillable = true, required = true)
	String state;

	public String getCity() {
		return city;
	}

	public void setCity(final String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(final String state) {
		this.state = state;
	}

	@Override
	public String toString() {
		return "Adress [city=" + city + ", state=" + state + "]";
	}




}
