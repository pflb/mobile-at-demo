package ru.pflb.at.mobile.test;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.remote.MobilePlatform;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import ru.pflb.at.mobile.test.wiki.pages.MainScreen;

import java.net.URL;
import java.util.Properties;
import java.util.Set;
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

    public static WebElement waitForElement(By locator) {
        return driverWait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public static void swipeToDirection(String direction) {

        sleep(1500);

        int screenHeight = driver.manage().window().getSize().getHeight();
        int screenWidth =  driver.manage().window().getSize().getWidth();

        int startx;
        int starty;
        int endx;
        int endy;

        if(direction.equals("left")){
            startx = (int) (screenWidth * 0.98);
            starty = (int) (screenHeight * 0.5);
            endx = (int) (screenWidth * 0.2);
            endy = starty;
            driver.swipe(startx, starty, endx, endy, 1000);
        } else if(direction.equals("right")){
            startx = (int) (screenWidth * 0.02);
            starty = (int) (screenHeight * 0.5);
            endx = (int) (screenWidth * 0.9);
            endy = starty;
            driver.swipe(startx, starty, endx, endy, 1000);
        }else if(direction.equals("up")){
            startx = (int) (screenWidth * 0.5);
            starty = (int) (screenHeight * 0.8);
            endx = (int) (screenWidth * 0.5);
            endy = (int) (screenHeight * 0.2);
            driver.swipe(startx, starty, endx, endy, 1000);
        }
    }

    private static void sleep(long time){
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public static void switchToWebView() {
        Set<String> contextHandles = driver.getContextHandles();
        for (String name : contextHandles) {
            System.out.println("Context ["+name+"]");
            if (name.contains("WEBVIEW")) {
                System.out.println("Switch to webvew");
                driver.context(name);
            }
        }
    }

    public static void switchToNative() {
        Set<String> contextHandles = driver.getContextHandles();
        for (String name : contextHandles) {
            if (name.contains("NATIVE")) {
                driver.context(name);
            }
        }
    }
}
