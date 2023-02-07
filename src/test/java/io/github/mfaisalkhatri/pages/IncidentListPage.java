package io.github.mfaisalkhatri.pages;

import static io.github.mfaisalkhatri.drivers.DriverManager.getDriver;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * @author Faisal Khatri
 * @since 2/7/2023
 **/
public class IncidentListPage {

    private String openedColumn() {
        return getDriver().findElement (By.cssSelector ("#incident_table >tbody > tr:nth-child(1) > td:nth-child(4)")).getText ();
    }

    private 

}
