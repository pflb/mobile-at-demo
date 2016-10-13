package ru.pflb.at.mobile.test.wiki;

import io.appium.java_client.MobileBy;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import ru.pflb.at.mobile.test.BaseTest;

import java.net.SocketPermission;

/**
 * Created by artim on 23.09.2016.
 */
public class NativScrollTest extends BaseTest {
    @Test
    public void Test(){
        String searchRequest = "Donald Duck";//"Donald Duck"
        String assertRequest = "Donald \"Duck\" Dunn";//"Donald \"Duck\" Dunn"

        System.out.print("Search for element...");
        Assert.assertTrue(mainScreen.searchBar.isDisplayed());
        System.out.println("Click search field...");
        mainScreen.searchBar.click();
        System.out.println("Enter to search input...");
        driver.findElement(By.id("org.wikipedia.alpha:id/search_src_text")).sendKeys(searchRequest);

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        scrollTo("Donald Duck talk");//"Donald Duck Party"

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void scrollTo(String selector) {
        System.out.println("Scrolling to \"" + selector + "\" ... ");
        String selectorString =
                String.format("new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().textContains(\""+selector+"\").instance(0))");
        driver.findElement(MobileBy.AndroidUIAutomator(selectorString));
        System.out.println("Scrolling was ended");

        //UiScrollable uiScrollable = new UiScrollable();
    }
}
