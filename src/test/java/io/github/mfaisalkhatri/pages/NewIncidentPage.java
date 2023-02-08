package io.github.mfaisalkhatri.pages;

import static io.github.mfaisalkhatri.drivers.DriverManager.getDriver;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * @author Faisal Khatri
 * @since 2/7/2023
 **/
public class NewIncidentPage {

    private WebElement callerNameField () {
        return getDriver ().findElement (By.cssSelector (".input-group > input#sys_display\\.incident\\.caller_id"));
    }

    private WebElement serviceField () {
        return getDriver ().findElement (
            By.cssSelector (".input-group > input#sys_display\\.incident\\.business_service"));
    }

    private WebElement serviceFieldHelpBtn () {
        return getDriver ().findElement (By.cssSelector (".input-group-btn > #lookup\\.incident\\.business_service"));
    }

    public void clickServiceFieldHelpBtn () {
        serviceFieldHelpBtn ().click ();
    }

    private Select channelField () {
        return new Select (getDriver ().findElement (By.cssSelector (".form-group select#incident\\.contact_type")));
    }

    public void selectChannelByVisibleText (String channelName) {
        channelField ().selectByVisibleText (channelName);
    }

    private WebElement shortDescriptionField () {
        return getDriver ().findElement (By.cssSelector (".form-group > div input#incident\\.short_description"));
    }

    private WebElement submitBtn () {
        return getDriver ().findElement (By.id ("sysverb_insert_bottom"));
    }

    private WebElement selectServicesLink () {
        return getDriver ().findElement (By.cssSelector (
            "#cmdb_ci_service > #cmdb_ci_service_table > tbody > tr:nth-child(2) > td:nth-child(3) > a"));
    }

    public void selectServices () {
        WebDriverWait wait = new WebDriverWait (getDriver (), Duration.ofSeconds (30));
        final String originalWindow = getDriver ().getWindowHandle ();
        wait.until (ExpectedConditions.numberOfWindowsToBe (2));
        for (final String windowHandle : getDriver ().getWindowHandles ()) {
            if (!originalWindow.contentEquals (windowHandle)) {
                getDriver ().switchTo ()
                    .window (windowHandle);
                selectServicesLink ().click ();
                break;
            }
        }
        getDriver ().switchTo ()
            .window (originalWindow);
    }

    public String getPageTitle () {
        return getDriver ().findElement (By.cssSelector ("h1 > div > div.navbar-title-display-value"))
            .getText ();
    }

    public IncidentListPage createNewIncident (String callerName, String channelName, String shortDesc) {
        callerNameField ().sendKeys (callerName);
        selectChannelByVisibleText (channelName);
        clickServiceFieldHelpBtn ();
        selectServices ();
        shortDescriptionField ().sendKeys (shortDesc);
        submitBtn ().click ();
        getDriver ().navigate ()
            .to ("https://dev21190.service-now.com/incident_list.do");
        return new IncidentListPage ();
    }

}
