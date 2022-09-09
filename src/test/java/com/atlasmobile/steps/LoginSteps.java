package com.atlasmobile.steps;

import com.atlasmobile.pages.BiometricPage;
import com.atlasmobile.pages.LoginPage;
import com.atlasmobile.pages.SimpleAlert;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.asserts.SoftAssert;

public class LoginSteps extends BaseTest {

    private LoginPage loginPage;
    @Given("User is on login page")
    public void user_is_on_login_page() {
        softAssert = new SoftAssert();
        loginPage = new LoginPage(getmDriver());
        softAssert.assertTrue(loginPage.verifyTitle());
    }
    @When("User enters {string} and {string}")
    public void user_enters_and(String username, String password) {
       loginPage.enterUsernameAndPassword(username, password);
    }
    @When("User taps Sign In button")
    public void user_taps_sign_in_button() {
        loginPage.tapSignInButton();
    }
    @Then("Alert message is displayed")
    public void alert_message_is_displayed() {
        SimpleAlert simpleAlert = new SimpleAlert(getmDriver());
        softAssert.assertTrue(simpleAlert.verifyAlertMessage("Your credentials were not recognized", "Sorry, we don't recognize your username or password. Please try again."));
        softAssert.assertAll();
    }
    @Then("User is taken to OTP page")
    public void user_is_taken_to_otp_page() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @Then("User is taken to Biometric page")
    public void userIsTakenToBiometricPage() {
        BiometricPage biometricPage = new BiometricPage(getmDriver());
        softAssert.assertTrue(biometricPage.verifyTitle());
        softAssert.assertAll();
    }
}
