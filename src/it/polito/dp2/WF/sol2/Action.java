package it.polito.dp2.WF.sol2;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Action {
	
	private String name;
	private String type;
	private String automInst;
	private String role;
	private String nestedWorkflow;
	private FollowingActions followingActions;
	
	public String getName() {
		return name;
	}

	@XmlAttribute
	public void setName(String name) {
		this.name = name;
	}
	
	public String getType() {
		return type;
	}

	@XmlAttribute
	public void setType(String type) {
		this.type = type;
	}
	
	public String getAutomInst() {
		return automInst;
	}

	@XmlAttribute
	public void setAutomInst(String automInst) {
		this.automInst = automInst;
	}
	
	public String getRole() {
		return role;
	}

	@XmlElement
	public void setRole(String role) {
		this.role = role;
	}
	
	public String getNestedWorkflow() {
		return nestedWorkflow;
	}

	@XmlElement
	public void setNestedWorkflow(String nestedWorkflow) {
		this.nestedWorkflow = nestedWorkflow;
	}
	
	public FollowingActions getFollowingActions() {
		return followingActions;
	}

	@XmlElement
	public void setFollowingActions(FollowingActions followingActions) {
		this.followingActions = followingActions;
	}

}
