package io.github.mfaisalkhatri.pages;



import static io.github.mfaisalkhatri.drivers.DriverManager.getDriver;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

/**
 * @author Faisal Khatri
 * @since 2/7/2023
 **/
public class NewIncidentPage {

    private WebElement callerNameField() {
        return getDriver().findElement (By.id("sys_display\\.incident\\.caller_id"));
    }

    private WebElement serviceField() {
        return getDriver ().findElement (By.id("sys_display\\.incident\\.business_service"));
    }

    private WebElement serviceFieldHelpBtn () {
        return getDriver ().findElement (By.id("lookup\\.incident\\.business_service"));
    }

    private Select channelField () {
        return new Select (getDriver ().findElement (By.id("incident\\.contact_type")));
    }

    public void selectChannelByVisibleText(String channelName) {
        channelField ().selectByVisibleText (channelName);
    }

    private WebElement shortDescriptionField() {
        return getDriver ().findElement (By.id("incident\\.short_description"));
    }

    private WebElement submitBtn() {
        return getDriver ().findElement (By.id("sysverb_insert_bottom"));
    }

    public void createNewIncident (String callerName, String channelName, String service, String shortDesc) {
        
    }
}
