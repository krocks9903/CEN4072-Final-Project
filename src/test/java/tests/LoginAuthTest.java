package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.BaseTest;

/**
 * LoginAuthTest - Tests for successful LinkedIn authentication.
 * Requires valid credentials in config.properties.
 * Contributor: [Member 1 Name]
 */
public class LoginAuthTest extends BaseTest {

    @Test(description = "Verify successful login redirects to feed page")
    public void testSuccessfulLoginRedirectsToFeed() {
        loginToLinkedIn();
        String currentUrl = getDriver().getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("/feed"),
                "After login, URL should contain '/feed'. Actual: " + currentUrl);
    }

    @Test(description = "Verify user profile icon appears after login")
    public void testProfileIconVisibleAfterLogin() {
        loginToLinkedIn();
        WebElement profileIcon = getWait().until(
                ExpectedConditions.presenceOfElementLocated(
                        By.xpath("//img[contains(@class,'global-nav__me-photo') or contains(@alt,'profile')]"))
        );
        Assert.assertTrue(profileIcon.isDisplayed(),
                "Profile icon/photo should be visible in the navigation bar after login");
    }

    @Test(description = "Verify navigation bar is present after login")
    public void testNavBarPresentAfterLogin() {
        loginToLinkedIn();
        WebElement navBar = getWait().until(
                ExpectedConditions.presenceOfElementLocated(By.id("global-nav"))
        );
        Assert.assertTrue(navBar.isDisplayed(), "Global navigation bar should be visible after login");
    }

    @Test(description = "Verify the page title changes after login")
    public void testPageTitleAfterLogin() {
        loginToLinkedIn();
        getWait().until(ExpectedConditions.urlContains("/feed"));
        String title = getDriver().getTitle();
        Assert.assertTrue(title.toLowerCase().contains("feed") || title.toLowerCase().contains("linkedin"),
                "Page title after login should reference feed or LinkedIn. Actual: " + title);
    }

    @Test(description = "Verify the Messaging icon is accessible after login")
    public void testMessagingIconAfterLogin() {
        loginToLinkedIn();
        WebElement messagingIcon = getWait().until(
                ExpectedConditions.presenceOfElementLocated(
                        By.xpath("//a[contains(@href,'/messaging')] | //span[text()='Messaging']/ancestor::a"))
        );
        Assert.assertTrue(messagingIcon.isDisplayed(),
                "Messaging icon should be visible in nav bar after login");
    }
}
