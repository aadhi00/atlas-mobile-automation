package com.atlasmobile.runner;

import io.cucumber.junit.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;

@RunWith(CucumberWithSerenity.class)
@CucumberOptions(tags="@invalid",features = "src/test/resources/features", glue = {"com.atlasmobile.runner"},plugin = {"pretty"})
public class TestRunner {

}
