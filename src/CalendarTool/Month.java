// tabs=4
//************************************************************
//	COPYRIGHT 2009 T. M. Rao and Milen Nikolov, The
//    College at Brockport, State University of New York. - 
//	  ALL RIGHTS RESERVED
//
// This file is the product of The College at Brockport and cannot 
// be reproduced, copied, or used in any shape or form without 
// the express written consent of The College at Brockport.
//************************************************************
//
package CalendarTool;

import java.util.*;

public class Month 
{
	private int identity;
	private Vector<Day> days;
	
	public Month(int identity, boolean isLeapYear)
	{
		this.identity = identity;
		
		switch(this.identity)
		{
			case 1: days = new Vector<Day>(31); break;
			case 2: 
					if(isLeapYear)
						days = new Vector<Day>(29); 
					else
						days = new Vector<Day>(28);
					break;
			case 3: days = new Vector<Day>(31); break;
			case 4: days = new Vector<Day>(30); break;
			case 5: days = new Vector<Day>(31); break;
			case 6: days = new Vector<Day>(30); break;
			case 7: days = new Vector<Day>(31); break;
			case 8: days = new Vector<Day>(31); break;
			case 9: days = new Vector<Day>(30); break;
			case 10: days = new Vector<Day>(31); break;
			case 11: days = new Vector<Day>(30); break;
			case 12: days = new Vector<Day>(31); break;
		}
	}
	
	@SuppressWarnings("deprecation")
	public void setBusinessDays(Date startDate)
	{
		int day = startDate.getDate();
		for(int i = 0; i < days.capacity(); i++)
		{
			if((startDate.getDay())%6 == 0)
				days.addElement(new Day(false));
			else
				days.addElement(new Day(true));
			day++;
			
			// use local variable
			startDate.setDate(day);
		}
		
	}
	public int getIdentity()
	{
		return identity;
	}
	public Vector<Day> getDays()
	{
		return this.days;
	}
	
	public void setDays(Vector<Day> days)
	{
		this.days = days;
	}
}
