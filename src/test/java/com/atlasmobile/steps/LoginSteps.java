package com.atlasmobile.steps;

import com.atlasmobile.pages.BiometricPage;
import com.atlasmobile.pages.LoginPage;
import com.atlasmobile.pages.SimpleAlert;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.thucydides.core.annotations.Step;

public class LoginSteps {

    LoginPage loginPage;

    @Step
    public boolean isOnLoginPage() {
        return loginPage.verifyTitle();
    }

    @Step
    public void entersUsernameAndPassword(String username, String password) {
        loginPage.enterUsernameAndPassword(username, password);
    }

    @Step
    public void tapsSignInButton() {
        loginPage.tapSignInButton();
    }

    @Step
    public boolean isShownAnAlertMessage() {
        return true;
    }
}
