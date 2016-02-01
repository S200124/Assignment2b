package it.polito.dp2.WF.sol2;

public class ProcessActionReader extends ActionReader {

	private String workflowName;
	
	public ProcessActionReader(Action act, String wfn) {
		super(act, wfn);
		workflowName = wfn;
	}

	public WorkflowReader getActionWorkflow() {
		return (new WorkflowReader(WorkFlowModel.findWorkflow(workflowName)));
	}

}
