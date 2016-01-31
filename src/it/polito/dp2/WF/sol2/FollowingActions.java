package it.polito.dp2.WF.sol2;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class FollowingActions {
	
	private List<String> actionName;
	
	public List<String> getActionName() {
		return actionName;
	}

	@XmlElement
	public void setActionName(List<String> actionName) {
		this.actionName = actionName;
	}

}
