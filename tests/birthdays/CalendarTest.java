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
    private GoogleCalendar googleCalendarUpdater;
    
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
        googleCalendarUpdater = new GoogleCalendar();
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testSetUserName() {
        System.out.println("setUserName");
        String userName = "foobar";
        
        googleCalendarUpdater.setUserName(userName);
        assertEquals(userName, googleCalendarUpdater.getUserName());
    }

    @Test
    public void testSetPassword() {
        System.out.println("setPassword");
        String password = "";
        googleCalendarUpdater.setPassword(password);
    }

    @Test
    public void testComputeAge() {
        System.out.println("computeAge");
        int birthYear = 2007;
        assertEquals(Calendar.getInstance().get(Calendar.YEAR) - birthYear, googleCalendarUpdater.computeAge(birthYear));
    }
    
    @Test
    public void testUpdateCalendarWithAgeEntry() {
        System.out.println("update calendar entry");
        int birthYear = 1977;
        int age = Calendar.getInstance().get(Calendar.YEAR) - birthYear;
        CalendarEntry entry = new CalendarEntry();
        entry.setTitle(new PlainTextConstruct("35, foo bar, " + birthYear));
        entry.setContent(new PlainTextConstruct("test entry for updating current age"));
        
        googleCalendarUpdater.updateBirthdayEntry(entry);
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
        
        googleCalendarUpdater.updateBirthdayEntry(entry);
        assertEquals(age + ", foo bar, " + birthYear, entry.getTitle().getPlainText());
    }
    
    @Test
    public void testUpdateCalendarWithNoBirthYearEntry() {
        System.out.println("update calendar with no year entry");
        CalendarEntry entry = new CalendarEntry();
        String origTitle = "1, foo bar";
        entry.setTitle(new PlainTextConstruct(origTitle));
        entry.setContent(new PlainTextConstruct("test entry for updating current age"));
        
        googleCalendarUpdater.updateBirthdayEntry(entry);
        assertEquals(origTitle, entry.getTitle().getPlainText());
    }
    
    public void testSetFeedUrl() {
        googleCalendarUpdater.setFeedUrl("foobar/public/basic ");
        assertEquals("foobar/private/full", googleCalendarUpdater.getFeedUrl());
    }    
}