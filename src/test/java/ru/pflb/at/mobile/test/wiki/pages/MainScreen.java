package ru.pflb.at.mobile.test.wiki.pages;

import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.support.PageFactory;
import ru.pflb.at.mobile.test.BaseTest;

import java.util.concurrent.TimeUnit;

public class MainScreen {

    @AndroidFindBy(id = "org.wikipedia.alpha:id/search_container")
    public MobileElement searchBar;


    public MainScreen(){
        System.out.println("Initializing Main screen elements");
        PageFactory.initElements(new AppiumFieldDecorator(BaseTest.getDriver(), 20, TimeUnit.SECONDS), this);
    }

}
