package element_utils;

import com.codeborne.selenide.*;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.interactions.Actions;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;
import static org.openqa.selenium.Keys.ENTER;

@Slf4j
public class SelenideElementActions {
    /**
     * Action: click the element
     */

    public SelenideElementActions clickWithScrolling(SelenideElement element) {
        element.shouldBe(visible, Duration.ofSeconds(10));
        Actions actions = new Actions(WebDriverRunner.getWebDriver());
        actions.moveToElement(element)
                .click()
                .perform();
        return this;
    }

    public SelenideElementActions click(SelenideElement element) {
        element
                .shouldBe(interactable, Duration.ofSeconds(10))
                .click();
        return this;
    }

    public SelenideElementActions click(SelenideElement element, int timeoutInSeconds) {
        element.shouldBe(interactable, Duration.ofSeconds(timeoutInSeconds)).click();
        return this;
    }

    public void visible(SelenideElement element) {
        try {
            element.should(appear).hover().should(visible);
        } catch (Exception e) {
            throw new RuntimeException("Element is not appeared in HTML or on the page " + element + "\n" + " " + e.getMessage());
        }
    }

    public SelenideElementActions click(SelenideElement element, ClickOptions options) {
        visible(element);
        element.click(options);
        return this;
    }

    public SelenideElementActions clickJS(SelenideElement element) {
        Selenide.executeJavaScript("arguments[0].click();", element.shouldBe(interactable));
        return this;
    }

    public SelenideElementActions hover(SelenideElement element) {
        visible(element);
        element.hover();
        return this;
    }

    public SelenideElementActions setValue(SelenideElement element, String text) {
        visible(element);
        element.shouldBe(visible).setValue(text);
        return this;
    }

    public SelenideElementActions input(SelenideElement element, String text) {
        element
                .shouldBe(visible)
                .scrollTo()
                .sendKeys(text);
        return this;
    }

    public SelenideElementActions inputToNotVisibleElement(SelenideElement element, String text) {
        visible(element);
        element.sendKeys(text);
        return this;
    }

    public SelenideElementActions inputWithClearToNotVisibleElement(SelenideElement element, String text) {
        visible(element);
        element.clear();
        element.sendKeys(text);
        return this;
    }

    public SelenideElementActions inputWithEnter(SelenideElement element, String text) {
        element.sendKeys(text, ENTER);
        return this;
    }

    public SelenideElementActions enter(SelenideElement element) {
        visible(element);
        element.sendKeys(ENTER);
        return this;
    }

    public String getText(SelenideElement element) {
        element
                .shouldBe(visible, Duration.ofSeconds(5))
                .shouldNotBe(empty, Duration.ofSeconds(5));
        return element.getText();
    }

    public List<String> getTexts(SelenideElement element, String path) {
        element
                .shouldBe(visible, Duration.ofSeconds(5))
                .shouldNotBe(empty, Duration.ofSeconds(5));
        return element.findAll(path).texts();
    }

    public String getValue(SelenideElement element) {
        element
                .shouldBe(visible, Duration.ofSeconds(5))
                .shouldNotBe(empty, Duration.ofSeconds(5));
        return element.getValue();
    }

    public String getInnerText(SelenideElement element) {
        return element.shouldBe(visible).innerText();
    }


    public SelenideElementActions selectOption(SelenideElement element, String text) {
        element
                .shouldBe(visible, Duration.ofSeconds(5))
                .shouldNotBe(empty, Duration.ofSeconds(5))
                .scrollTo()
                .selectOption(text);
        return this;
    }

    public SelenideElementActions selectOptionContainingText(SelenideElement element, String text) {
        element.shouldBe(visible).scrollTo().selectOptionContainingText(text);
        return this;
    }

    public SelenideElementActions selectOptions(SelenideElement element, List<String> texts) {
        element.shouldBe(visible).scrollTo().selectOption(texts.toString());
        return this;
    }

    public SelenideElementActions selectOption(SelenideElement element, int i) {
        element.shouldBe(visible).scrollTo().selectOption(i);
        return this;
    }

    public SelenideElement getSelectedOption(SelenideElement element) {
        return element.shouldBe(visible).scrollTo().getSelectedOption();
    }

    public ElementsCollection getSelectedOptions(SelenideElement element) {
        return element.shouldBe(visible).scrollTo().getSelectedOptions();
    }

    public SelenideElementActions pressEnter(SelenideElement element) {
        element.shouldBe(visible).pressEnter();
        return this;
    }

    public SelenideElementActions pressTab(SelenideElement element) {
        element.shouldBe(visible).pressTab();
        return this;
    }

    public SelenideElementActions scrollToElement(SelenideElement element) {
        element.scrollIntoView(true).shouldBe(visible);
        return this;
    }

    public SelenideElementActions selectByValue(SelenideElement element, String value) {
        element.scrollTo().selectOptionByValue(value);
        return this;
    }

    public SelenideElementActions selectByVisibleText(SelenideElement element, String txt) {
        element.selectOptionContainingText(txt);
        return this;
    }

    public SelenideElementActions clear(SelenideElement element) {
        visible(element);
        element.clear();
        return this;
    }

    public static SelenideElement findById(String id) {
        return $(By.id(id));
    }

    public static SelenideElement findByXpath(String xpath) {
        return $x(xpath);
    }

    public SelenideElementActions inputWithClear(SelenideElement element, String txt) {
        visible(element);
        element.shouldBe(visible).clear();
        element.sendKeys(txt);
        return this;
    }

    public SelenideElementActions highlightElement(SelenideElement element) {
        visible(element);
        String originalStyle = element.getAttribute("style");

        // Apply a temporary style to highlight the element
        String highlightStyle = "border: 2px solid red; background-color: yellow;";
        String script = "arguments[0].setAttribute('style', arguments[1]);";
        Selenide.executeJavaScript(script, element, highlightStyle);
        Selenide.sleep(1000);
        // Restore the original style after clicking
        Selenide.executeJavaScript(script, element, originalStyle);
        return this;
    }

    public SelenideElementActions clickElementWithJsExecutor(SelenideElement element) {
        Selenide.executeJavaScript("arguments[0].click();", element);
        return this;
    }

    public String getPropertyValue(SelenideElement element, String propertyValue) {
        element.hover().shouldBe(visible);
        return element.getDomProperty(propertyValue);
    }

    private boolean isElementMatchCondition(SelenideElement element, int timeoutInSeconds, Condition condition) {
        try {
            element.shouldBe(condition, Duration.ofSeconds(timeoutInSeconds));
            return true;
        } catch (AssertionError e) {
            return false;
        }
    }

    public boolean isElementVisible(SelenideElement element, int timeoutInSeconds) {
        return isElementMatchCondition(element, timeoutInSeconds, visible);
    }

    public boolean isElementShouldNotBe(SelenideElement element, int timeoutInSeconds, Condition condition) {
        try {
            element.shouldNotBe(condition, Duration.ofSeconds(timeoutInSeconds));
            return true;
        } catch (AssertionError e) {
            log.error("Element {} should not be {} in {} sec", element, condition, timeoutInSeconds);
            return false;
        }
    }

    public boolean isElementShouldBe(SelenideElement element, int timeoutInSeconds, Condition condition) {
        try {
            element.shouldBe(condition, Duration.ofSeconds(timeoutInSeconds));
            return true;
        } catch (AssertionError e) {
            log.error("Element {} should be {} in {} sec", element, condition, timeoutInSeconds);
            return false;
        }
    }

    public boolean isElementShouldBeVisible(SelenideElement element, int timeoutInSeconds) {
        try {
            element.shouldBe(visible, Duration.ofSeconds(timeoutInSeconds));
            return element.isDisplayed();
        } catch (AssertionError e) {
            log.error("Element {} should be visible in {} sec", element, timeoutInSeconds);
            return false;
        }
    }

    public boolean isElementShouldNotBeVisible(SelenideElement element, int timeoutInSeconds) {
        try {
            element.shouldBe(visible, Duration.ofSeconds(timeoutInSeconds));
            return element.isDisplayed();
        } catch (AssertionError e) {
            log.error("Element {} should be visible in {} sec", element, timeoutInSeconds);
            return false;
        }
    }

    public boolean isElementShouldHaveText(SelenideElement element, int timeoutInSeconds, String text) {
        try {
            element.shouldHave(text(text), Duration.ofSeconds(timeoutInSeconds));
            return element.has(text(text));
        } catch (AssertionError e) {
            log.error("The message '{}' did not appear within {} seconds.", text, timeoutInSeconds);
            return false;
        }
    }

    public boolean isElementShouldNotHaveText(SelenideElement element, int timeoutInSeconds, String text) {
        try {
            element.shouldNotHave(text(text), Duration.ofSeconds(timeoutInSeconds));
            return !element.has(text(text));
        } catch (AssertionError e) {
            log.error("The message '{}' did not appear within {} seconds.", text, timeoutInSeconds);
            return false;
        }
    }

    public boolean isElementShouldHave(SelenideElement element, int timeoutInSeconds, Condition condition) {
        return element.shouldHave(condition, Duration.ofSeconds(timeoutInSeconds)).is(condition);
    }

    public boolean isElementShouldNotHave(SelenideElement element, int timeoutInSeconds, Condition condition) {
        return !element.shouldNotHave(condition, Duration.ofSeconds(timeoutInSeconds))
                .is(condition);
    }

    public void refreshPage() {
        Selenide.refresh();
    }

    public List<String> getAllVisibleTextsFromSelect(SelenideElement element) {
        element.shouldBe(visible).scrollTo();
        ElementsCollection options = element.$$("option");

        return options.stream()
                .map(SelenideElement::getText)
                .collect(Collectors.toList());
    }
}
