package io.github.mfaisalkhatri.tests;

import static io.github.mfaisalkhatri.drivers.DriverManager.getDriver;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import io.github.mfaisalkhatri.pages.IncidentListPage;
import io.github.mfaisalkhatri.pages.LoginPage;
import io.github.mfaisalkhatri.pages.NewIncidentPage;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * @author Faisal Khatri
 * @since 2/7/2023
 **/
public class ServiceNowTests extends BaseTest {

    private static final String CALLER_NAME       = "John Adams";
    private static final String CHANNEL           = "Phone";
    private static final String SHORT_DESCRIPTION = "New incident created from automated test!";

    @BeforeClass

    public void setup () {
        getDriver ().navigate ()
            .to ("https://dev21190.service-now.com/");
    }

    @Test
    public void testNewIncident () {
        LoginPage loginPage = new LoginPage ();
        loginPage.performLogin ();
        NewIncidentPage newIncidentPage = new NewIncidentPage ();
        assertEquals (newIncidentPage.getPageTitle (), "New record");

        IncidentListPage incidentListPage = newIncidentPage.createNewIncident (CALLER_NAME, CHANNEL, SHORT_DESCRIPTION);
        
        assertNotNull (incidentListPage.getIncidentNumber ());
        assertTrue (incidentListPage.getOpenedValue ()
            .contains (incidentListPage.getCurrentDate ()));
        assertEquals (incidentListPage.getShortDescriptionValue (), SHORT_DESCRIPTION);
        assertEquals (incidentListPage.getCallerNameValue (), CALLER_NAME);
        assertEquals (incidentListPage.getStateValue (), "New");
        assertEquals (incidentListPage.getCategoryValue (), "Inquiry / Help");
    }

}
