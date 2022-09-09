package com.atlasmobile.pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

public class BiometricPage extends BasePage {

    public BiometricPage(AppiumDriver mDriver) {
        super(mDriver);
    }

    @AndroidFindBy(uiAutomator = "text(\"Enable fingerprint\")")
    private WebElement title;

    public boolean verifyTitle(){
        return title.isDisplayed();
    }
}
