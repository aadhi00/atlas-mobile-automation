package com.atlasmobile.pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import net.serenitybdd.core.annotations.findby.By;
import net.serenitybdd.screenplay.PerformsTasks;
import net.serenitybdd.screenplay.actions.ClickInteraction;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

import java.util.ArrayList;
import java.util.List;

public class LoginPage extends BasePage{

    public LoginPage(WebDriver mDriver) {
        super(mDriver);
    }

//    @iOSXCUITFindBy(accessibility = "foo_bar")
//    @AndroidFindBy(className = "android.widget.EditText")
//    private List<WebElement> txtFields;

    public static final Target USERNAME = Target.the("username-field").locatedForAndroid(AppiumBy.androidUIAutomator("text(\"Username\")")).locatedForIOS(AppiumBy.iOSNsPredicateString("value='Username'"));
    public static final Target PASSWORD = Target.the("password-field").locatedForAndroid(AppiumBy.androidUIAutomator("text(\"Password\")")).locatedForIOS(AppiumBy.iOSNsPredicateString("value='Password'"));

    public static final Target SIGNIN = Target.the("sign-in button").locatedForAndroid(AppiumBy.androidUIAutomator("text(\"Sign In\")")).locatedForIOS(AppiumBy.iOSNsPredicateString("value=='Sign In'"));
    @iOSXCUITFindBy(accessibility = "foo_bar")
    @AndroidFindBy(uiAutomator = "text(\"Sign In\")")
    private WebElement btnSignIn;

    @iOSXCUITFindBy(accessibility = "foo_bar")
    @AndroidFindBy(className = "android.widget.TextView")
    private List<WebElement> txtLables;

    public boolean verifyTitle() {
        try {
            waitForElement(new AppiumBy.ByAndroidUIAutomator("text(\"Welcome! Please sign in with your CIBIL credentials\")"));
            return true;
        } catch (TimeoutException toe){
            return false;
        }
    }

    public LoginPage enterUsernameAndPassword(String username, String password){
        verifyTitle();
        //type(txtFields.get(0), username);
       // type(txtFields.get(1), password);
        //Enter.theValue(username).into(USERNAME);
        type($(USERNAME),username);
        System.out.println("entered username");
        type($(PASSWORD),password);
        //Enter.theValue(password).into(PASSWORD);
        System.out.println("entered password");
        return this;
    }

    public LoginPage tapSignInButton() {
       //tap(btnSign);
        clickOn($(SIGNIN));
       return this;
    }

    public void tapOKButton() {
        tap(txtLables.get(2));
    }

    public boolean verifyLablesAndDefaultText(){
        result = new ArrayList<>();
        result.add(txtLables.get(0).getText().equals("Welcome! Please sign in with your CIBIL credentials"));
        result.add(txtLables.get(1).getText().equals("Not a member yet?"));
        result.add(txtLables.get(2).getText().equals("Create a new account."));
        //result.add(txtFields.get(0).getText().equals("Username"));
       // result.add(txtFields.get(1).getText().equals("Password"));
        return !result.contains(false);
    }
}
