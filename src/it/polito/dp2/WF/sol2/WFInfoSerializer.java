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
		//System.setProperty("it.polito.dp2.WF.WorkflowMonitorFactory", "it.polito.dp2.WF.Random.WorkflowMonitorFactoryImpl");
		
		try {
			WorkflowMonitor testWorkflowMonitor = WorkflowMonitorFactory.newInstance().newWorkflowMonitor();

			for(ProcessReader ts:testWorkflowMonitor.getProcesses())
			{
				System.out.println(ts.getStartTime());
				for(ActionStatusReader as:ts.getStatus())
				{
					System.out.println(as.getActionName() + " " + as.getTerminationTime());
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
			WfInfo rootElement = appendWorkflows();
			
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


	private List<Process> appendProcesses() {
		List<Process> ret = new ArrayList<Process>();
		// Get the list of processes
		Set<ProcessReader> set = monitor.getProcesses();
		
		// For each process print related data
		for (ProcessReader wfr: set)
		{
			Process pr = new Process();
			pr.setStartAt(String.valueOf(wfr.getStartTime().getTimeInMillis()));
			pr.setWorkflowName(wfr.getWorkflow().getName());

			List<ActionStatus> ass = new ArrayList<ActionStatus>();
			List<ActionStatusReader> statusSet = wfr.getStatus();
			
			for (ActionStatusReader asr : statusSet)
			{
				ActionStatus as = new ActionStatus();
				as.setActionName(asr.getActionName());
				
				if (asr.isTakenInCharge())
				{
					Actor act = new Actor();
					act.setName(asr.getActor().getName());
					act.setRole(asr.getActor().getRole());
					as.setActor(act);
				}

				if (asr.isTerminated())
					as.setTerminatedAt(String.valueOf(asr.getTerminationTime().getTimeInMillis()));
				
				ass.add(as);
			}
			pr.setActionStatus(ass);
			ret.add(pr);
		}
		return ret;
	}


	private WfInfo appendWorkflows() {
		WfInfo ret = new WfInfo();
		List<Workflow> wfs = new ArrayList<Workflow>();
		// Get the list of workflows
		Set<WorkflowReader> set = monitor.getWorkflows();
		
		/* Print the header of the table */
		
		// For each workflow print related data
		for (WorkflowReader wfr: set)
		{
			Workflow wf = new Workflow();

			wf.setName(wfr.getName());

			List<Action> acts = new ArrayList<Action>();
			// Print actions
			Set<ActionReader> setAct = wfr.getActions();
			
			for (ActionReader ar: setAct)
			{
				Action act = new Action();
				act.setAutomInst(String.valueOf(ar.isAutomaticallyInstantiated()));
				act.setName(ar.getName());
				act.setRole(ar.getRole());
				
				if (ar instanceof SimpleActionReader) {
					act.setType("simple");

					// Print next actions
					Set<ActionReader> setNxt = ((SimpleActionReader)ar).getPossibleNextActions();
					if(!setNxt.isEmpty())
					{
						FollowingActions fa = new FollowingActions();
						List<String> fas = new ArrayList<String>();
						for (ActionReader nAct: setNxt)
							fas.add(nAct.getName());
						fa.setActionName(fas);
						act.setFollowingActions(fa);
					}
				}
				else if (ar instanceof ProcessActionReader) {
					act.setType("process");
					act.setNestedWorkflow(((ProcessActionReader)ar).getActionWorkflow().getName());
					// print workflow
				}
				acts.add(act);
			}
			wf.setAction(acts);
			wfs.add(wf);
		}
		ret.setWorkflow(wfs);
		ret.setProcess(appendProcesses());
		return ret;
	}
	
}

