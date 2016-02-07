package it.polito.dp2.WF.sol2;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Workflow {

	private String name;
	private List<Action> action;
	
	public String getName() {
		return name;
	}

	@XmlAttribute
	public void setName(String name) {
		this.name = name;
	}
	
	public List<Action> getAction() {
		return action;
	}

	@XmlElement
	public void setAction(List<Action> action) {
		this.action = action;
	}
}
