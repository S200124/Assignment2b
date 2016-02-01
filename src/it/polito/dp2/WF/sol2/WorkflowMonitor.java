package it.polito.dp2.WF.sol2;

import java.util.*;


public class WorkflowMonitor implements it.polito.dp2.WF.WorkflowMonitor {

	@Override
	public Set<it.polito.dp2.WF.ProcessReader> getProcesses() {
		Set<it.polito.dp2.WF.ProcessReader> ret = new HashSet<it.polito.dp2.WF.ProcessReader>();
		
		for(Workflow wf:WorkFlowModel.allWorkflow())
			for(Process pr:WorkFlowModel.whereProcesses(wf.getName()))
				ret.add(new ProcessReader(pr, wf.getName()));
		
		return ret;
	}

	@Override
	public WorkflowReader getWorkflow(String arg0) {
		return (new WorkflowReader(WorkFlowModel.findWorkflow(arg0)));
	}

	@Override
	public Set<it.polito.dp2.WF.WorkflowReader> getWorkflows() {
		Set<it.polito.dp2.WF.WorkflowReader> ret = new HashSet<it.polito.dp2.WF.WorkflowReader>();
		
		for(Workflow wf:WorkFlowModel.allWorkflow())
			ret.add(new WorkflowReader(wf));
		
		return ret;
	}
	
}
