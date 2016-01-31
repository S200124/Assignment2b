package it.polito.dp2.WF.sol2;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Process {

	private String startAt;
	private List<ActionStatus> actionStatus;
	
	public String getStartAt() {
		return startAt;
	}

	@XmlAttribute
	public void setStartAt(String startAt) {
		this.startAt = startAt;
	}
	
	public List<ActionStatus> getActionStatus() {
		return actionStatus;
	}

	@XmlElement
	public void setActionStatus(List<ActionStatus> actionStatus) {
		this.actionStatus = actionStatus;
	}
}
