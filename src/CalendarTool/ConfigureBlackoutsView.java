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

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;

// Modified (Mitra)
import GlobalData.*;
// To here

public class ConfigureBlackoutsView extends JPanel
{
	private JDialog myDialog;
	
	private JLabel titleLabel;
	private JLabel taskLabel;
	
	private JLabel monthLabel;
	private JLabel yearLabel;
	
	// days of the week 
	private JLabel monLabel;
	private JLabel tueLabel;
	private JLabel wedLabel;
	private JLabel thuLabel;
	private JLabel friLabel;
	private JLabel satLabel;
	private JLabel sunLabel;
	
	// Buttons
	
	private ArrayList<JButton> dates;
	
	private JButton prevYear;
	private JButton nextYear;
	private JButton prevMonth;
	private JButton nextMonth;
	
	
	private JButton backButton;
	private JButton resetButton;
	private JButton confirmButton;
	
	//Panels
	private JPanel titlePanel;
	private JPanel monthYearDayPanel;
	private JPanel monthYearPanel;
	private JPanel daysPanel;
	private JPanel datesPanel;
	private JPanel calendarButtonsPanel;
	private JPanel navigationButtonsPanel;
	private JPanel buttonsPanel;
	
	
	private CommunicationsCalendar cc;
	private CommunicationsCalendarIO ccIO;
	private TreeMap<Integer, Vector<Month>> calendar;
	
	
	private int currentYear;
	private int currentMonth;
	private int currentDate;
	
	
	private String[] monthName = {"January", "February",
            "March", "April", "May", "June", "July",
            "August", "September", "October", "November",
            "December"};
      
    //------------------------------------------------------------------------
    public int getCurrentDate()
    {
    	return currentDate;
    }
	//------------------------------------------------------------------------
    public int getCurrentMonth()
    {
    	return currentMonth;
    }
    //------------------------------------------------------------------------
    public int getCurrentYear()
    {
    	return currentYear;
    }
	//------------------------------------------------------------------------
	public ConfigureBlackoutsView (JDialog myDialog)
	{
		this.myDialog = myDialog;
	
		ccIO = new CommunicationsCalendarIO();
		cc = ccIO.readCalendar();
		calendar = cc.getCommunicationsCalendar();
		
		Date today = new Date();
		
		currentMonth = today.getMonth();
		currentYear = today.getYear()+1900;
		currentDate = today.getDate();
		
		initializeGUIComponents();
		addListeners();
		buildGUI();
		
		//DEBUG System.out.println("CurrentDate = "+ currentDate + 
		//"\nCurrent Month = " + currentMonth +
		//"\nCurrent Year = "+ currentYear);
	}
	
	//--------------------------------------------------------------------------
	public ConfigureBlackoutsView (JDialog myDialog, int currentMonth, 
	   int currentYear, int currentDate, CommunicationsCalendar cc, boolean reset)
	{
		this.myDialog = myDialog;
	
		ccIO = new CommunicationsCalendarIO();
		
		if(reset)
		{
			this.cc = ccIO.readCalendar();
		}
		else
		{
			this.cc = cc;
		}
		
		calendar = this.cc.getCommunicationsCalendar();
	
		this.currentMonth = currentMonth;
		this.currentYear = currentYear;
		this.currentDate = currentDate;
		
		initializeGUIComponents();
		addListeners();
		buildGUI();
		
		
		
	}
	
	//--------------------------------------------------------------------------
	public void initializeGUIComponents()
	{
		
		titleLabel = new JLabel("Brockport Fast Trax");
		Font myFont_1 = new Font("Helvetica", Font.BOLD, 14);
		titleLabel.setFont(myFont_1);
		taskLabel = new JLabel("Calendar Configuration");
		taskLabel.setFont(myFont_1);
		
		monthLabel = new JLabel("<Month>");
		yearLabel = new JLabel("<Year>");
		
		sunLabel = new JLabel("Sun");
		monLabel = new JLabel("Mon");
		tueLabel = new JLabel("Tue");
		wedLabel = new JLabel("Wed");
		thuLabel = new JLabel("Thu");
		friLabel = new JLabel("Fri");
		satLabel = new JLabel("Sat");
		
		dates = new ArrayList<JButton>();
		
		int firstDayOfMonth = new Date(currentYear-1900, currentMonth, 1).getDay();
		//DEBUG System.out.println("firstDayOfMonth (0 = sun, 1 = mon, etc.) = "+firstDayOfMonth);
		for(int i = 0; i < 42; i++)
		{
			if(i >= firstDayOfMonth && 
			  (i-firstDayOfMonth) < calendar.get(currentYear).get(currentMonth).getDays().size())
			{
				
				dates.add(i, new JButton(""+(i+1-firstDayOfMonth)));
				//This line of code was changed by T.M. Rao on 5-7-09
				//i == currentDate-1 was changed to
				//i == firstDayOfMonth+currentDate-1
				if(i == firstDayOfMonth+currentDate-1 && 
				   currentMonth == new Date().getMonth() && 
				   currentYear == (new Date().getYear()+1900))
				{
					//DEBUG System.out.println("RED i = "+ i);
					dates.get(i).setForeground(Color.RED);
					dates.get(i).setBackground(Color.WHITE);
				}
				else
				if(cc.isBusinessDay(currentMonth, i-firstDayOfMonth, currentYear))
				{
					dates.get(i).setForeground(Color.white);
					dates.get(i).setBackground(Color.blue);
				}
				else
				{
					dates.get(i).setForeground(Color.black);
					dates.get(i).setBackground(Color.LIGHT_GRAY);
				}
			}
			else
			{
				dates.add(new JButton(""));
				dates.get(i).setBackground(Color.BLACK);
				dates.get(i).setSize(50, 50);
			}
		}
		
		prevYear = new JButton("Previous Year");
		nextYear = new JButton("Next Year");
		
		prevMonth = new JButton("Previous Month");
		nextMonth = new JButton("Next Month");
		
		backButton = new JButton("Back");
		resetButton = new JButton("Reset");
		confirmButton = new JButton("Confirm");
		
		titlePanel = new JPanel();
		monthYearDayPanel = new JPanel();
		monthYearPanel = new JPanel();
		daysPanel = new JPanel();
		datesPanel = new JPanel();
		navigationButtonsPanel = new JPanel();
		calendarButtonsPanel = new JPanel();
		buttonsPanel = new JPanel();
	}
	
	@SuppressWarnings("static-access")
	//---------------------------------------------------------------------------
	public void buildGUI()
	{    	
		titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
		JPanel titleLabelPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		titleLabelPanel.add(titleLabel);
		titlePanel.add(titleLabelPanel);
		JPanel taskLabelPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		taskLabelPanel.add(taskLabel);
		titlePanel.add(taskLabelPanel);
	
		monthYearDayPanel.setLayout(new BorderLayout(10,10));
	
		monthYearPanel.setLayout(new GridLayout(1,3,10,10));
		
		monthYearPanel.add(new JLabel());
		
		monthLabel.setText("<  "+getMonthName(currentMonth) + " " + currentYear + "  >");
		monthYearPanel.add(monthLabel);
			
		monthYearPanel.add(new JLabel());
			
		monthYearDayPanel.add(monthYearPanel, BorderLayout.NORTH);
		
		daysPanel.setLayout(new GridLayout(1,7));
		daysPanel.add(sunLabel);
		daysPanel.add(monLabel);
		daysPanel.add(tueLabel);
		daysPanel.add(wedLabel);
		daysPanel.add(thuLabel);
		daysPanel.add(friLabel);
		daysPanel.add(satLabel);
		
		monthYearDayPanel.add(daysPanel, BorderLayout.CENTER);
		
		datesPanel.setLayout(new GridLayout(6,7));
		
		for(int i = 0; i < dates.size(); i++)
		{
			
			datesPanel.add(dates.get(i));
		}

		monthYearDayPanel.add(datesPanel, BorderLayout.SOUTH);
    	
    	navigationButtonsPanel.setLayout(new GridLayout(1,3));
    	navigationButtonsPanel.add(backButton);
    	navigationButtonsPanel.add(resetButton);
    	navigationButtonsPanel.add(confirmButton);
    	
    	calendarButtonsPanel.setLayout(new GridLayout(1,4));
    	
    	if(currentYear == calendar.firstKey())
    	{
    		prevYear.setEnabled(false);
    		if (currentMonth == 0)
			{
				prevMonth.setEnabled(false);
			}
    	}
    	
    	if(currentYear == calendar.lastKey())
    	{
    		nextYear.setEnabled(false);
			if (currentMonth == 11)
			{	
				nextMonth.setEnabled(false);
			}
    	}
    			
    	calendarButtonsPanel.add(prevYear);
    	calendarButtonsPanel.add(prevMonth);
    	calendarButtonsPanel.add(nextMonth);
    	calendarButtonsPanel.add(nextYear);
    	
    	buttonsPanel.setLayout(new BorderLayout(10,10));
    	buttonsPanel.add(calendarButtonsPanel, BorderLayout.NORTH);
    	buttonsPanel.add(navigationButtonsPanel, BorderLayout.CENTER);
    	
    	
    	this.setLayout(new BorderLayout(10,10));
    	this.add(titlePanel, BorderLayout.NORTH);
    	this.add(monthYearDayPanel, BorderLayout.CENTER);
    	this.add(buttonsPanel, BorderLayout.SOUTH);
    	
	}
	
	//--------------------------------------------------------------------------
	public void addListeners()
	{		
		
		for(int i = 0; i< dates.size(); i++)
		{   
			//------- BEGINNING OF MODIFICATION BY TMR ON 5-8-09
			//Trying to repair this: TMR 5-8-09.
		 	//I have added the the following lines of code
		 	//The idea is, if it is a weekend day, no listner is attached to it.
		 	//Therefore, if a user clicks it, nothing will happen.
// Modified (Mitra)
		 	int weekendDay1 = GlobalConstants.WEEKEND_DAY_1;
		 	int weekendDay2 = GlobalConstants.WEEKEND_DAY_2;
		 	
			if ((i%7 == weekendDay1) || (i%7 == weekendDay2)) continue;
// To here
			
			//If the button is before the firstday of month or after the last
			//day of month, again we don't attach any listener.
			int firstDayOfMonth = new Date(currentYear-1900, currentMonth, 1).getDay();
			if (i < firstDayOfMonth) continue;
			if (i >= firstDayOfMonth + calendar.get(currentYear).get(currentMonth).getDays().size())
				continue;
			//--------- END OF MODIFICATION BY TMR -------------
			
			dates.get(i).addActionListener
			(
				new ActionListener()
				{
					public void actionPerformed(ActionEvent evt)
					{
						
						String date = evt.getActionCommand();
						// DEBUG System.out.println("Date selected = " + date);
						toggleBusinessDay(date);
						
						int firstDayOfMonth = new Date(currentYear-1900, currentMonth, 1).getDay();
						
						try
						{
							int d = Integer.parseInt(date);
						
							if(cc.isBusinessDay(currentMonth, d-1, currentYear))
							{
								dates.get(d+firstDayOfMonth-1).setForeground(Color.white);
								dates.get(d+firstDayOfMonth-1).setBackground(Color.blue);
							}
							else
							{
								dates.get(d+firstDayOfMonth-1).setForeground(Color.black);
								dates.get(d+firstDayOfMonth-1).setBackground(Color.LIGHT_GRAY);
							}
						}
						catch (Exception ex)
						{
						}
						
					}
				}
			);
		}

		this.prevYear.addActionListener
		(

				new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						ConfigureBlackoutsView cbv = new ConfigureBlackoutsView(myDialog, currentMonth, currentYear-1, currentDate, cc, false);
						PanelSwap.swapInPanel(myDialog, cbv);
					}
				}
		);
		
		this.nextYear.addActionListener
		(

				new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						ConfigureBlackoutsView cbv = new ConfigureBlackoutsView(myDialog, currentMonth, currentYear+1, currentDate, cc, false);
						PanelSwap.swapInPanel(myDialog, cbv);
					}
				}
		);
		
		this.prevMonth.addActionListener
		(

				new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{

						ConfigureBlackoutsView cbv;
						
						if(currentMonth-1 < 0)
				    	{
							cbv = new ConfigureBlackoutsView(myDialog, 11, currentYear-1, currentDate, cc, false);
				    	}
						else
						{
							int monthToDisplay = currentMonth - 1;
							cbv = new ConfigureBlackoutsView(myDialog, monthToDisplay, currentYear, currentDate, cc, false);
						}
						PanelSwap.swapInPanel(myDialog, cbv);
					}
				}
		);
		
		this.nextMonth.addActionListener
		(

				new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						ConfigureBlackoutsView cbv;
						
						if(currentMonth+1 > 11)
				    	{
							cbv = new ConfigureBlackoutsView(myDialog, 0, currentYear+1, currentDate, cc, false);
				    	}
						else
						{
							int monthToDisplay = currentMonth + 1;
							cbv = new ConfigureBlackoutsView(myDialog, monthToDisplay, currentYear, currentDate, cc, false);
						}
						PanelSwap.swapInPanel(myDialog, cbv);
					}
				}
		);
		
		this.backButton.addActionListener
		(

				new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						 int response;

						  response = JOptionPane.showConfirmDialog(null, "You will lose any unsaved changes made to this calendar! Are you sure you'd like to continue?");
						  
						  if(response == 0)
						  {
							  CalendarView cv = new CalendarView(myDialog);
							  PanelSwap.swapInPanel(myDialog, cv);
						  }	
					}
				}
		);
		
		this.resetButton.addActionListener
		(

				new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						  int response;

						  response = JOptionPane.showConfirmDialog(null, "Are you sure you'd like to reset the calendar?");
						  
						  if(response == 0)
						  {
							  ConfigureBlackoutsView cbv = new ConfigureBlackoutsView(myDialog, currentMonth, currentYear, currentDate, cc, true);
							  PanelSwap.swapInPanel(myDialog, cbv);
						  }			  
					}
				}
		);
		
		this.confirmButton.addActionListener
		(

				new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						  int response;

						  response = JOptionPane.showConfirmDialog(null, "Are you sure you'd like to apply changes to the calendar?");
						  
						  if(response == 0)
						  {
							 ccIO.writeCalendar(cc);
							 JOptionPane.showMessageDialog(null, "Changes successfully applied!");
						  }			  
					}
				}
		);
	}		

	//--------------------------------------------------------------------------
	public void toggleBusinessDay(String date)
	{
		String month = ""+currentMonth;
		String year = ""+currentYear;
		
		cc.toggleBusinessDay(month, year, date);
	}
	
	//--------------------------------------------------------------------------	
	public String getMonthName(int m)
	{
		return monthName[m];
	}

}
