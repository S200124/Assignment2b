package it.polito.dp2.WF.sol2;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Process {

	private String startAt;
	private String workflowName;
	private List<ActionStatus> actionStatus;
	
	public String getStartAt() {
		return startAt;
	}

	@XmlAttribute
	public void setStartAt(String startAt) {
		this.startAt = startAt;
	}
	
	public String getWorkflowName() {
		return workflowName;
	}

	@XmlAttribute
	public void setWorkflowName(String workflowName) {
		this.workflowName = workflowName;
	}
	
	public List<ActionStatus> getActionStatus() {
		return actionStatus;
	}

	@XmlElement
	public void setActionStatus(List<ActionStatus> actionStatus) {
		this.actionStatus = actionStatus;
	}
}
