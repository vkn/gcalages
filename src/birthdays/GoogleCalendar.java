package birthdays;

import com.google.gdata.client.calendar.CalendarService;
import com.google.gdata.data.PlainTextConstruct;
import com.google.gdata.data.calendar.CalendarEntry;
import com.google.gdata.data.calendar.CalendarFeed;
import com.google.gdata.util.AuthenticationException;
import com.google.gdata.util.ServiceException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class GoogleCalendar {
    
    private String userName;
    private String password;
    private String feedUrl = "";
    private static final Logger LOG = Logger.getLogger(GoogleCalendar.class.getName());
    

    /**
     * Get the value of feedUrl
     *
     * @return the value of feedUrl
     */
    public String getFeedUrl() {
        return feedUrl;
    }

    /**
     * Set the value of feedUrl
     *
     * @param feedUrl new value of feedUrl
     */
    public void setFeedUrl(String feedUrl) {
        this.feedUrl = feedUrl.replaceAll("public/basic", "private/full").trim();
    }

    /**
     * Set the value of userName
     *
     * @param userName new value of userName
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }
    
    /**
     * 
     * @return String
     */
    public String getUserName() {
        return userName;
    }
    
    /**
     * Set the value of password
     *
     * @param password new value of userName
     */
    public void setPassword(String password) {
        this.password = password;
    }
    
    private CalendarService getService() {
        try {        
            CalendarService service = new CalendarService("Birthday calculation application");
            service.setUserCredentials(userName, password);
            return service;
        } catch (AuthenticationException e) {
            LOG.log(Level.SEVERE, "Cannot Login with {0} {1}", new Object[]{userName, e.getMessage()});
        }
        return null;
    }

    public void updateBirthdays() {
        CalendarService service = getService();
        List<CalendarEntry> entries = _getCalendarEntries(service);
        for (int i = 0; i < entries.size(); i++) {
            CalendarEntry entry = entries.get(i);
            updateBirthdayEntry(entry);
            updateEntryRemote(entry, service);
        }
        System.out.println("\nTotal Entries: " + entries.size());
    }
    
    private void updateEntryRemote(CalendarEntry entry, CalendarService service) {
        try {
            URL editUrl = new URL(entry.getEditLink().getHref());
            service.update(editUrl, entry);        
        } catch (AuthenticationException e) {
            LOG.log(Level.SEVERE, "Cannot Login with {0} {1}", new Object[]{userName, e.getMessage()});
        } catch (MalformedURLException e) {
            LOG.log(Level.SEVERE, "Invalid url {0} {1}", new Object[]{entry.getEditLink().getHref(), e.getMessage()});
        } catch (ServiceException | IOException e) {
            LOG.log(Level.SEVERE, "Cannot get feed {0}", e.getMessage());
        }            
    }
    
    private List<CalendarEntry> _getCalendarEntries(CalendarService service) {
        String url = "http://www.google.com/calendar/feeds/" + userName + "@gmail.com/private/full";
        try {
            System.out.println("Entered URL: " +  feedUrl);
            URL metafeedUrl = new URL(!feedUrl.isEmpty() ? feedUrl : url);
            System.out.println("Using URL: " + metafeedUrl.toString());
            System.out.println("Getting Calendar entries...\n");
            CalendarFeed feed = service.getFeed(metafeedUrl, CalendarFeed.class);
            System.out.println(feed.getTitle().getPlainText());
            return feed.getEntries();
        } catch (AuthenticationException e) {
            LOG.log(Level.SEVERE, "Cannot Login with {0} {1}", new Object[]{userName, e.getMessage()});
        } catch (MalformedURLException e) {
            LOG.log(Level.SEVERE, "Invalid url {0} {1}", new Object[]{url, e.getMessage()});
        } catch (ServiceException | IOException e) {
            LOG.log(Level.SEVERE, "Cannot get feed {0}", e.getMessage());
        }
        return Collections.emptyList();
    }
    
    public void updateBirthdayEntry(CalendarEntry entry) {
        
        System.out.println("\n\n");
        //System.out.println("\t entry kind  " + entry());
        String entryText = entry.getTitle().getPlainText();
        Pattern birthYearPattern = Pattern.compile("([\\d]{4})$");
        Matcher birthYearMatcher = birthYearPattern.matcher(entryText);
        if (birthYearMatcher.find()) {
            System.out.println("\t Old entry text: " + entryText);
            int birthYear = Integer.parseInt(birthYearMatcher.group(0));
            String currentAge = Integer.toString(computeAge(birthYear));
            entryText = entryText.replaceFirst("^([\\d]{1,3})", currentAge);
            if (!entryText.startsWith(currentAge)) {
                entryText = currentAge + ", " + entryText;
            }
            entry.setTitle(new PlainTextConstruct(entryText));
            System.out.println("\t New entry text: " + entry.getTitle().getPlainText());
        } else {
            System.out.println("\t No birthyear information found:  " + entryText);
        }
    }
    
    public int computeAge(int birthYear) {
        return Calendar.getInstance().get(Calendar.YEAR) - birthYear;
    }
}
