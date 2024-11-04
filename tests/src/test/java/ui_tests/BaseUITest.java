package ui_tests;

import com.codeborne.selenide.Selenide;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeSuite;

import static com.codeborne.selenide.Configuration.*;
import static com.codeborne.selenide.Configuration.holdBrowserOpen;

@Slf4j

public class BaseUITest {

    protected static final String HEIMDALL_ENDPOINT = ":8443";


//asd

    @BeforeSuite(alwaysRun = true)
    public void configurationSetUp() {
        browserCapabilities = Driver.initBrowserCapabilities();
        browserSize = "1920x1080";
        headless = false;
        holdBrowserOpen = false;
        if (getConfig().remote()) {
            log.warn("==============IN DOCKER=============");
            var currentPort = System.getProperty("CURRENT_PORT");
            log.warn("<==============Retrieving the current Docker port: {}", currentPort);
            remote = getConfig().dockerUrl().replace("{port}", currentPort);
            holdBrowserOpen = false;
        }
    }

    @AfterMethod(alwaysRun = true)
    public void cleanUp() {
        Selenide.clearBrowserCookies();
        Selenide.closeWebDriver();
    }
}
