package it.polito.dp2.WF.sol2;

import java.util.*;

import org.w3c.dom.Node;

public class SimpleActionReader extends ActionReader {

	private Node action;
	private String workflowName;
	
	public SimpleActionReader(Node act, String wfn) {
		super(act, wfn);
		action = act;
		workflowName = wfn;
	}

	public Set<ActionReader> getPossibleNextActions() {
		Set<ActionReader> ret = new HashSet<ActionReader>();
		
		for(Node actionName:WorkFlowModel.followingActions(action))
			for(Node actionNode:WorkFlowModel.allActions(WorkFlowModel.findWorkflow(workflowName)))
			{
				HashMap<String,String> attr = WorkFlowModel.getAttibutes(actionNode);
				if(WorkFlowModel.getNodeValue(actionName) == attr.get("name"))
					ret.add(new ActionReader(actionNode, workflowName));
			}
		
		return ret;
	}

}
