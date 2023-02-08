package io.github.mfaisalkhatri.drivers;

import static java.text.MessageFormat.format;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.HashMap;
import java.util.Objects;

import io.github.mfaisalkhatri.enums.Browsers;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

/**
 * @author Faisal Khatri
 * @since 2/7/2023
 **/
public class DriverManager {

    private static final ThreadLocal<WebDriver> DRIVER          = new ThreadLocal<> ();
    private static final String                 GRID_URL        = "@hub.lambdatest.com/wd/hub";
    private static final String                 LT_ACCESS_TOKEN = System.getProperty ("LT_ACCESS_KEY");
    private static final String                 LT_USERNAME     = System.getProperty ("LT_USERNAME");

    public static void createDriver (final Browsers browser) {
        switch (browser) {
            case FIREFOX:
                setupFirefoxDriver ();
                break;
            case REMOTE_CHROME_LAMBDATEST:
                setupChromeInLambdaTest ();
                break;
            case REMOTE_FIREFOX_LAMBDATEST:
                setupFirefoxInLambdaTest ();
                break;
            case CHROME:
            default:
                setupChromeDriver ();
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

    private static void setupChromeDriver () {
        final boolean isHeadless = Boolean.parseBoolean (
            Objects.requireNonNullElse (System.getProperty ("headless"), "true"));
        final HashMap<String, Object> chromePrefs = new HashMap<> ();
        chromePrefs.put ("safebrowsing.enabled", "true");
        chromePrefs.put ("download.prompt_for_download", "false");
        chromePrefs.put ("download.default_directory",
            String.valueOf (Paths.get (System.getProperty ("user.home"), "Downloads")));

        final ChromeOptions options = new ChromeOptions ();
        options.addArguments ("--no-sandbox");
        options.addArguments ("--disable-dev-shm-usage");
        options.addArguments ("--window-size=1050,600");
        if (isHeadless) {
            options.addArguments ("--headless");
        }
        options.addArguments ("--safebrowsing-disable-download-protection");
        options.setExperimentalOption ("prefs", chromePrefs);

        setDriver (new ChromeDriver (options));
    }

    private static void setupChromeInLambdaTest () {
        final ChromeOptions browserOptions = new ChromeOptions ();
        browserOptions.setPlatformName ("Windows 10");
        browserOptions.setBrowserVersion ("108.0");
        browserOptions.setCapability ("LT:Options", ltOptions ());
        try {
            setDriver (
                new RemoteWebDriver (new URL (format ("https://{0}:{1}{2}", LT_USERNAME, LT_ACCESS_TOKEN, GRID_URL)),
                    browserOptions));
        } catch (final MalformedURLException e) {
            System.out.print ("Error setting up cloud browser in LambdaTest " + e);
        }

    }

    private static void setupFirefoxInLambdaTest () {
        final FirefoxOptions browserOptions = new FirefoxOptions ();
        browserOptions.setPlatformName ("Windows 10");
        browserOptions.setBrowserVersion ("108.0");
        browserOptions.setCapability ("LT:Options", ltOptions ());
        try {
            setDriver (
                new RemoteWebDriver (new URL (format ("https://{0}:{1}{2}", LT_USERNAME, LT_ACCESS_TOKEN, GRID_URL)),
                    browserOptions));
        } catch (final MalformedURLException e) {
            System.out.println ("Error setting up firefox  browser in LambdaTest " + e);
        }
    }

    private static void setupFirefoxDriver () {
        final FirefoxOptions options = new FirefoxOptions ();
        options.addArguments ("--no-sandbox");
        options.addArguments ("--disable-dev-shm-usage");
        options.addArguments ("--window-size=1050,600");
        options.addArguments ("--headless");
        setDriver (new FirefoxDriver (options));
    }

    private static HashMap<String, Object> ltOptions () {
        final HashMap<String, Object> ltOptions = new HashMap<> ();
        ltOptions.put ("username", LT_USERNAME);
        ltOptions.put ("accessKey", LT_ACCESS_TOKEN);
        ltOptions.put ("resolution", "2560x1440");
        ltOptions.put ("selenium_version", "4.0.0");
        ltOptions.put ("build", "ServiceNow Build");
        ltOptions.put ("name", "ServiceNow Tests");
        ltOptions.put ("w3c", true);
        ltOptions.put ("plugin", "java-testNG");
        return ltOptions;
    }

}
