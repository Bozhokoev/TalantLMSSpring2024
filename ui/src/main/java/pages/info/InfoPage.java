package pages.info;

import com.codeborne.selenide.SelenideElement;
import data.MockDataGenerator;
import io.qameta.allure.Step;
import pages.BasePage;

import static com.codeborne.selenide.Selenide.$x;

public class InfoPage extends BasePage {
    public SelenideElement userFirstName = $x("//input[@id='name']");
    public SelenideElement userLastName = $x("//input[@id='surname']");
    public SelenideElement userEmail = $x("//input[@id='email']");
    public SelenideElement userGrade = $x("//input[@id='1']");
    public SelenideElement save = $x("//button[@type='submit']");
    public SelenideElement cancel = $x("//a[@data-testid='cancel-button']");

    @Step("Input first name {0}")
    public void inputFirstName(String name){
        elementActions
                .clear(userFirstName)
                .input(userFirstName, name);
    }

    @Step("Input last name {0}")
    public void inputLastName(String lastName){
        elementActions
                .clear(userLastName)
                .input(userLastName, lastName);
    }

    @Step("Input email address {0}")
    public void inputEmail(String email){
        elementActions
                .clear(userEmail)
                .input(userEmail, email);
    }

    @Step("Input grade {0}")
    public void inputGrade(String grade){
        elementActions
                .clear(userGrade)
                .input(userGrade, grade);
    }

    @Step("Edit user and save successfully")
    public void editUserAndSave(){
        inputFirstName(MockDataGenerator.randomFirstName());
        inputLastName(MockDataGenerator.randomLastName());
        inputEmail(MockDataGenerator.randomEmail());
        inputGrade(MockDataGenerator.randomGrade());
        elementActions.click(save);
    }

    @Step("Edit user and cancel successfully")
    public InfoPage editUserAndCancel(){
        inputFirstName(MockDataGenerator.randomFirstName());
        inputLastName(MockDataGenerator.randomLastName());
        inputEmail(MockDataGenerator.randomEmail());
        inputGrade(MockDataGenerator.randomGrade());
        elementActions.click(cancel);
        return this;
    }
}
