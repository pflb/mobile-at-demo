package ru.pflb.at.mobile.test.wiki;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.remote.MobilePlatform;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.*;
import ru.pflb.at.mobile.test.wiki.pages.MainScreen;

import java.net.URL;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import static java.lang.Thread.sleep;

/**
 * Created by artim on 26.09.2016.
 */
public class DaySixTest{
    private DesiredCapabilities capabilities = new DesiredCapabilities();
    private AppiumDriver driver;
    private MainScreen mainScreen;

    @Parameters({"Device_ID", "Appium_Port"})
    @BeforeTest
    public void startDriver(String deviceId, String appiumPort) throws Exception {
        Properties baseProperties = new Properties();
        baseProperties.load(DaySixTest.class.getResourceAsStream("/properties/platform.properties"));

        if (driver == null) {
            String platform = baseProperties.getProperty("platform");
            if (platform.equals("Android")) {

                setCapabilities(deviceId);
                URL serverUrl = new URL(appiumPort);
                System.out.println("Creating Android driver...");
                driver = new AndroidDriver(serverUrl, capabilities);
                //driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
                mainScreen=new MainScreen(driver);
            } else {
                System.out.print("Unsupported capability...");
            }
        }
    }

    @Test
    public void testForParallel() {

        String searchRequest = "Donald Duck";//"Donald Duck"
        String assertRequest = "Donald \"Duck\" Dunn";//"Donald \"Duck\" Dunn"

        System.out.print("Search for element...");
        Assert.assertTrue(mainScreen.searchBar.isDisplayed());
        System.out.println("Click search field...");
        mainScreen.searchBar.click();
        System.out.println("Enter to search input...");

        try {
            sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        driver.findElement(By.id("search_src_text")).sendKeys(searchRequest);

    }

    @AfterTest
    public void afterEachTest(){
        System.out.println("After Test");
        driver.resetApp();
        System.out.println("Stopping Appium driver...");
        driver.quit();
    }


    public void setCapabilities(String device) throws Exception {

        Properties androidDriverProperties = new Properties();
        androidDriverProperties.load(DaySixTest.class.getResourceAsStream("/properties/android.properties"));
        String deviceName = androidDriverProperties.getProperty("device.name");
        String testedApplication = androidDriverProperties.getProperty("application.path");
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, deviceName);
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, MobilePlatform.ANDROID);
        capabilities.setCapability(MobileCapabilityType.APP, testedApplication);
    }

}
