package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import utils.BaseTest;

public class LoginPageTest extends BaseTest {

    @BeforeClass
    @Override
    public void setUp() {
        super.setUp();
        getDriver().get("https://www.linkedin.com/login");
        pause(2000);
    }

    @Test(priority = 1, description = "Verify all login form elements using soft assertions")
    public void testLoginFormElementsPresent() {
        SoftAssert sa = new SoftAssert();
        WebElement email = getDriver().findElement(By.id("username"));
        WebElement pass = getDriver().findElement(By.id("password"));
        WebElement btn = getDriver().findElement(By.xpath("//button[@type='submit']"));
        sa.assertTrue(email.isDisplayed(), "Email field displayed");
        sa.assertTrue(pass.isDisplayed(), "Password field displayed");
        sa.assertTrue(btn.isDisplayed(), "Sign In button displayed");
        sa.assertTrue(email.isEnabled(), "Email field enabled");
        sa.assertTrue(pass.isEnabled(), "Password field enabled");
        takeScreenshot("login_form_elements");
        sa.assertAll();
    }

    @Test(priority = 2, description = "Verify email field type and autocomplete attributes")
    public void testEmailFieldAttributes() {
        WebElement email = getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("username")));
        Assert.assertEquals(email.getAttribute("type"), "text", "Email type should be text");
        Assert.assertNotNull(email.getAttribute("autocomplete"), "Should have autocomplete attribute");
    }

    @Test(priority = 3, description = "Verify password field masks input")
    public void testPasswordFieldMasksInput() {
        WebElement pass = getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("password")));
        Assert.assertEquals(pass.getAttribute("type"), "password", "Should be type=password");
        pass.clear();
        pass.sendKeys("testpassword123");
        Assert.assertEquals(pass.getAttribute("type"), "password", "Should still mask after typing");
        pass.clear();
    }

    @Test(priority = 4, description = "Verify Sign In button text and CSS")
    public void testSignInButtonProperties() {
        WebElement btn = getWait().until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@type='submit']")));
        Assert.assertTrue(btn.getText().toLowerCase().contains("sign in"), "Button should say Sign in");
        String bg = btn.getCssValue("background-color");
        Assert.assertNotNull(bg, "Button should have background color");
        Assert.assertNotEquals(bg, "transparent", "Button bg should not be transparent");
    }

    @Test(priority = 5, description = "Verify Forgot Password link is present")
    public void testForgotPasswordLink() {
        WebElement forgot = getWait().until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//a[contains(text(),'Forgot') or contains(@href,'request-password-reset')]")));
        Assert.assertTrue(forgot.isDisplayed(), "Forgot password link should be visible");
        Assert.assertNotNull(forgot.getAttribute("href"), "Should have href");
        scrollToElement(forgot);
        takeScreenshot("login_forgot_password");
    }
}
