package it.polito.dp2.WF.sol2;

import java.io.File;
import java.util.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

public final class WorkFlowModel {
	
	private WorkFlowModel() {}
	
	private static WfInfo getRootNode()
	{
		try
		{
			File file = new File(System.getProperty("it.polito.dp2.WF.sol2.WorkflowInfo.file"));
			JAXBContext jaxbContext = JAXBContext.newInstance(WfInfo.class);

			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			WfInfo root = (WfInfo) jaxbUnmarshaller.unmarshal(file);
			
			return root;

		  } catch (JAXBException e) {
			e.printStackTrace();
		  }
		return null;
	}
	
	public static List<Workflow> allWorkflow()
	{
		return getRootNode().getWorkflow();
	}
	
	public static List<Process> allProcesses()
	{
		List<Process> ret = new ArrayList<Process>();
		
		for(Workflow wf:allWorkflow())
			ret.addAll(wf.getProcess());
		
		return ret;
	}
	
	public static List<Process> whereProcesses(String workflowName)
	{
		return findWorkflow(workflowName).getProcess();
	}
	
	public static Workflow findWorkflow(String name)
	{	
		for(Workflow wf:allWorkflow())
			if(wf.getName().trim().equals(name))
				return wf;
		
		return null;
	}
	
	public static List<ActionStatus> allActionStatus(Process pr)
	{
		return pr.getActionStatus();
	}
	
	public static List<Action> allActions(Workflow wf)
	{
		return wf.getAction();
	}

	public static Action findAction(Workflow wf, String name)
	{	
		if(wf != null)
			for(Action act:wf.getAction())
				if(act.getName().trim().equals(name))
					return act;
		
		return null;
	}
	
	public static List<String> followingActions(Action act)
	{
		if(act != null)
			return act.getFollowingActions().getActionName();
		
		return null;
	}
}
