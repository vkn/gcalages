package birthdays;

import java.io.IOException;
import java.util.Scanner;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;

public class Application {
    private static final Logger LOG = Logger.getLogger(Application.class.getName());
    
    public static void main(String[] args) throws IOException {
        Handler handler = new FileHandler("application.log", 1024*1024*1024, 10);
        Logger.getLogger("").addHandler(handler);
        
        GoogleCalendar calendar = new GoogleCalendar();
        Scanner input = new Scanner(System.in);
        
        System.out.print("Enter username: ");
        calendar.setUserName(input.nextLine());
        
        System.out.print("Enter password: ");
        calendar.setPassword(input.nextLine());
        
        System.out.print("Enter calendar feed url, otherwise I'll use the main calendar: ");
        calendar.setFeedUrl(input.nextLine());
        calendar.updateBirthdays();
        LOG.info("DONE");
    }    
    
}
