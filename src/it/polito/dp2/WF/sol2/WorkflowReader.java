package it.polito.dp2.WF.sol2;

import java.util.*;

public class WorkflowReader implements it.polito.dp2.WF.WorkflowReader {

	private Workflow workflow;
	
	public WorkflowReader(Workflow wf)
	{
		workflow = wf;
	}
	@Override
	public ActionReader getAction(String arg0) {
		return (new ActionReader(WorkFlowModel.findAction(workflow, arg0), getName()));
	}

	@Override
	public Set<it.polito.dp2.WF.ActionReader> getActions() {
		Set<it.polito.dp2.WF.ActionReader> ret = new HashSet<it.polito.dp2.WF.ActionReader>();
		
		for(Action act:WorkFlowModel.allActions(workflow))
			ret.add(new ActionReader(act, getName()));
		
		return ret;
	}

	@Override
	public String getName() {
		return workflow.getName();
	}

	@Override
	public Set<it.polito.dp2.WF.ProcessReader> getProcesses() {
		Set<it.polito.dp2.WF.ProcessReader> ret = new HashSet<it.polito.dp2.WF.ProcessReader>();
		
		for(Process pr:WorkFlowModel.whereProcesses(workflow.getName()))
			ret.add(new ProcessReader(pr, workflow.getName()));
		
		return ret;
	}

}
