package it.polito.dp2.WF.sol2;

import it.polito.dp2.WF.Actor;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ActionStatusReader implements it.polito.dp2.WF.ActionStatusReader {

	private ActionStatus actionStat;
	
	public ActionStatusReader(ActionStatus as)
	{
		actionStat = as;
	}
	
	@Override
	public String getActionName() {
		return actionStat.getActionName();
	}

	@Override
	public Actor getActor() {
		if(actionStat.getActor() != null)
		{
			String name = actionStat.getActor().getName();
			String role = actionStat.getActor().getRole();
	
			if((name != null && !name.isEmpty()) && (role != null && !role.isEmpty()))
			{
				Actor act = new Actor(name, role);
				return act;
			}
		}
		
		return null;
	}

	@Override
	public Calendar getTerminationTime() {
		try
		{   //"yyyy.MM.dd G 'at' HH:mm:ss z"	2001.07.04 AD at 12:08:56 PDT
			DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
			Calendar cal  = Calendar.getInstance();
			cal.setTime(df.parse(actionStat.getTerminatedAt()));
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
		if(actionStat.getTerminatedAt() != null && !actionStat.getTerminatedAt().isEmpty())
			return true;
		else
			return false;
	}

}
