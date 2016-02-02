package it.polito.dp2.WF.sol2;

import it.polito.dp2.WF.Actor;

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
		//DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		Calendar cal  = Calendar.getInstance();
		cal.setTimeInMillis(Long.parseLong(actionStat.getTerminatedAt()));
		return cal;
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
