package io.github.mfaisalkhatri.tests;

import static io.github.mfaisalkhatri.drivers.DriverManager.getDriver;
import static org.testng.Assert.assertEquals;

import io.github.mfaisalkhatri.pages.LoginPage;
import io.github.mfaisalkhatri.pages.NewIncidentPage;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * @author Faisal Khatri
 * @since 2/7/2023
 **/
public class ServiceNowTests extends BaseTest {

    @BeforeClass
    public void setup () {
        getDriver ().navigate ()
            .to ("https://dev21190.service-now.com/");
    }

    @Test
    public void testCreateNewIncident () {
        LoginPage loginPage = new LoginPage ();
        loginPage.performLogin ();
        NewIncidentPage newIncidentPage = new NewIncidentPage ();
        assertEquals(newIncidentPage.getPageTitle (), "New record");
        newIncidentPage.createNewIncident ("John Adams", "Phone", "This is a test");
    }

}
