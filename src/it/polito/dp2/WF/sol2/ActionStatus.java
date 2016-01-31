package it.polito.dp2.WF.sol2;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ActionStatus {
	
	private String actionName;
	private String terminatedAt;
	private Actor actor;
	
	public String getActionName() {
		return actionName;
	}

	@XmlElement
	public void setActionName(String actionName) {
		this.actionName = actionName;
	}
	
	public String getTerminatedAt() {
		return terminatedAt;
	}

	@XmlAttribute
	public void setTerminatedAt(String terminatedAt) {
		this.terminatedAt = terminatedAt;
	}
	
	public Actor getActor() {
		return actor;
	}

	@XmlElement
	public void setActor(Actor actor) {
		this.actor = actor;
	}

}
