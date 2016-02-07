package it.polito.dp2.WF.sol2;

import java.util.*;


public class WorkflowMonitor implements it.polito.dp2.WF.WorkflowMonitor {

	@Override
	public Set<it.polito.dp2.WF.ProcessReader> getProcesses() {
		Set<it.polito.dp2.WF.ProcessReader> ret = new HashSet<it.polito.dp2.WF.ProcessReader>();
		List<Process> lpr = WorkFlowModel.allProcesses();
		
		if(lpr != null)
			for(Process pr:lpr)
				ret.add(new ProcessReader(pr));
		
		return ret;
	}

	@Override
	public WorkflowReader getWorkflow(String arg0) {
		return (new WorkflowReader(WorkFlowModel.findWorkflow(arg0)));
	}

	@Override
	public Set<it.polito.dp2.WF.WorkflowReader> getWorkflows() {
		Set<it.polito.dp2.WF.WorkflowReader> ret = new HashSet<it.polito.dp2.WF.WorkflowReader>();
		List<Workflow> lwf = WorkFlowModel.allWorkflow();
		
		if(lwf != null)
			for(Workflow wf:lwf)
				ret.add(new WorkflowReader(wf));
		
		return ret;
	}
	
}
