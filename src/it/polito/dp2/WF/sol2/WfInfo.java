package it.polito.dp2.WF.sol2;

import java.util.List;

//import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class WfInfo {
	
	private List<Workflow> workflow;

	public List<Workflow> getWorkflow() {
		return workflow;
	}

	@XmlElement
	public void setWorkflow(List<Workflow> workflow) {
		this.workflow = workflow;
	}

}
