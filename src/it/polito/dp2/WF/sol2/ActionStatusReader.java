package it.polito.dp2.WF.sol2;

import it.polito.dp2.WF.Actor;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import org.w3c.dom.Node;

public class ActionStatusReader implements it.polito.dp2.WF.ActionStatusReader {

	private Node actionStat;
	
	public ActionStatusReader(Node as)
	{
		actionStat = as;
	}
	
	@Override
	public String getActionName() {
		String actionName = WorkFlowModel.getNodeValue(WorkFlowModel.getActionName(actionStat));
		if(actionName != null)
			return actionName;
		else
			return "";
	}

	@Override
	public Actor getActor() {
		HashMap<String,String> attr = WorkFlowModel.getAttibutes(WorkFlowModel.getActor(actionStat));
		String role = WorkFlowModel.getNodeValue(WorkFlowModel.getRole(WorkFlowModel.getActor(actionStat)));

		if(role != null && attr.containsKey("name"))
		{
			Actor act = new Actor(attr.get("name"),role);
			return act;
		}
		else
			return null;
	}

	@Override
	public Calendar getTerminationTime() {
		HashMap<String,String> attr = WorkFlowModel.getAttibutes(actionStat);
		try
		{   //"yyyy.MM.dd G 'at' HH:mm:ss z"	2001.07.04 AD at 12:08:56 PDT
			DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
			Calendar cal  = Calendar.getInstance();
			cal.setTime(df.parse(attr.get("terminatedAt")));
			return cal;
		}
		catch (ParseException e)
		{
			return null;
		}
	}

	@Override
	public boolean isTakenInCharge() {
		if(getActor() != null)
			return true;
		else
			return false;
	}

	@Override
	public boolean isTerminated() {
		HashMap<String,String> attr = WorkFlowModel.getAttibutes(actionStat);
		if(attr.containsKey("terminatedAt"))
			return true;
		else
			return false;
	}

}
