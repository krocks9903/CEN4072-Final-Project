package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.BaseTest;

import java.util.List;

/**
 * HomepageTest - Tests for the LinkedIn public homepage (no login required).
 * Contributor: [Member 1 Name]
 */
public class HomepageTest extends BaseTest {

    private static final String HOME_URL = "https://www.linkedin.com/";

    @Test(description = "Verify LinkedIn homepage loads with correct title")
    public void testHomepageTitle() {
        getDriver().get(HOME_URL);
        String title = getDriver().getTitle();
        Assert.assertTrue(title.toLowerCase().contains("linkedin"),
                "Homepage title should contain 'LinkedIn'. Actual: " + title);
    }

    @Test(description = "Verify the LinkedIn logo is displayed on the homepage")
    public void testLogoIsDisplayed() {
        getDriver().get(HOME_URL);
        WebElement logo = getWait().until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.cssSelector("icon[data-test-id='nav-logo'], .nav-logo, a[href='/'] svg, .logo"))
        );
        Assert.assertTrue(logo.isDisplayed(), "LinkedIn logo should be visible on the homepage");
    }

    @Test(description = "Verify the 'Join now' button is present on the homepage")
    public void testJoinNowButtonPresent() {
        getDriver().get(HOME_URL);
        WebElement joinButton = getWait().until(
                ExpectedConditions.presenceOfElementLocated(
                        By.xpath("//a[contains(text(),'Join now') or contains(text(),'join now')]"))
        );
        Assert.assertTrue(joinButton.isDisplayed(), "'Join now' button should be visible");
    }

    @Test(description = "Verify the 'Sign in' link is present on the homepage")
    public void testSignInLinkPresent() {
        getDriver().get(HOME_URL);
        WebElement signInLink = getWait().until(
                ExpectedConditions.presenceOfElementLocated(
                        By.xpath("//a[contains(@href, '/login') or contains(text(),'Sign in')]"))
        );
        Assert.assertTrue(signInLink.isDisplayed(), "'Sign in' link should be visible");
    }

    @Test(description = "Verify the homepage URL is correct and page is loaded")
    public void testHomepageUrlAndStatus() {
        getDriver().get(HOME_URL);
        getWait().until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));
        String currentUrl = getDriver().getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("linkedin.com"),
                "URL should contain 'linkedin.com'. Actual: " + currentUrl);
        // Page should not be a blank page
        String pageSource = getDriver().getPageSource();
        Assert.assertTrue(pageSource.length() > 500,
                "Page source should not be empty — page did not load correctly");
    }
}
