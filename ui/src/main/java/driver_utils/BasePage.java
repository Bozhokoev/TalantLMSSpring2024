package driver_utils;

import com.codeborne.selenide.Selenide;
import element_utils.SelenideElementActions;
import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.OutputType;

import static com.codeborne.selenide.Selenide.open;

@Slf4j
public abstract class BasePage {
    @Getter
    public BrowserManager browserManager = new BrowserManager();
    @Getter
    public SelenideElementActions webElementActions = new SelenideElementActions();
    @Getter
    public FrameManager frameManager = new FrameManager();
    private String url;

    public BasePage() {
    }

    public BasePage(String url) {
        this.url = url;
    }

    @Step("Open Login page")
    public BasePage openLoginPage() {
        log.info("Navigate to: {}", this.url);
        return this;
    }

    @Step("Open Login page")
    public BasePage openLoginPage(String url) {
        log.info("Navigate to: {}", url);
        open(url);
        return this;
    }

    @Step("Open Login page")
    public BasePage navigateTo(String url) {
        log.info("Navigate to: {}", url);
        Selenide
                .webdriver()
                .object()
                .navigate()
                .to(url);
        return this;
    }

    @Attachment(value = "Скриншот", type = "image/png")
    @Step("Take screenshot")
    public byte[] takeScreenshot() {
        log.info("Take screenshot");
        return Selenide.screenshot(OutputType.BYTES);  // Делает скриншот и возвращает его как байты
    }
}