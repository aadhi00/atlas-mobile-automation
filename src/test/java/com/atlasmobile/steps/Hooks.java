package com.atlasmobile.steps;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import net.thucydides.core.annotations.Managed;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class Hooks {

    @Managed()
    WebDriver mDriver;

    @Before
    public void setUp(){

    }

    @After
    public void tearDown(Scenario scenario) {
        //validate if scenario has failed
      //  if (scenario.isFailed()) {
          //  final byte[] screenshot = ((TakesScreenshot) dr).getScreenshotAs(OutputType.BYTES);
          //  scenario.attach(screenshot, "image/png", scenario.getName());
       // }
    }
}