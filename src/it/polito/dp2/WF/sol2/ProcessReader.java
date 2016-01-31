package it.polito.dp2.WF.sol2;

import java.text.*;
import java.util.*;
import org.w3c.dom.*;

public class ProcessReader implements it.polito.dp2.WF.ProcessReader {
	
	private Node process;
	private String workflowName;
	
	public ProcessReader(Node pr, String wfn)
	{
		process = pr;
		workflowName = wfn;
	}
	
	@Override
	public Calendar getStartTime() {
		HashMap<String,String> attr = WorkFlowModel.getAttibutes(process);
		try
		{
			//"yyyy.MM.dd G 'at' HH:mm:ss z"	2001.07.04 AD at 12:08:56 PDT
			DateFormat df = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
			Calendar cal  = Calendar.getInstance();
			cal.setTime(df.parse(attr.get("startAt")));
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
		
		for(Node currentNode:WorkFlowModel.allActionStatus(process))
			ret.add(new ActionStatusReader(currentNode));
		
		return ret;
	}

	@Override
	public WorkflowReader getWorkflow() {
		return (new WorkflowReader(WorkFlowModel.findWorkflow(workflowName)));
	}

}
