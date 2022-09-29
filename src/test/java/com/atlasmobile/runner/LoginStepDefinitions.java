package com.atlasmobile.runner;

import com.atlasmobile.pages.BiometricPage;
import com.atlasmobile.pages.LoginPage;
import com.atlasmobile.pages.SimpleAlert;
import com.atlasmobile.steps.LoginSteps;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.thucydides.core.annotations.Steps;

import static org.assertj.core.api.Assertions.assertThat;

public class LoginStepDefinitions {

    @Steps
    LoginSteps user;
    @Given("User is on login page")
    public void user_is_on_login_page() {
        assertThat(user.isOnLoginPage());
    }
    @When("User enters {string} and {string}")
    public void user_enters_and(String username, String password) {
        user.entersUsernameAndPassword(username, password);
    }
    @When("User taps Sign In button")
    public void user_taps_sign_in_button() {
       user.tapsSignInButton();
    }
    @Then("Alert message is displayed")
    public void alert_message_is_displayed() {
       assertThat(user.isShownAnAlertMessage());
    }
    @Then("User is taken to OTP page")
    public void user_is_taken_to_otp_page() {
        //
    }

    @Then("User is taken to Biometric page")
    public void userIsTakenToBiometricPage() {
       assertThat(user.isOnLoginPage());
    }
}
