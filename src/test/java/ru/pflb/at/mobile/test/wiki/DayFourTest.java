package ru.pflb.at.mobile.test.wiki;

import io.appium.java_client.MobileBy;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import ru.pflb.at.mobile.test.BaseTest;

import java.util.List;

/**
 * Created by artim on 22.09.2016.
 */


public class DayFourTest extends BaseTest{

    @Test
    public void test(){

        String searchRequest = "Donald Duck";//"Donald Duck"
        String assertRequest = "Donald \"Duck\" Dunn";//"Donald \"Duck\" Dunn"

        System.out.print("Search for element...");
        Assert.assertTrue(mainScreen.searchBar.isDisplayed());
        System.out.println("Click search field...");
        mainScreen.searchBar.click();
        System.out.println("Enter to search input...");
        driver.findElement(By.id("org.wikipedia.alpha:id/search_src_text")).sendKeys(searchRequest);

        scrollTo("Donald Duck pocket books");
        List<WebElement> listResult = driver.findElements(By.id("org.wikipedia.alpha:id/page_list_item_title"));

        int i=0;
        for(WebElement element:listResult){
            System.out.println("Found element: "+element.getText());
            if (StringUtils.equals(element.getText(),assertRequest))
            {System.out.println("Yahooo! I found it");i=listResult.indexOf(element);break;}
        }

        System.out.println("Click on search result...");
        WebElement firstSearchResult=listResult.get(i);
        Assert.assertTrue(firstSearchResult.getText().contains(assertRequest));
        firstSearchResult.click();
        Assert.assertTrue(driver.findElement(By.id("view_article_header_text")).getText().contains(assertRequest));

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        switchToWebView();


        WebElement qf = driver.findElement(By.xpath("//strong[contains(text(),'Quick facts')]/../.."));
        int startY = qf.getLocation().getY() + qf.getSize().getHeight()/2;
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0," + startY + "-screen.height/2)");


        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void scrollTo(String selector) {
        String selectorString = String.format("new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView("+ selector +")");
        driver.findElement(MobileBy.AndroidUIAutomator(selectorString));
    }

}
