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

public class Day 
{
	private boolean isBusinessDay;
	
	public Day(boolean isBusinessDay)
	{
		this.isBusinessDay = isBusinessDay;
	}
	
	public boolean isBusinessDay()
	{
		return isBusinessDay;
	}
	
	public void setIsBusinessDay(boolean isBusinessDay)
	{
		this.isBusinessDay = isBusinessDay;
	}

	public String toString()
	{
		return isBusinessDay + " ";
	}
}
