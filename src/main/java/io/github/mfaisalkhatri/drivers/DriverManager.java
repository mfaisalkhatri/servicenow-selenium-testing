package io.github.mfaisalkhatri.drivers;

import static java.text.MessageFormat.format;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;

import io.github.mfaisalkhatri.enums.Browsers;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

/**
 * @author Faisal Khatri
 * @since 2/7/2023
 **/
public class DriverManager {

    private static final ThreadLocal<WebDriver> DRIVER        = new ThreadLocal<> ();
    private static final String                 GRID_URL      = "@hub.lambdatest.com/wd/hub";
    private static final String                 LT_ACCESS_KEY = System.getProperty ("LT_ACCESS_KEY");
    private static final String                 LT_USERNAME   = System.getProperty ("LT_USERNAME");

    public static void createDriver (final Browsers browser) {
        switch (browser) {
            case REMOTE_FIREFOX:
                setupFirefoxInCloud ();
                break;
            case REMOTE_CHROME:
            default:
                setupChromeInCloud ();
        }
        setupBrowserTimeouts ();
    }

    public static WebDriver getDriver () {
        return DriverManager.DRIVER.get ();
    }

    public static void quitDriver () {
        if (null != DRIVER.get ()) {
            getDriver ().quit ();
            DRIVER.remove ();
        }
    }

    private static void setDriver (final WebDriver driver) {
        DriverManager.DRIVER.set (driver);
    }

    private static void setupBrowserTimeouts () {
        getDriver ().manage ()
            .timeouts ()
            .implicitlyWait (Duration.ofSeconds (30));
        getDriver ().manage ()
            .timeouts ()
            .pageLoadTimeout (Duration.ofSeconds (30));
        getDriver ().manage ()
            .timeouts ()
            .scriptTimeout (Duration.ofSeconds (30));
    }

    private static void setupChromeInCloud () {
        final ChromeOptions browserOptions = new ChromeOptions ();
        browserOptions.setPlatformName ("Windows 10");
        browserOptions.setBrowserVersion ("108.0");
        browserOptions.setCapability ("LT:Options", ltOptions ());
        try {
            setDriver (
                new RemoteWebDriver (new URL (format ("https://{0}:{1}{2}", LT_USERNAME, LT_ACCESS_KEY, GRID_URL)),
                    browserOptions));
        } catch (final MalformedURLException e) {
            System.out.print ("Error setting up cloud browser in LambdaTest " + e);
        }

    }

    private static void setupFirefoxInCloud () {
        final FirefoxOptions browserOptions = new FirefoxOptions ();
        browserOptions.setPlatformName ("Windows 10");
        browserOptions.setBrowserVersion ("108.0");
        browserOptions.setCapability ("LT:Options", ltOptions ());
        try {
            setDriver (
                new RemoteWebDriver (new URL (format ("https://{0}:{1}{2}", LT_USERNAME, LT_ACCESS_KEY, GRID_URL)),
                    browserOptions));
        } catch (final MalformedURLException e) {
            System.out.println ("Error setting up firefox  browser in LambdaTest " + e);
        }
    }

    private static HashMap<String, Object> ltOptions () {
        final HashMap<String, Object> ltOptions = new HashMap<> ();
        ltOptions.put ("username", LT_USERNAME);
        ltOptions.put ("accessKey", LT_ACCESS_KEY);
        ltOptions.put ("resolution", "2560x1440");
        ltOptions.put ("selenium_version", "4.0.0");
        ltOptions.put ("build", "ServiceNow Build");
        ltOptions.put ("name", "ServiceNow Tests");
        ltOptions.put ("w3c", true);
        ltOptions.put ("plugin", "java-testNG");
        return ltOptions;
    }

}
