package it.polito.dp2.WF.sol2;

import java.util.List;

//import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class WfInfo {
	
	private List<Workflow> workflow;
	private List<Process> process;

	public List<Workflow> getWorkflow() {
		return workflow;
	}

	@XmlElement
	public void setWorkflow(List<Workflow> workflow) {
		this.workflow = workflow;
	}
	
	public List<Process> getProcess() {
		return process;
	}

	@XmlElement
	public void setProcess(List<Process> process) {
		this.process = process;
	}

}
