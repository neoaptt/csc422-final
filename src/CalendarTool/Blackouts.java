// tabs=4
//************************************************************
//	COPYRIGHT 2014 T. M. Rao and Sandeep Mitra, The
//    College at Brockport, State University of New York. - 
//	  ALL RIGHTS RESERVED
//
// This file is the product of The College at Brockport and cannot 
// be reproduced, copied, or used in any shape or form without 
// the express written consent of The College at Brockport.
//************************************************************
//
package CalendarTool;

import java.text.SimpleDateFormat; 
/**
 * class: Blackouts
 * 
 * methods that need to be tested:
 *  + Date computeDueDate(Date todayDate, String categoryName)
 *  + int calculateFine(Date todayDate, Date dueDate)
 */

import java.util.*;

//project imports
import GlobalData.*;

public class Blackouts 
{
	
	private static int CHECKOUT_PERIOD = GlobalConstants.CHECKOUT_PERIOD; // 1 DAY
	private static int RENEW_PERIOD = GlobalConstants.RENEW_PERIOD; // 1 DAY
	private static int MAX_CAGEWORKER_ALLOWED_RENEW_PERIOD = 
		GlobalConstants.MAX_CAGEWORKER_ALLOWED_RENEW_PERIOD; // 3 DAYS
		
	private static int FINE_GRADE_1 = GlobalConstants.FINE_GRADE_1; // $ 0
	private static int FINE_GRADE_2 = GlobalConstants.FINE_GRADE_2; // $ 5
	
	
	// handles data structures and algorithms for the calendar
	private CommunicationsCalendar cc;
	
	// handles input output from calendar file
	private CommunicationsCalendarIO ccIO;

	
	//---------------------------------------------------------------------------------------------------
	public Blackouts()
	{
		ccIO = new CommunicationsCalendarIO();
		// DEBUG System.out.println("DEBUG: Read comm calendar 1");
		cc = ccIO.readCalendar();
		// DEBUG System.out.println("DEBUG: Read comm calendar 2");
	}

	 /**
	  * Computes the due date of an item (taking into account possible non-business days). Number
	  * of days allowed for rental is given by the constant "CHECKOUT_PERIOD" above.
	  * 
	  * @param todayDate Date of rental
	  * @return Computed due date
	  */
	//--------------------------------------------------------------------------------------------------
	public Date computeDueDate(Date todayDate)
	{ 
	 	TreeMap<Integer, Vector<Month>> calendar = cc.getCommunicationsCalendar();
	 	int checkoutPeriod = CHECKOUT_PERIOD;
	 	Date dueDate;
	 	 
		/**
		 * Get a local Calendar (almost certainly the Gregorian calendar used in the Western world),
		 * and associate it with the 'todayDate' parameter.
		 */
		Calendar localCalendar = Calendar.getInstance();
		localCalendar.setTime(todayDate);
		
		// Initialize due date to today's date as well
		dueDate = localCalendar.getTime();
		
		int numDaysMoved = 1;
		
		while (numDaysMoved <= checkoutPeriod)
		{
		 	localCalendar.add(Calendar.DATE, 1);
		 	dueDate = localCalendar.getTime();
		 	
		 	int m = localCalendar.get(Calendar.MONTH);
			int y = localCalendar.get(Calendar.YEAR);
			int d = localCalendar.get(Calendar.DAY_OF_MONTH);
			
			/* Not needed for closed day calculation
			int h = localCalendar.get(Calendar.HOUR_OF_DAY);
			int min = localCalendar.get(Calendar.MINUTE);
			*/
		 	
			while(!calendar.get(y).get(m).getDays().get(d-1).isBusinessDay())
			{
				localCalendar.add(Calendar.DATE, 1);
				dueDate = localCalendar.getTime();
				
				m = localCalendar.get(Calendar.MONTH);
				y = localCalendar.get(Calendar.YEAR);
				d = localCalendar.get(Calendar.DAY_OF_MONTH);
			}
			
			numDaysMoved++; 		
		}		
 		
		return dueDate;
	 }



	 /**
	  * Computes the renew date of an item (taking into account possible non-business days). Number
	  * of renewal days allowed is given by the constant RENEW_PERIOD above 
	  *  
	  * @param todayDate Original due date - date from which renew date is computed
	  * @param categoryName Can be ignored (left as "" value)
	  * @return Computed renewal date
	  */
	//--------------------------------------------------------------------------------------------------
	public Date computeRenewDate(Date todayDate, String categoryName)
	{ 
		 TreeMap<Integer, Vector<Month>> calendar = cc.getCommunicationsCalendar();
		
		 int renewPeriod = RENEW_PERIOD;
		 Date dueDate;
		 
		  /*
		  * We get a new local Calendar instance (probably Gregorian) and set today's date
		  */
		 Calendar localCalendar = Calendar.getInstance();
		 localCalendar.setTime(todayDate);
		 
		 dueDate = localCalendar.getTime();
		 	 	
		 int numDaysMoved = 1;
		 while (numDaysMoved <= renewPeriod)
		 {
		 	localCalendar.add(Calendar.DATE, 1);
		 	dueDate = localCalendar.getTime();
		 	
		 	int m = localCalendar.get(Calendar.MONTH);
			int y = localCalendar.get(Calendar.YEAR);
			int d = localCalendar.get(Calendar.DAY_OF_MONTH);
		 	
			while(!calendar.get(y).get(m).getDays().get(d-1).isBusinessDay())
			{
				localCalendar.add(Calendar.DATE, 1);
				dueDate = localCalendar.getTime();
				 
				m = localCalendar.get(Calendar.MONTH);
				y = localCalendar.get(Calendar.YEAR);
				d = localCalendar.get(Calendar.DAY_OF_MONTH);
			}
			
			numDaysMoved++; 		
		}		
 		
		return dueDate;
	 }
	 
	 /**
	  * Given today's date, and a due date at which the item was due (earlier than today's date),
	  * compute the value of the fine (which $ 5/day). However, if in between today's date and
	  * the due date, there were non-business days, then these days are not included in the fine's
	  * computation
	  *  
	  * @param todayDate Today's date
	  * @param dueDate	 Original due date
	  * @return Amount of fine computed
	  */
	//------------------------------------------------------------------------------------
	public int calculateFine(Date todayDate, Date dueDate)
	 {	
	 	 SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm"); 
	 	
		 int fine = 0;
		 int daysLate = 0;
		 
		 Calendar localCalendar = Calendar.getInstance();
		 localCalendar.setTime(dueDate);
		 
		 TreeMap<Integer, Vector<Month>> calendar = cc.getCommunicationsCalendar();	 
		 
		 // DEBUG (Mitra) System.out.println("Today date = " + sdf.format(todayDate));
	 	 // DEBUG (Mitra) System.out.println("Original Due date = " + sdf.format(dueDate));
	 	 
	 	 // Roll the due date to the first business day if necessary (should never be executed
	 	 // if we have computed the due date properly)
	 	 int m = localCalendar.get(Calendar.MONTH);
		 int y = localCalendar.get(Calendar.YEAR);
		 int d = localCalendar.get(Calendar.DAY_OF_MONTH);
			 
		 while (!calendar.get(y).get(m).getDays().get(d-1).isBusinessDay())
		 {
			 	
			// DEBUG System.out.println("Roll due date to first business day");
			localCalendar.add(Calendar.DATE, 1);
			dueDate = localCalendar.getTime();
				
			m = localCalendar.get(Calendar.MONTH);
			y = localCalendar.get(Calendar.YEAR);
			d = localCalendar.get(Calendar.DAY_OF_MONTH);
		}
		 
		 /* if items is returned before due date than there is no fine */
		 if(todayDate.compareTo(dueDate) <= 0)
		 {
			 fine = 0;
		 }
		 else
		 {	 
			 long todayDateTimestamp = todayDate.getTime();
			 long dueDateTimestamp = dueDate.getTime();
		 
			 // Calculate the number of closed days between the original value of 
			 // due date (passed in as parameter) and today
			 int numClosedDays = 0;
			 
			 while (todayDate.compareTo(dueDate) > 0)
			 {				 
				localCalendar.add(Calendar.DATE, 1);
				dueDate = localCalendar.getTime();
			 
			 	m = localCalendar.get(Calendar.MONTH);
				y = localCalendar.get(Calendar.YEAR);
				d = localCalendar.get(Calendar.DAY_OF_MONTH);
				
				if((calendar.get(y).get(m).getDays().get(d-1).isBusinessDay()) == false)
				{
					numClosedDays++;
				 }
			 }
			 
			 // If the last value of due date is a closed day, reduce the numClosedDays count by 1
			 // We do this because let us suppose this program was running on a Monday afternoon,
			 // and that Monday was a holiday, then we would count the number of closed days as 3,
			 // including the weekend, when there were only 2 closed days BETWEEN - say - the Friday
			 // that was the original due date and the Monday this method is running
			 m = localCalendar.get(Calendar.MONTH);
			 y = localCalendar.get(Calendar.YEAR);
			 d = localCalendar.get(Calendar.DAY_OF_MONTH);
				
			 if((calendar.get(y).get(m).getDays().get(d-1).isBusinessDay()) == false)
			 {
				numClosedDays--;
			 }
			 
			 // DEBUG System.out.println("Num closed days: " + numClosedDays);
			 
			  // converting milliseconds to hours (an hour has 60000*60 milliseconds)
			 int hoursLate = (int)((todayDateTimestamp - dueDateTimestamp - 
			 		(numClosedDays*60000*60*24))/(60000*60));
			 // DEBUG System.out.println("Hours late = " + hoursLate);
		
			// Fast Trax wants to charge a full day's fine for the first day it is late
			 daysLate = hoursLate/24 + 1;
			 	
			 // DEBUG System.out.println("Days late = " + daysLate);
			 if (daysLate > 0)
			 {
			 	fine = daysLate * FINE_GRADE_2;
			 } 
			 
			 // DEBUG System.out.println("Fine: " + fine);
		 }
		 
		 	 
		 return fine;
	 }
	 
	

}

