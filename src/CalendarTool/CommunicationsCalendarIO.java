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

// Edited to suit the i/o from database
package CalendarTool;

import java.io.*;
import java.util.*;
import java.util.Map.Entry;
import java.sql.*;


import database.*;


public class CommunicationsCalendarIO extends Persistable
{	
    private String tableName;
    protected static Properties mySchema;
    
    public CommunicationsCalendarIO()
    {
    	tableName = "WNWDATA";
    	initializeSchema(tableName);
    }
     //--------------------------------------------------------------
	protected void initializeSchema(String tableName)
	{
		if (mySchema == null)
		{
			mySchema = getSchemaInfo(tableName); 
			// method inherited from Persistable
		}
	}
	protected void updateRow(Properties primaryKeysValues, Properties valueToWrite)
	{
		try
		{
			updatePersistentState(mySchema, valueToWrite, primaryKeysValues);
		}catch(SQLException sqle)
		{
			System.out.println("Error in CommunicationsCalendarIP.updateRow"+valueToWrite);
		}
	}
	public boolean writeCalendar(CommunicationsCalendar cc)
	{
		
		//Legacy code from Milen Nikolov
		//FileWriter fstream = new FileWriter(GlobalConfiguration.communicationsCalendarFile);
		//BufferedWriter out = new BufferedWriter(fstream);
		
		TreeMap<Integer, Vector<Month>> calendar = cc.getCommunicationsCalendar();			
			
		// out.write(""+calendar.size()); This is not needed, because the size is 
		// set in the database to a constant 19.
		// Write the number of years into the database
			
		Iterator<Entry<Integer, Vector<Month>>> calendarIterator = calendar.entrySet().iterator();
			
		while(calendarIterator.hasNext())
		{
			Entry<Integer, Vector<Month>> e = calendarIterator.next();
				
			int year = e.getKey(); 
				
			// out.write(System.getProperty("line.separator")+year); 
			// We don't need to write the year, because it is already there, all we
			// will do is to update the working-non-working days
			// Write the individual year (like 2009) into the database
			
			//We will create Property objects for update values and primary key-values
			Properties updateValues = new Properties();
			updateValues.setProperty("year", ""+year);
			Properties primaryKeysValues = new Properties();
			primaryKeysValues.setProperty("year", ""+year);
				
			Iterator<Month> calendarMonths = e.getValue().iterator();
				
			while(calendarMonths.hasNext())
			{
				Month m = calendarMonths.next();
				int monthNumber = m.getIdentity();
				
				updateValues.setProperty("month", ""+monthNumber);
				primaryKeysValues.setProperty("month",""+monthNumber);
					
				int daysInMonth = m.getDays().size();
				updateValues.setProperty("numDays", ""+daysInMonth);
					
				//out.write(System.getProperty("line.separator"));
				//We don't have to write the line seperator into the database
					
				//out.write(daysInMonth+" ");
				// Write the number of days in this month of this year into the database
					
				Iterator<Day> days = m.getDays().iterator();
				String wnw = "";
				
				//We create a string of 0's and 1's indicating working and non-working
				//days
				while(days.hasNext())
				{
					Day dayToBeWritten = days.next();
						
					if(dayToBeWritten.isBusinessDay())
						//out.write(0+" ");
						wnw += "0 ";
					else
						//out.write(1+" ");
						wnw += "1 ";
						//Make the whole thing into a string and write it at nce
					}
					
					updateValues.setProperty("wnwString", ""+wnw);
					updateRow(primaryKeysValues, updateValues);
				}				
			}
			
			//out.close();
		
			return true;
	}
	
	
	public CommunicationsCalendar readCalendar()
	{
		CommunicationsCalendar cc = new CommunicationsCalendar();
		TreeMap<Integer, Vector<Month>> calendar = cc.getCommunicationsCalendar();
		
		try
		{
			//Legacy code from Milen Nikolov
			//Scanner s = new Scanner(new File("calendar.dat"));
			
			//int numYears = s.nextInt();
			//Get number of years from database
			String select = "SELECT numYears FROM NUMYEARS";
			Vector yV = getSelectQueryResult(select);
			if (yV == null) System.out.println("ccIO.readCalendar: Null number of years received");
			Properties pV = (Properties)yV.elementAt(0);
			int numYears = Integer.parseInt(pV.getProperty("numYears"));
			
			//Get all years from the database
			String select2 = "SELECT DISTINCT year FROM WNWDATA ORDER BY year";
			Vector allYears = getSelectQueryResult(select2);
		
			// RAO 5/5/11
			// We could do this: Get the whole table WNWDATA from the database once
			String selectALL_WNWDATA = "SELECT * FROM WNWDATA ORDER BY Year, Month";
			Vector allWNW_DATA = getSelectQueryResult(selectALL_WNWDATA);
			// Search through the vector to find the info we need.
			
			for(int i = 0; i < numYears; i++)
			{
				//Get the next year from the database
				Properties yearP = (Properties)allYears.elementAt(i);
				int year = Integer.parseInt(yearP.getProperty("year"));
				
				//int year = s.nextInt();
						
				Vector<Month> months = new Vector<Month>(); 
				boolean isLeapYear;
				
				if(year % 4 == 0)
					isLeapYear = true;
				else
					isLeapYear = false;
				
				for(int j = 1; j <= 12; j++)
				{
					//int numDays = s.nextInt();
					//Get the number of days for this month from database
					/* NOT NEEDED AS PER REVISIONS OF 5/6/11
					String select3 = "SELECT numDays FROM WNWDATA WHERE year="+year+
						" AND month = "+j;
					Vector nV = getSelectQueryResult(select3);
					Properties pNV = (Properties)nV.elementAt(0);
					*/
					
					//RAO 5-6-11
					Properties thisMonth = (Properties) (allWNW_DATA.elementAt(12*i+(j-1)));
					//int numDays = Integer.parseInt(pNV.getProperty("numDays"));
					//RAO 5-6-11
					int numDays = Integer.parseInt(thisMonth.getProperty("numDays"));
					
					Month m = new Month(j, isLeapYear);
					
					Vector<Day> days = m.getDays();
					
					/* NOT NEEDED AS PER REVISIONS OF 5/6/11
					String select4 = "SELECT wnwString FROM WNWDATA WHERE year="+year+
						" AND month = "+j;
					Vector wnwV = getSelectQueryResult(select4);
					Properties pWNW = (Properties)wnwV.elementAt(0);
					String wnwString = pWNW.getProperty("wnwString");
					*/
					//RAO 5-6-11
					String wnwString = thisMonth.getProperty("wnwString");
					
					StringTokenizer stk = new StringTokenizer(wnwString, " ");
					for(int k = 0; k < numDays; k++)
					{
						String thisToken = stk.nextToken();
						int wnwSetting = Integer.parseInt(thisToken);
						
						if(wnwSetting == 0)
						//Read a string of 0/1's to indicate working/non-working status of this day
							days.add(new Day(true));
						else
							days.add(new Day(false));
					}
					
					m.setDays(days);
					months.add(m);
				}
				calendar.put(year, months);
			}
		}
		catch(Exception sqle)
		{
			System.out.println("ccIO: readCalendar: Caught exception: " + sqle.toString());
			sqle.printStackTrace();
		}
		
		return cc;
	}
}
