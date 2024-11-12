package pages.auth;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import pages.BasePage;
import pages.admin_dashboard.AdminDashboardPage;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class LoginPage extends BasePage {
    public SelenideElement loginInput = $(By.id("username"));
    public SelenideElement passwordInput = $(By.id("password"));
    public SelenideElement loginButton = $x("//button[@type='submit']");

    @Step("Do login {0} {1}")
    public AdminDashboardPage doLogin(String username, String password) {
        elementActions.input(loginInput, username)
                .input(passwordInput, password)
                .click(loginButton);
        return new AdminDashboardPage();
    }
}
