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

import userinterface.WindowPosition;

//==========================================================================
public class CalendarView extends JPanel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	JFrame myFrame;
	
	private JDialog myDialog;
	
	private JLabel titleLabel;
	private JLabel taskLabel;
	
	// Buttons
	private JButton generateCalendarButton;
	private JButton configureBlackoutsButton;
	private JButton quitButton;
	
	//Panels
	private JPanel titlePanel;
	private JPanel buttonsPanel;
	private JPanel navigationButtonsPanel;
	
	//-----------------------------------------------------------
	public CalendarView (JFrame myFrame)
	{
		this.myFrame = myFrame;
		
		myDialog = new JDialog(myFrame, "Calendar Tool", true);
		
		initializeGUIComponents();
		addListeners();
		buildGUI();
		
	}
	
	//-----------------------------------------------------------
	public CalendarView (JDialog myDialog)
	{
		
		this.myDialog = myDialog;
		
		initializeGUIComponents();
		addListeners();
		buildGUI();
		
	}
	
	//-----------------------------------------------------------
	public void initializeGUIComponents()
	{
		titleLabel = new JLabel("Brockport Fast Trax");
		Font myFont_1 = new Font("Helvetica", Font.BOLD, 14);
		titleLabel.setFont(myFont_1);
		taskLabel = new JLabel("Calendar Configuration");
		taskLabel.setFont(myFont_1);
			
		//generateCalendarButton = new JButton("Generate calendar");
		configureBlackoutsButton = new JButton("Configure blackout dates");
		quitButton = new JButton("Quit");
		
		titlePanel = new JPanel();
		navigationButtonsPanel = new JPanel();
		buttonsPanel = new JPanel(new GridLayout(2,1));	
	}
	
	@SuppressWarnings("static-access")
	//------------------------------------------------------------
	public void buildGUI()
	{
		myDialog.setDefaultCloseOperation(myDialog.DISPOSE_ON_CLOSE);
    	
		titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
		JPanel titleLabelPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		titleLabelPanel.add(titleLabel);
		titlePanel.add(titleLabelPanel);
		JPanel taskLabelPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		taskLabelPanel.add(taskLabel);
		titlePanel.add(taskLabelPanel);
	
    	buttonsPanel.add(configureBlackoutsButton);
    	//buttonsPanel.add(generateCalendarButton);
    	
    	
    	navigationButtonsPanel.setLayout(new GridLayout(1,1));
    	navigationButtonsPanel.add(quitButton);
    	
    	
    	
    	this.setLayout(new BorderLayout(10,10));
    	this.add(titlePanel, BorderLayout.NORTH);
    	this.add(buttonsPanel, BorderLayout.CENTER);
    	this.add(navigationButtonsPanel, BorderLayout.SOUTH);
    	
    	Container c =  myDialog.getContentPane();
    	
    	c.add(this);
    	
    	myDialog.pack();
    	WindowPosition.placeCenter(myDialog);
    	myDialog.setVisible(true);
	}
	
	//-------------------------------------------------------------
	public void addListeners()
	{
		/*
		this.generateCalendarButton.addActionListener
		(
				new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{	
						CalendarGeneratorConfigView tv = new CalendarGeneratorConfigView(myDialog);
						PanelSwap.swapInPanel(myDialog, tv);
					}
				}
		);
		
		*/
		
		this.configureBlackoutsButton.addActionListener
		(
				new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						ConfigureBlackoutsView cbv = new ConfigureBlackoutsView(myDialog);
						PanelSwap.swapInPanel(myDialog, cbv);
					}
				}
		);
		
		
		this.quitButton.addActionListener
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
	}
	
	
}
