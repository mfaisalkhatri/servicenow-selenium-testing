package io.github.mfaisalkhatri.pages;

import static io.github.mfaisalkhatri.drivers.DriverManager.getDriver;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * @author Faisal Khatri
 * @since 2/7/2023
 **/
public class LoginPage {

    private String userName = System.getProperty ("website_username");
    private String password = System.getProperty ("website_password");

    private WebElement userNameField () {
        return getDriver ().findElement (By.id ("user_name"));
    }

    private WebElement passwordField () {
        return getDriver ().findElement (By.id ("user_password"));
    }

    private WebElement loginBtn () {
        return getDriver ().findElement (By.id ("sysverb_login"));
    }

    public NewIncidentPage performLogin () {
        userNameField ().sendKeys (userName);
        passwordField ().sendKeys (password);
        loginBtn ().click ();
        getDriver ().navigate ()
            .to ("https://dev21190.service-now.com/incident.do");
        return new NewIncidentPage ();
    }
}
