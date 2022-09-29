package com.atlasmobile.pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class SimpleAlert extends BasePage{

    public SimpleAlert(WebDriver mDriver) {
        super(mDriver);
        waitForElement(new AppiumBy.ByAndroidUIAutomator("text(\"Your credentials were not recognized\")"));
    }

    @iOSXCUITFindBy(accessibility = "foo_bar")
    @AndroidFindBy(className = "android.widget.TextView")
    private List<WebElement> txtLables;

    public boolean verifyAlertMessage(String expTitle, String expDesc){
        List<Boolean> result = new ArrayList<>();
        result.add(txtLables.get(0).getText().equals(expTitle));
        result.add(txtLables.get(1).getText().equals(expDesc));
        return !result.contains(false);
    }

    public void tapOKButton() {
        txtLables.get(2).click();
    }
}
