package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.BaseTest;

/**
 * LoginPageTest - Tests for the LinkedIn login page UI and validation.
 * Contributor: [Member 1 Name]
 */
public class LoginPageTest extends BaseTest {

    private static final String LOGIN_URL = "https://www.linkedin.com/login";

    @Test(description = "Verify login page loads with correct title")
    public void testLoginPageTitle() {
        getDriver().get(LOGIN_URL);
        String title = getDriver().getTitle();
        Assert.assertTrue(title.toLowerCase().contains("linkedin") || title.toLowerCase().contains("login"),
                "Login page title should reference LinkedIn or Login. Actual: " + title);
    }

    @Test(description = "Verify email input field is present and enabled")
    public void testEmailFieldPresent() {
        getDriver().get(LOGIN_URL);
        WebElement emailField = getWait().until(
                ExpectedConditions.visibilityOfElementLocated(By.id("username"))
        );
        Assert.assertTrue(emailField.isEnabled(), "Email field should be enabled");
        Assert.assertEquals(emailField.getAttribute("type"), "text",
                "Email field should be a text input");
    }

    @Test(description = "Verify password input field is present and is password type")
    public void testPasswordFieldPresent() {
        getDriver().get(LOGIN_URL);
        WebElement passwordField = getWait().until(
                ExpectedConditions.visibilityOfElementLocated(By.id("password"))
        );
        Assert.assertTrue(passwordField.isEnabled(), "Password field should be enabled");
        Assert.assertEquals(passwordField.getAttribute("type"), "password",
                "Password field should mask input (type=password)");
    }

    @Test(description = "Verify Sign In button is present and clickable")
    public void testSignInButtonPresent() {
        getDriver().get(LOGIN_URL);
        WebElement signInBtn = getWait().until(
                ExpectedConditions.elementToBeClickable(By.xpath("//button[@type='submit']"))
        );
        Assert.assertTrue(signInBtn.isDisplayed(), "Sign In button should be visible");
        String btnText = signInBtn.getText().toLowerCase();
        Assert.assertTrue(btnText.contains("sign in"),
                "Button text should contain 'Sign in'. Actual: " + btnText);
    }

    @Test(description = "Verify error message appears with invalid credentials")
    public void testInvalidLoginShowsError() {
        getDriver().get(LOGIN_URL);

        WebElement emailField = getWait().until(
                ExpectedConditions.visibilityOfElementLocated(By.id("username"))
        );
        emailField.sendKeys("invalid_user_test@fakeemail.com");

        WebElement passwordField = getDriver().findElement(By.id("password"));
        passwordField.sendKeys("WrongPassword123!");

        WebElement signInBtn = getDriver().findElement(By.xpath("//button[@type='submit']"));
        signInBtn.click();

        // LinkedIn should show an error or redirect to a challenge page
        getWait().until(ExpectedConditions.or(
                ExpectedConditions.presenceOfElementLocated(By.id("error-for-username")),
                ExpectedConditions.presenceOfElementLocated(By.id("error-for-password")),
                ExpectedConditions.presenceOfElementLocated(
                        By.xpath("//*[contains(@class,'alert') or contains(@class,'error')]")),
                ExpectedConditions.urlContains("checkpoint")
        ));

        // If we're still on login page, check for error; if redirected to checkpoint, that's also expected
        String currentUrl = getDriver().getCurrentUrl();
        boolean hasError = !currentUrl.contains("/feed");
        Assert.assertTrue(hasError,
                "Invalid login should NOT proceed to the feed — should show error or challenge");
    }
}
