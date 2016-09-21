package ru.pflb.at.mobile.test.wiki;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import ru.pflb.at.mobile.test.BaseTest;

import java.util.List;

public class DayThreeTest extends BaseTest{

    @Test
    public void testSwipe(){
        System.out.print("Search for element...");
        Assert.assertTrue(mainScreen.searchBar.isDisplayed());

        System.out.println("Click search field...");
        mainScreen.searchBar.click();

        System.out.println("Enter to search input...");
        driver.findElement(By.id("search_src_text")).sendKeys("Donald Duck");
        List<WebElement> listResult = driver.findElements(By.id("org.wikipedia.alpha:id/page_list_item_title"));
        for(WebElement element:listResult){
            System.out.println("Found element:"+element.getText());
        }


        System.out.println("Click on first search result...");
        WebElement firstSearchResult=listResult.get(0);
        Assert.assertTrue(firstSearchResult.getText().contains("Donald Duck"));
        firstSearchResult.click();

        try {
            Thread.sleep(5*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        switchToWebView();

        List<WebElement> allLinks = driver.findElements(By.tagName("a"));
        driver.findElement(By.cssSelector("#content"));
        int i =0;
        for (WebElement eachLink : allLinks) {
            System.out.print("Found link:"+eachLink.getText());
            if(i>10){
                break;
            }
        }

        switchToNative();

        try {
            Thread.sleep(10*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
