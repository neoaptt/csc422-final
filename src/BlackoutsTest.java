import java.util.*;
import CalendarTool.*;
import javax.swing.*;
import java.text.*;

public class BlackoutsTest
{
	
	//---------------------------------------------------------------------------------------------------	
	public static void main (String [] args)
	{
		Blackouts b = new Blackouts();
		Calendar rightNow = Calendar.getInstance();
		Date todayDate = rightNow.getTime();
		
		System.out.println("Today date: " + todayDate);
		System.out.println("Due date from if checked out TODAY: " + b.computeDueDate(todayDate).toString());
		System.out.println("**************************");
		
		String formatString = "yyyy/MM/dd HH:mm";
		SimpleDateFormat sdf = new SimpleDateFormat(formatString);
		
		String dateValue = "2015/04/07 16:45";
		System.out.println("Check out date and time (in conventional form): " + dateValue);
		try
		{
			todayDate = sdf.parse(dateValue);
			System.out.println("Due Date: " + b.computeDueDate(todayDate).toString());	
			System.out.println("**************************");
		}
		catch (Exception ex)
		{
		}
		todayDate = rightNow.getTime();
		
		Date dueDate;
		String dueDateValue = "2015/04/24 23:59";
		
		try
		{
			dueDate = sdf.parse(dueDateValue);
			
			System.out.println("Date Due: " + dueDate);
			
			System.out.println("Fine Amount: $ " + b.calculateFine(todayDate, dueDate));
		}
		catch (Exception ex)
		{
		}
	
	}
}