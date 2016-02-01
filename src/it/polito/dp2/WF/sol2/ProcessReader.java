package it.polito.dp2.WF.sol2;

import java.text.*;
import java.util.*;

public class ProcessReader implements it.polito.dp2.WF.ProcessReader {
	
	private Process process;
	private String workflowName;
	
	public ProcessReader(Process pr, String wfn)
	{
		process = pr;
		workflowName = wfn;
	}
	
	@Override
	public Calendar getStartTime() {
		try
		{
			//"yyyy.MM.dd G 'at' HH:mm:ss z"	2001.07.04 AD at 12:08:56 PDT
			DateFormat df = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
			Calendar cal  = Calendar.getInstance();
			cal.setTime(df.parse(process.getStartAt()));
			return cal;
		}
		catch (ParseException e)
		{
			return null;
		}	
	}

	@Override
	public List<it.polito.dp2.WF.ActionStatusReader> getStatus() {
		List<it.polito.dp2.WF.ActionStatusReader> ret = new ArrayList<it.polito.dp2.WF.ActionStatusReader>();
		
		for(ActionStatus as:WorkFlowModel.allActionStatus(process))
			ret.add(new ActionStatusReader(as));
		
		return ret;
	}

	@Override
	public WorkflowReader getWorkflow() {
		return (new WorkflowReader(WorkFlowModel.findWorkflow(workflowName)));
	}

}
