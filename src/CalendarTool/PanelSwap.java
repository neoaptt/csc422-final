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

/**
* PanelSwap.java
* This just shows how to remove the current panel in the
* dialog and replace it with a new panel. The dialog and 
* new panel are passed as parameters to the static method
* swapInPanel
* 
* T.M. Rao
* Jan 2008
*/
//----------------------------------------------------------
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

@SuppressWarnings("unused")
public class PanelSwap
{

	//----------------------------------------------------------------------
    public static void swapInPanel(JFrame frame, JPanel newPanel)
    {
        // get the current view
        JPanel currentView =
            (JPanel)frame.getContentPane().getComponent(0);
           
        // and remove it
        frame.getContentPane().remove(currentView);
       
        // add our view
        frame.getContentPane().add(newPanel);
       
        //pack the frame and show it
        frame.pack();
  }
  
  	//----------------------------------------------------------------------
    public static void swapInPanel(JDialog dialog, JPanel newPanel)
    {
        // get the current view
        JPanel currentView =
            (JPanel)dialog.getContentPane().getComponent(0);
           
        // and remove it
        dialog.getContentPane().remove(currentView);
       
        // add our view
        dialog.getContentPane().add(newPanel);
       
        //pack the frame and show it
        dialog.pack();
  }
    
   //------------------------------------------------------------------------------------
   public static void swapInPanel(JTabbedPane tabbedPane, JPanel oldPanel, JPanel newPanel)
   {
	   tabbedPane.remove(oldPanel);
	   
	   // add our view
	   tabbedPane.add(newPanel);
  }
}
