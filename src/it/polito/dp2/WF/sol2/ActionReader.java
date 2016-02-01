package it.polito.dp2.WF.sol2;

public class ActionReader implements it.polito.dp2.WF.ActionReader {
	
	private Action action;
	private String workflowName;
	
	public ActionReader(Action act, String wfn)
	{
		action = act;
		workflowName = wfn;
	}

	@Override
	public WorkflowReader getEnclosingWorkflow() {
		return (new WorkflowReader(WorkFlowModel.findWorkflow(workflowName)));
	}

	@Override
	public String getName() {
		return action.getName();
	}

	@Override
	public String getRole() {
		return action.getRole();
	}

	@Override
	public boolean isAutomaticallyInstantiated() {
		return Boolean.parseBoolean(action.getAutomInst());
	}

}
