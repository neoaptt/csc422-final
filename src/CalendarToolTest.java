import java.util.*;
import CalendarTool.*;
import javax.swing.*;

public class CalendarToolTest
{
	
	//---------------------------------------------------------------------------------------------------	
	public static void main (String [] args)
	{
		Blackouts b = new Blackouts();
		Calendar rightNow = Calendar.getInstance();
		Date todayDate = rightNow.getTime();
		
		JFrame frame = new JFrame("Calendar Tool");
		CalendarView cv = new CalendarView(frame);
		//System.out.println(new Blackouts().computeDueDate(todayDate, "Doesn't matter for now").toString());
		//System.out.println(b.calculateFine(todayDate, new Date(2009-1900,1,10)));
		//
		//System.out.println(new Blackouts().calculateEarliestBorrowDate(todayDate, new Date(2009-1900,2,18)));
	}
}