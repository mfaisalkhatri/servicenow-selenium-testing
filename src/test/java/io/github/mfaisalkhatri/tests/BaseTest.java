package io.github.mfaisalkhatri.tests;

import static io.github.mfaisalkhatri.drivers.DriverManager.createDriver;
import static io.github.mfaisalkhatri.drivers.DriverManager.quitDriver;

import io.github.mfaisalkhatri.enums.Browsers;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

/**
 * @author Faisal Khatri
 * @since 2/7/2023
 **/
public class BaseTest {

    @Parameters ("browser")
    @BeforeClass (alwaysRun = true)
    public void setupTest (final String browser) {
        createDriver (Browsers.valueOf (browser.toUpperCase ()));
    }

    @AfterClass (alwaysRun = true)
    public void tearDown () {
        quitDriver ();
    }

}
