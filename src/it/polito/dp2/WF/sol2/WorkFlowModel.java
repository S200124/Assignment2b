package it.polito.dp2.WF.sol2;

import java.io.File;
import java.util.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

public final class WorkFlowModel {
	
	private WorkFlowModel() {}
	
	public static WfInfo getRootNode()
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
		try {
			return getRootNode().getWorkflow();
		}
		catch(Exception ex) {
			return null;
		}
	}
	
	public static List<Process> allProcesses()
	{	
		try {
			return getRootNode().getProcess();
		}
		catch(Exception ex) {
			return null;
		}
	}
	
	public static List<Process> whereProcesses(String workflowName)
	{
		List<Process> ret = new ArrayList<Process>();
		List<Process> lap = allProcesses();
		
		if(lap != null && !lap.isEmpty())
			for(Process pr:lap)
				if(pr.getWorkflowName().trim().equals(workflowName))
					ret.add(pr);
		
		return ret;
	}
	
	public static Workflow findWorkflow(String name)
	{	
		List<Workflow> law = allWorkflow();
		
		if(law != null && !law.isEmpty())
			for(Workflow wf:law)
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
