package ru.pflb.at.mobile.test;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.remote.MobilePlatform;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import ru.pflb.at.mobile.test.wiki.pages.MainScreen;

import java.net.URL;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class BaseTest {

    protected static AppiumDriver driver;
    protected static MainScreen mainScreen;
    protected static WebDriverWait driverWait;


    public static AppiumDriver getDriver(){
        return  driver;
    }


    @BeforeSuite
    public void setUp() throws  Exception{
        System.out.println("Starting Appium driver...");
        startDriver();
        mainScreen=new MainScreen();
    }

    @AfterMethod
    public void afterEachTest(){
        System.out.println("After Test");
        driver.resetApp();
    }

    @AfterSuite
    public void tearDown(){
        System.out.println("Stopping Appium driver...");
        driver.quit();
    }

    public void startDriver() throws Exception {
        Properties baseProperties = new Properties();
        baseProperties.load(BaseTest.class.getResourceAsStream("/properties/platform.properties"));

        if (driver == null) {
            String platform = baseProperties.getProperty("platform");
            if (platform.equals("Android")) {
                Properties androidDriverProperties = new Properties();
                androidDriverProperties.load(BaseTest.class.getResourceAsStream("/properties/android.properties"));

                String deviceName=androidDriverProperties.getProperty("device.name");
                String testedApplication=androidDriverProperties.getProperty("application.path");

                DesiredCapabilities capabilities = new DesiredCapabilities();
                capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, deviceName);
                capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, MobilePlatform.ANDROID);
                capabilities.setCapability(MobileCapabilityType.APP,testedApplication);

                URL serverUrl = new URL(androidDriverProperties.getProperty("appium.server"));
                System.out.println("Creating Android driver...");
                driver = new AndroidDriver(serverUrl, capabilities);
                driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
            }else{
                System.out.print("Unsupported capability...");
            }

            if(driver!=null) {
                driverWait = new WebDriverWait(driver, 60);
            }
        } else {
            driver.resetApp();
        }
    }
}
