
import CalendarTool.Blackouts;
import java.text.SimpleDateFormat;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class BlackoutTest {

    private static Blackouts b;

    @Before
    public void setUp() {
        b = new Blackouts();
    }

    @Test
    public void computeDueDateWeekDayTest() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        try {
            assertTrue(b.computeDueDate(sdf.parse("2017/04/18 09:45")).toString().equals("Wed Apr 19 09:45:00 EDT 2017"));
        } catch (Exception ex) {
            assertTrue(false);
        }
    }
    @Test
    public void computeDueDateWeekEndTest() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        try {
            assertTrue(b.computeDueDate(sdf.parse("2017/04/21 09:45")).toString().equals("Mon Apr 24 09:45:00 EDT 2017"));
        } catch (Exception ex) {
            assertTrue(false);
        }
    }
    @Test
    public void computeDueDateBlackoutDayTest() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        try {
            assertTrue(b.computeDueDate(sdf.parse("2017/04/11 09:45")).toString().equals("Thu Apr 13 09:45:00 EDT 2017"));
        } catch (Exception ex) {
            assertTrue(false);
        }
    }

}
