package it.polito.dp2.WF.sol2;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Actor {
	
	private String name;
	private String role;
	
	public String getName() {
		return name;
	}

	@XmlAttribute
	public void setName(String name) {
		this.name = name;
	}
	
	public String getRole() {
		return role;
	}

	@XmlElement
	public void setRole(String role) {
		this.role = role;
	}

}
