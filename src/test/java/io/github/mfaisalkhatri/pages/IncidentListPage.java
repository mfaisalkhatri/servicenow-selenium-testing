package io.github.mfaisalkhatri.pages;

import static io.github.mfaisalkhatri.drivers.DriverManager.getDriver;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import org.openqa.selenium.By;

/**
 * @author Faisal Khatri
 * @since 2/7/2023
 **/
public class IncidentListPage {

    public String getOpenedValue () {
        return getDriver ().findElement (By.cssSelector ("#incident_table >tbody > tr:nth-child(1) > td:nth-child(4)"))
            .getText ();
    }

    public String getShortDescriptionValue () {
        return getDriver ().findElement (By.cssSelector ("#incident_table >tbody > tr:nth-child(1) > td:nth-child(5)"))
            .getText ();
    }

    public String getCallerNameValue () {
        return getDriver ().findElement (By.cssSelector ("#incident_table >tbody > tr:nth-child(1) > td:nth-child(6)"))
            .getText ();
    }

    public String getStateValue () {
        return getDriver ().findElement (By.cssSelector ("#incident_table >tbody > tr:nth-child(1) > td:nth-child(8)"))
            .getText ();
    }

    public String getCategoryValue () {
        return getDriver ().findElement (By.cssSelector ("#incident_table >tbody > tr:nth-child(1) > td:nth-child(9)"))
            .getText ();
    }

    public String getIncidentNumber () {
        return getDriver ().findElement (By.cssSelector ("#incident_table >tbody > tr:nth-child(1) > td:nth-child(3)"))
            .getText ();
    }

    public String getCurrentDate () {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern ("yyyy-MM-dd");
        LocalDateTime now = LocalDateTime.now (ZoneId.of ("America/Los_Angeles"));
        return dateTimeFormatter.format (now);
    }
}
