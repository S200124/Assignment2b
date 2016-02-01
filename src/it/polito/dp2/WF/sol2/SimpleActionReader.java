package it.polito.dp2.WF.sol2;

import java.util.*;

public class SimpleActionReader extends ActionReader {

	private Action action;
	private String workflowName;
	
	public SimpleActionReader(Action act, String wfn) {
		super(act, wfn);
		action = act;
		workflowName = wfn;
	}

	public Set<ActionReader> getPossibleNextActions() {
		Set<ActionReader> ret = new HashSet<ActionReader>();
		
		for(String actionName:WorkFlowModel.followingActions(action))
			for(Action act:WorkFlowModel.allActions(WorkFlowModel.findWorkflow(workflowName)))
				if(actionName == act.getName())
					ret.add(new ActionReader(act, workflowName));
		
		return ret;
	}

}
