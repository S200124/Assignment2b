package it.polito.dp2.WF.sol2;

import java.util.*;
import org.w3c.dom.*;


public class WorkflowReader implements it.polito.dp2.WF.WorkflowReader {

	private Node workflow;
	
	public WorkflowReader(Node wf)
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
		
		for(Node currentNode:WorkFlowModel.allActions(workflow))
			ret.add(new ActionReader(currentNode, getName()));
		
		return ret;
	}

	@Override
	public String getName() {
		HashMap<String,String> attr = WorkFlowModel.getAttibutes(workflow);
		return attr.get("name");
	}

	@Override
	public Set<it.polito.dp2.WF.ProcessReader> getProcesses() {
		HashMap<String,String> attr = WorkFlowModel.getAttibutes(workflow);
		String name = attr.get("name");
		Set<it.polito.dp2.WF.ProcessReader> ret = new HashSet<it.polito.dp2.WF.ProcessReader>();
		
		for(Node currentNode:WorkFlowModel.whereProcesses(name))
			ret.add(new ProcessReader(currentNode, name));
		
		return ret;
	}

}
