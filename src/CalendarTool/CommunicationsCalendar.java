// tabs=4
//************************************************************
// Adapted to work from a mysql database rather than from
// a data file
// July 28, 2010 (TMR and SM)
//	COPYRIGHT 2010 T. M. Rao and Milen Nikolov, The
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
import java.text.*;

public class CommunicationsCalendar 
{
	private TreeMap<Integer, Vector<Month>> calendar;
	private int yearsAdvance;
	
	public CommunicationsCalendar(int yearsInAdvance)
	{
		this.yearsAdvance = yearsInAdvance;
		
		this.calendar = initializeMonths(yearsInAdvance);
	}
	
	public CommunicationsCalendar()
	{
		this.yearsAdvance = -1;
		this.calendar = new TreeMap<Integer, Vector<Month>>(); 	
	}
	
	@SuppressWarnings("deprecation")
	public TreeMap<Integer, Vector<Month>> initializeMonths(int yearsInAdvance)
	{
		TreeMap<Integer, Vector<Month>> calendar = new TreeMap<Integer, Vector<Month>>();
		
		Calendar c = Calendar.getInstance();
		
		Date today = c.getTime();
		
		// work with a java calendar instead
		int year = today.getYear();
				
		for (int i = 0; i < yearsInAdvance; i++)
		{
			boolean isLeapYear;
			
			if((year+1900)%4 == 0)
				isLeapYear = true;
			else
				isLeapYear = false;
			
			Vector<Month> months = new Vector<Month>();
		
			for(int j = 1; j <= 12; j++)
			{
				Month m = new Month(j, isLeapYear);
				
				//System.out.println(new Date(year, j-1, 1));
				m.setBusinessDays(new Date(year, j-1, 1));
				
				months.add(m);
			}
			
			calendar.put(year+1900, months);

			year += 1;
		}
		
		return calendar;
	}
	
	public TreeMap<Integer, Vector<Month>> getCommunicationsCalendar()
	{
		return this.calendar;
	}
	
	public void setCommunicationsCalendar(TreeMap<Integer, Vector<Month>> calendar)
	{
		this.calendar = calendar;
	}
	
	public int getYearsInAdvance()
	{
		return this.yearsAdvance;
	}
	
	public void toggleBusinessDay(String month, String year, String date)
	{
		int m = Integer.parseInt(month);
		
		int y = Integer.parseInt(year);
		
		int d = Integer.parseInt(date);
		
		if(calendar.get(y).get(m).getDays().get(d-1).isBusinessDay())
			calendar.get(y).get(m).getDays().get(d-1).setIsBusinessDay(false);
		else
			calendar.get(y).get(m).getDays().get(d-1).setIsBusinessDay(true);
	}
	
	@SuppressWarnings("deprecation")
	public boolean isBusinessDay(int month, int date, int year)
	{	
		//System.out.println(year+" "+(month-1)+" " + date);
		return calendar.get(year).get(month).getDays().get(date).isBusinessDay();
	}
		
}
