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
		List<Action> lact = WorkFlowModel.allActions(workflow);
		
		if(lact != null)
			for(Action act:lact)
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
		List<Process> lpr = WorkFlowModel.whereProcesses(workflow.getName());
		
		if(!lpr.isEmpty())
			for(Process pr:lpr)
				ret.add(new ProcessReader(pr));
		
		return ret;
	}

}
