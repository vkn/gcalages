package birthdays;

import com.google.gdata.data.PlainTextConstruct;
import com.google.gdata.data.calendar.CalendarEntry;
import java.util.Calendar;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;


public class CalendarTest {
    
    public CalendarTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testSetUserName() {
        System.out.println("setUserName");
        String userName = "foobar";
        GoogleCalendar instance = new GoogleCalendar();
        instance.setUserName(userName);
        // TODO review the generated test code and remove the default call to fail.
        assertEquals(userName, instance.getUserName());
    }

    @Test
    public void testSetPassword() {
        System.out.println("setPassword");
        String password = "";
        GoogleCalendar instance = new GoogleCalendar();
        instance.setPassword(password);
    }

    @Test
    public void testComputeAge() {
        System.out.println("computeAge");
        int birthYear = 2007;
        GoogleCalendar instance = new GoogleCalendar();
        assertEquals(Calendar.getInstance().get(Calendar.YEAR) - birthYear, instance.computeAge(birthYear));
    }
    
    @Test
    public void testUpdateCalendarWithAgeEntry() {
        System.out.println("update calendar entry");
        int birthYear = 1977;
        int age = Calendar.getInstance().get(Calendar.YEAR) - birthYear;
        CalendarEntry entry = new CalendarEntry();
        entry.setTitle(new PlainTextConstruct("35, foo bar, " + birthYear));
        entry.setContent(new PlainTextConstruct("test entry for updating current age"));
        
        GoogleCalendar instance = new GoogleCalendar();
        instance.updateBirthdayEntry(entry);
        System.out.println(entry);
        assertEquals(age + ", foo bar, " + birthYear, entry.getTitle().getPlainText());
    }
    
    @Test
    public void testUpdateCalendarWithNoAgeEntry() {
        System.out.println("update calendar with no age entry");
        int birthYear = 1977;
        int age = Calendar.getInstance().get(Calendar.YEAR) - birthYear;
        CalendarEntry entry = new CalendarEntry();
        entry.setTitle(new PlainTextConstruct("foo bar, " + birthYear));
        entry.setContent(new PlainTextConstruct("test entry for updating current age"));
        
        GoogleCalendar instance = new GoogleCalendar();
        instance.updateBirthdayEntry(entry);
        System.out.println(entry);
        assertEquals(age + ", foo bar, " + birthYear, entry.getTitle().getPlainText());
    }
}