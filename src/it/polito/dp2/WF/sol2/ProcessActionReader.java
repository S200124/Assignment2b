package it.polito.dp2.WF.sol2;

import org.w3c.dom.Node;

public class ProcessActionReader extends ActionReader {

	private String workflowName;
	
	public ProcessActionReader(Node act, String wfn) {
		super(act, wfn);
		workflowName = wfn;
	}

	public WorkflowReader getActionWorkflow() {
		return (new WorkflowReader(WorkFlowModel.findWorkflow(workflowName)));
	}

}
