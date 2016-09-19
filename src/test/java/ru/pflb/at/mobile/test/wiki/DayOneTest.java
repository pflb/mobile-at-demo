package ru.pflb.at.mobile.test.wiki;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.pflb.at.mobile.test.BaseTest;

public class DayOneTest extends BaseTest{

    @Test
    public void testSearchOpen(){
        Assert.assertTrue(mainScreen.searchBar.isDisplayed());

        mainScreen.searchBar.click();
    }

}
