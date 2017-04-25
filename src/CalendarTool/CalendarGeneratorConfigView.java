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
import javax.swing.*;

@SuppressWarnings({ "unused", "serial" })
public class CalendarGeneratorConfigView extends JPanel
{
	private JDialog myDialog;
	
	//Labels
	private JLabel titleLabel;
	private JLabel taskLabel;
	private JLabel yearsInAdvanceLabel;
	private JLabel messageLabel;
	
	//Buttons
	private JButton submitButton;
	private JButton quitButton;
	private JButton backButton;
	
	//Text fields	
	private JTextField yearsInAdvanceTextField;
	
	//Panels	
	private JPanel titlePanel;
	private JPanel inputPanel;
	private JPanel buttonsPanel;

	//-------------------------------------------------------------------------
	public CalendarGeneratorConfigView(JDialog myDialog)
	{
		this.myDialog = myDialog;
		
		instantiateGUIComponents();
		addListeners();	
    	buildGUI();
    		
	}
	
	//-------------------------------------------------------------------------
	public void instantiateGUIComponents()
	{	
		titleLabel = new JLabel("Brockport Fast Trax");
		Font myFont_1 = new Font("Helvetica", Font.BOLD, 14);
		titleLabel.setFont(myFont_1);
		taskLabel = new JLabel("Calendar Configuration");
		taskLabel.setFont(myFont_1);
		
		yearsInAdvanceLabel = new JLabel("Configure years in advance: ");
		messageLabel = new JLabel();
		
		yearsInAdvanceTextField = new JTextField();
		
		submitButton = new JButton("Generate Calendar");
		quitButton = new JButton("Quit");
		backButton = new JButton("Back");
		
		// instantiate panels
    	titlePanel = new JPanel ();
    	inputPanel = new JPanel ();
    	buttonsPanel = new JPanel();
	    
	}
	
	@SuppressWarnings("static-access")
	//------------------------------------------------------------------------
	public void buildGUI()
	{
    		
		titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
		JPanel titleLabelPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		titleLabelPanel.add(titleLabel);
		titlePanel.add(titleLabelPanel);
		JPanel taskLabelPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		taskLabelPanel.add(taskLabel);
		titlePanel.add(taskLabelPanel);
		
		inputPanel.setLayout(new GridLayout(2,2,10,10));
    	inputPanel.add(yearsInAdvanceLabel);
    	inputPanel.add(yearsInAdvanceTextField);
    	inputPanel.add(submitButton);
    	inputPanel.add(messageLabel);
    	
		
		buttonsPanel.setLayout(new GridLayout(1,2,10,10));
    	buttonsPanel.add(backButton);
    	buttonsPanel.add(quitButton);   	
    	
    	this.setLayout(new BorderLayout(10,10));
    	this.add(titlePanel, BorderLayout.NORTH);
    	this.add(inputPanel, BorderLayout.CENTER);
    	this.add(buttonsPanel, BorderLayout.SOUTH);
    	
	}
	
	//-------------------------------------------------------------------------
	public void addListeners()
	{	
		quitButton.addActionListener
		(
				new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
					    myDialog.setVisible(false);
						myDialog.dispose();
					}
				}
		);
		
		backButton.addActionListener
		(
				new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						CalendarView cv = new CalendarView(myDialog);
						PanelSwap.swapInPanel(myDialog, cv);
					}
				}
		);
		
		submitButton.addActionListener
		(
				new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						String yearsInAdvance = yearsInAdvanceTextField.getText();
						
						try
						{
							int years = Integer.parseInt(yearsInAdvance);
							
							if(years <=0)
							{
								messageLabel.setForeground(Color.RED);
								messageLabel.setText("Cannot generate past calendars!");								
							}
							else 
							if(years > 1000)
							{
								messageLabel.setForeground(Color.RED);
								messageLabel.setText("Cannot generate calendar for more than 1000 years in advance!");
							}
							else
							{
								CalendarGenerator cg = new CalendarGenerator(years);
								messageLabel.setForeground(Color.GREEN);
								messageLabel.setText("Calendar was successfully generated!");
							}
						}
						catch(NumberFormatException nfe)
						{
							
							messageLabel.setForeground(Color.RED);
							messageLabel.setText("You need to enter a valid number!");
						}
					}
				}
		);
	}
}

