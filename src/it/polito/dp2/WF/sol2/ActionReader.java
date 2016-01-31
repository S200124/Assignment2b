package it.polito.dp2.WF.sol2;

import java.util.HashMap;

import org.w3c.dom.*;

public class ActionReader implements it.polito.dp2.WF.ActionReader {
	
	private Node action;
	private String workflowName;
	
	public ActionReader(Node act, String wfn)
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
		HashMap<String,String> attr = WorkFlowModel.getAttibutes(action);
		return attr.get("name");
	}

	@Override
	public String getRole() {
		String role = WorkFlowModel.getNodeValue(WorkFlowModel.getRole(action));
		if(role != null)
			return role;
		else
			return "";
	}

	@Override
	public boolean isAutomaticallyInstantiated() {
		HashMap<String,String> attr = WorkFlowModel.getAttibutes(action);
		return Boolean.parseBoolean(attr.get("automInst"));
	}

}
