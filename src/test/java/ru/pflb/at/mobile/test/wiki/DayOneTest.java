package ru.pflb.at.mobile.test.wiki;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.pflb.at.mobile.test.BaseTest;

public class DayOneTest extends BaseTest{

    @Test
    public void testSearchOpen(){
        System.out.print("Search for element...");
        Assert.assertTrue(mainScreen.searchBar.isDisplayed());

        System.out.println("Click");
        mainScreen.searchBar.click();

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Done");
    }

}
