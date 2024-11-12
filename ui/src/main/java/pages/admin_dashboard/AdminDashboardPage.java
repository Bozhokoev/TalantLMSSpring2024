package pages.admin_dashboard;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import pages.BasePage;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class AdminDashboardPage extends BasePage {
    public SelenideElement admin = $x("//div[text()='Administrator']");
    public SelenideElement leftSidebarMenu = $("#main-menu");

    @Step("Get admin text")
    public String getAdminText() {
        return elementActions.getText(admin);
    }

    @Step("Select element from left sidebar")
    public void selectMenuItem(String menuItem) {
        SelenideElement menu = leftSidebarMenu.$x(".//li//*[contains(text(), '" + menuItem + "')]").scrollTo();
        elementActions.click(menu);
    }
}
