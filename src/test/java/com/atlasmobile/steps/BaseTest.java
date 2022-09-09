package com.atlasmobile.steps;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.asserts.SoftAssert;

import java.io.File;
import java.io.FileReader;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

/**
 * @author aadhithyan.nagarajan
 */
public abstract class BaseTest {

    public static final String GLOBAL_TEST_RESOURCES_FOLDER = System.getProperty("user.dir") + File.separator + "src" + File.separator + "test" + File.separator + "resources" + File.separator;

    protected static AppiumDriver mDriver;

    protected SoftAssert softAssert;

    private static final Logger log = LogManager.getLogger();

    private Properties properties;

    @Parameters({"configuration", "environment"})
    @BeforeMethod
    public void setUp(@Optional("config") String configuration, @Optional("environment") String environment) {
        properties = new Properties();
        JSONParser parser = new JSONParser();
        DesiredCapabilities capabilities = new DesiredCapabilities();
        try {
            String configFile;
            if (configuration.equals("config")) {
                properties.load(new FileReader(new File(GLOBAL_TEST_RESOURCES_FOLDER + File.separator + "environment.properties")));
                configFile = properties.getProperty("config");
            } else {
                configFile = configuration;
            }
            if(environment.equalsIgnoreCase("environment")){
                environment = "env1";
            }
            JSONObject config = (JSONObject) parser.parse(new FileReader(GLOBAL_TEST_RESOURCES_FOLDER + configFile + ".config.json"));
            JSONObject envs = (JSONObject) config.get("environments");
            String[] configArray = configFile.split("[.]");
            String configType = configArray[0];
            String platformName = configArray[1];
            boolean isAndroid = platformName.equalsIgnoreCase("android");
            if (configType.equalsIgnoreCase("local")) {
                Map<String, String> commonCapabilities = (Map<String, String>) config.get("capabilities");
                Iterator it = commonCapabilities.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry pair = (Map.Entry) it.next();
                    if (capabilities.getCapability(pair.getKey().toString()) == null) {
                        capabilities.setCapability(pair.getKey().toString(), pair.getValue().toString());
                    }
                }
                String server = (String) config.get("server");
                String port = (String) config.get("port");
                URL url = new URL("http://" + server + ":" + port);
                if (isAndroid) {
                    mDriver = new AndroidDriver(url, capabilities);
                } else {
                    mDriver = new IOSDriver(url, capabilities);
                }
            } else if (configType.equalsIgnoreCase("bs") || configType.equalsIgnoreCase("sauce")) {
                Map<String, String> envCapabilities = (Map<String, String>) envs.get(environment);
                Iterator it = envCapabilities.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry pair = (Map.Entry) it.next();
                    capabilities.setCapability(pair.getKey().toString(), pair.getValue().toString());
                }
                Map<String, String> commonCapabilities = (Map<String, String>) config.get("capabilities");
                it = commonCapabilities.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry pair = (Map.Entry) it.next();
                    if (capabilities.getCapability(pair.getKey().toString()) == null) {
                        capabilities.setCapability(pair.getKey().toString(), pair.getValue().toString());
                    }
                }

                String username = System.getenv("BROWSERSTACK_USERNAME");
                if (username == null) {
                    username = (String) config.get("username");
                }

                String accessKey = System.getenv("BROWSERSTACK_ACCESS_KEY");
                if (accessKey == null) {
                    accessKey = (String) config.get("access_key");
                }

                String app = System.getenv("BROWSERSTACK_APP_ID");
                if (app != null && !app.isEmpty()) {
                    capabilities.setCapability("app", app);
                }
                if (isAndroid) {
                    mDriver = new AndroidDriver(new URL("http://" + username + ":" + accessKey + "@" + config.get("server") + "/wd/hub"), capabilities);
                } else {
                    mDriver = new IOSDriver(new URL("http://" + username + ":" + accessKey + "@" + config.get("server") + "/wd/hub"), capabilities);
                }
                log.info("launched the application");
            } else {
                throw new Exception("invalid config type in environment properties");
            }
            setmDriver(mDriver);
        } catch (Exception e) {
            log.fatal("unable to initialize mobile driver " + e);
        }
    }

   @AfterMethod
    public void tearDown() {
        if (mDriver != null) {
            mDriver.quit();
           log.info("closing the app");
        }
    }

    public AppiumDriver getmDriver() {
        return this.mDriver;
    }

    public void setmDriver(AppiumDriver mDriver) {
        this.mDriver = mDriver;
    }

}
