package it.polito.dp2.WF.sol2;

import it.polito.dp2.WF.ActionReader;
import it.polito.dp2.WF.ActionStatusReader;
import it.polito.dp2.WF.ProcessActionReader;
import it.polito.dp2.WF.ProcessReader;
import it.polito.dp2.WF.SimpleActionReader;
import it.polito.dp2.WF.WorkflowMonitor;
import it.polito.dp2.WF.WorkflowMonitorException;
import it.polito.dp2.WF.WorkflowMonitorFactory;
import it.polito.dp2.WF.WorkflowReader;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import java.io.File;

import javax.xml.bind.*;


public class WFInfoSerializer {
	private WorkflowMonitor monitor;
	private DateFormat dateFormat;

	
	/**
	 * Default constructor
	 * @throws WorkflowMonitorException 
	 */
	public WFInfoSerializer() throws WorkflowMonitorException {
		System.setProperty("it.polito.dp2.WF.WorkflowMonitorFactory", "it.polito.dp2.WF.Random.WorkflowMonitorFactoryImpl");
		WorkflowMonitorFactory factory = WorkflowMonitorFactory.newInstance();
		monitor = factory.newWorkflowMonitor();
		dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm");
	}
	
	public WFInfoSerializer(WorkflowMonitor monitor) {
		super();
		this.monitor = monitor;
		dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm");
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		WFInfoSerializer wf;
		try {
			String source = args[0];
			//String source = "xsd/file.xml";
			wf = new WFInfoSerializer();
			wf.createXmlFile(source);
			//wf.checkLibrary(source);
		} catch (WorkflowMonitorException e) {
			System.err.println("Could not instantiate data generator.");
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	/*private void checkLibrary(String fileName)
	{
		System.setProperty("it.polito.dp2.WF.WorkflowMonitorFactory", "it.polito.dp2.WF.sol2.WorkflowMonitorFactory");
		System.setProperty("it.polito.dp2.WF.sol2.WFInfo.file", fileName);
		
		try {
			WorkflowMonitor testWorkflowMonitor = WorkflowMonitorFactory.newInstance().newWorkflowMonitor();

			for(ProcessReader ts:testWorkflowMonitor.getProcesses())
			{
				System.out.println(ts.getStartTime().getWeekYear());
				for(ActionStatusReader as:ts.getStatus())
				{
					System.out.println(as.getActionName() + " " + as.getTerminationTime().getWeekYear());
					System.out.println(as.getActor().getName() + " " + as.getActor().getRole());
				}
			}
			
		} catch (WorkflowMonitorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/
	
	public void createXmlFile(String fileName)
	{
		try
		{
			WfInfo rootElement = appendData();
			
			File file = new File(fileName);
			JAXBContext jaxbContext = JAXBContext.newInstance(WfInfo.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

			// output pretty printed
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			jaxbMarshaller.marshal(rootElement, file);
		 } catch (JAXBException e) {
				e.printStackTrace();
			      }
	}


	private List<Process> appendProcesses(String workflowName) {
		List<Process> ret = new ArrayList<Process>();
		try
		{
			// Get the list of processes
			Set<ProcessReader> set = monitor.getProcesses();
			
			// For each process print related data
			for (ProcessReader wfr: set)
			{
				if(wfr.getWorkflow().getName() == workflowName)
				{
					/*Element process = doc.createElement("process");
					rootElement.appendChild(process);*/
					Process prc = new Process();
					List<ActionStatus> ass = new ArrayList<ActionStatus>();
					
					prc.setStartAt(String.valueOf(wfr.getStartTime().getTime()));
					//process.setAttribute("startAt", String.valueOf(wfr.getStartTime().getTime()));
					/*System.out.println("Process started at " + 
										dateFormat.format(wfr.getStartTime().getTime()) +
							            " "+"- Workflow " + wfr.getWorkflow().getName());
					System.out.println("Status:");*/
					List<ActionStatusReader> statusSet = wfr.getStatus();
	
					for (ActionStatusReader asr : statusSet)
					{
						/*Element actionStatus = doc.createElement("actionStatus");
						process.appendChild(actionStatus);*/
						ActionStatus as = new ActionStatus();
						
						as.setActionName(asr.getActionName());
						
						//System.out.print(asr.getActionName()+"\t");
						if (asr.isTakenInCharge())
						{
							/*Element actor = doc.createElement("actor");
							actionStatus.appendChild(actor);*/
							Actor act = new Actor();
							
							act.setName(asr.getActor().getName());
							act.setRole(asr.getActor().getRole());
							
							as.setActor(act);
							//actor.setAttribute("name", asr.getActor().getName());
							//System.out.print(asr.getActor().getName()+"\t\t");
							/*Element role = doc.createElement("role");
							role.appendChild(doc.createTextNode(asr.getActor().getRole()));
							actor.appendChild(role);*/
						}
						
						/*Element actionName = doc.createElement("actionName");
						actionName.appendChild(doc.createTextNode(asr.getActionName()));
						actionStatus.appendChild(actionName);*/
		
						if (asr.isTerminated())
							as.setTerminatedAt(String.valueOf(dateFormat.format(asr.getTerminationTime().getTime())));
							//actionStatus.setAttribute("terminatedAt", String.valueOf(dateFormat.format(asr.getTerminationTime().getTime())));
							//System.out.println(dateFormat.format(asr.getTerminationTime().getTime()));
						
						ass.add(as);
					}
					prc.setActionStatus(ass);
					ret.add(prc);
				}
			}
			return ret;
		}
		catch(Exception ex)
		{
			return null;
		}
	}


	private WfInfo appendData() {
		try
		{
			WfInfo ret = new WfInfo();
			List<Workflow> wfs = new ArrayList<Workflow>();
			Set<WorkflowReader> set = monitor.getWorkflows();
			
			//ret.setXmlns("http://www.w3schools.com");
			
			for (WorkflowReader wfr: set)
			{
				/*Element workflow = doc.createElement("workflow");
				rootElement.appendChild(workflow);*/
				Workflow wf = new Workflow();
				
				List<Action> acts = new ArrayList<Action>();
				
				//workflow.setAttribute("name", wfr.getName());
				wf.setName(wfr.getName());
				wf.setProcess(appendProcesses(wfr.getName()));
				
				// Print actions
				//System.out.println("Actions:");
				Set<ActionReader> setAct = wfr.getActions();
				//printHeader("Action Name\tRole\t\tAutom.Inst.\tSimple/Process\tWorkflow\tNext Possible Actions");
				for (ActionReader ar: setAct)
				{
					/*Element action = doc.createElement("action");
					workflow.appendChild(action);*/
					Action act = new Action();
					
					/*Element role = doc.createElement("role");
					role.appendChild(doc.createTextNode(ar.getRole()));
					action.appendChild(role);*/
					act.setRole(ar.getRole());
					
					//action.setAttribute("name", ar.getName());
					act.setName(ar.getName());
					//action.setAttribute("automInst", String.valueOf(ar.isAutomaticallyInstantiated()));
					act.setAutomInst(String.valueOf(ar.isAutomaticallyInstantiated()));
					
					if (ar instanceof SimpleActionReader)
					{
						//action.setAttribute("type", "simple");
						act.setType("simple");
						
						Set<ActionReader> setNxt = ((SimpleActionReader)ar).getPossibleNextActions();
						if(!setNxt.isEmpty())
						{
							/*Element followingActions = doc.createElement("followingActions");
							action.appendChild(followingActions);*/
							FollowingActions fa = new FollowingActions();
							List<String> fan = new ArrayList<String>();
							
							for (ActionReader nAct: setNxt)
								fan.add(nAct.getName());
								/*Element actionName = doc.createElement("actionName");
								actionName.appendChild(doc.createTextNode(nAct.getName()));
								followingActions.appendChild(actionName);*/
							fa.setActionName(fan);
							act.setFollowingActions(fa);
						}
					}
					else if (ar instanceof ProcessActionReader)
					{
						//action.setAttribute("type", "process");
						act.setType("process");
						act.setNestedWorkflow(((ProcessActionReader)ar).getActionWorkflow().getName());
						
						/*Element nestedWorkflow = doc.createElement("nestedWorkflow");
						nestedWorkflow.setAttribute("workflowName", ((ProcessActionReader)ar).getActionWorkflow().getName());
						action.appendChild(nestedWorkflow);*/
					}
					acts.add(act);
				}
				wf.setAction(acts);
				wfs.add(wf);
			}
			ret.setWorkflow(wfs);
			return ret;
		}
		catch(Exception ex)
		{
			return null;
		}
	}
	
}

