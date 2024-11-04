package driver_utils;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;

import java.util.ArrayList;

public class BrowserManager {

    private String defaultTab;

    public BrowserManager switchToNewTab(String url) {
        defaultTab = WebDriverRunner.getWebDriver().getWindowHandle();
        Selenide.executeJavaScript("window.open('" + url + "','_blank');");
        ArrayList<String> tabs = new ArrayList<>(WebDriverRunner.getWebDriver().getWindowHandles());
        tabs.stream().filter(tab ->
                !tab.equals(defaultTab)).findFirst().ifPresent(newTab
                -> WebDriverRunner.getWebDriver().switchTo().window(newTab));
        return this;
    }

    public BrowserManager switchToDefaultTabAndCloseCurrentTab() {
        WebDriverRunner.getWebDriver().switchTo().window(WebDriverRunner.getWebDriver().getWindowHandle()).close();
        WebDriverRunner.getWebDriver().switchTo().window(WebDriverRunner.getWebDriver().getWindowHandles().stream().findFirst().get());
        return this;
    }

    public BrowserManager closeRemainingTabs() {
        ArrayList<String> tabs = new ArrayList<>(WebDriverRunner.getWebDriver().getWindowHandles());
        for (String tab : tabs) {
            if (!tab.equals(defaultTab)) {
                WebDriverRunner.getWebDriver().switchTo().window(tab).close();
            }
        }
        WebDriverRunner.getWebDriver().switchTo().window(WebDriverRunner.getWebDriver().getWindowHandles().stream().findFirst().get());
        return this;
    }

}
