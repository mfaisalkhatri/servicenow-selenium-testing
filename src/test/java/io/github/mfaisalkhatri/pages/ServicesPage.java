package io.github.mfaisalkhatri.pages;

import static io.github.mfaisalkhatri.drivers.DriverManager.getDriver;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * @author Faisal Khatri
 * @since 2/7/2023
 **/
public class ServicesPage {

    private WebElement itServicesLink() {
        return getDriver ().findElement (By.cssSelector ("#cmdb_ci_service_table > tbody > tr:nth-child(15) > td:nth-child(3)"));
    }

    public void selectITServicesLink () {
        itServicesLink ().click ();
    }
}
