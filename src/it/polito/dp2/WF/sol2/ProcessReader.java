package it.polito.dp2.WF.sol2;

import java.util.*;

public class ProcessReader implements it.polito.dp2.WF.ProcessReader {
	
	private Process process;
	
	public ProcessReader(Process pr)
	{
		process = pr;
	}
	
	@Override
	public Calendar getStartTime() {
		Calendar cal  = Calendar.getInstance();
		cal.setTimeInMillis(Long.parseLong(process.getStartAt()));
		return cal;	
	}

	@Override
	public List<it.polito.dp2.WF.ActionStatusReader> getStatus() {
		List<it.polito.dp2.WF.ActionStatusReader> ret = new ArrayList<it.polito.dp2.WF.ActionStatusReader>();
		List<ActionStatus> las = WorkFlowModel.allActionStatus(process);
		
		if(las != null)
			for(ActionStatus as:las)
				ret.add(new ActionStatusReader(as));
		
		return ret;
	}

	@Override
	public WorkflowReader getWorkflow() {
		return (new WorkflowReader(WorkFlowModel.findWorkflow(process.getWorkflowName())));
	}

}
