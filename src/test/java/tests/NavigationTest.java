package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.BaseTest;

/**
 * NavigationTest - Tests for LinkedIn navigation bar links (requires login).
 * Contributor: [Member 2 Name]
 */
public class NavigationTest extends BaseTest {

    @Test(description = "Verify Home link is present in the navigation bar")
    public void testHomeNavLinkPresent() {
        loginToLinkedIn();
        WebElement homeLink = getWait().until(
                ExpectedConditions.presenceOfElementLocated(
                        By.xpath("//a[contains(@href,'/feed')] //span[text()='Home']/.. | //span[text()='Home']/ancestor::a"))
        );
        Assert.assertTrue(homeLink.isDisplayed(), "'Home' nav link should be visible");
    }

    @Test(description = "Verify My Network link is present in the navigation bar")
    public void testMyNetworkNavLinkPresent() {
        loginToLinkedIn();
        WebElement networkLink = getWait().until(
                ExpectedConditions.presenceOfElementLocated(
                        By.xpath("//a[contains(@href,'/mynetwork')] | //span[text()='My Network']/ancestor::a"))
        );
        Assert.assertTrue(networkLink.isDisplayed(), "'My Network' nav link should be visible");
    }

    @Test(description = "Verify Jobs link is present in the navigation bar")
    public void testJobsNavLinkPresent() {
        loginToLinkedIn();
        WebElement jobsLink = getWait().until(
                ExpectedConditions.presenceOfElementLocated(
                        By.xpath("//a[contains(@href,'/jobs')] | //span[text()='Jobs']/ancestor::a"))
        );
        Assert.assertTrue(jobsLink.isDisplayed(), "'Jobs' nav link should be visible");
    }

    @Test(description = "Verify clicking Jobs navigates to the Jobs page")
    public void testJobsNavLinkNavigation() {
        loginToLinkedIn();
        WebElement jobsLink = getWait().until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//a[contains(@href,'/jobs')] | //span[text()='Jobs']/ancestor::a"))
        );
        jobsLink.click();

        getWait().until(ExpectedConditions.urlContains("/jobs"));
        String currentUrl = getDriver().getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("/jobs"),
                "After clicking Jobs, URL should contain '/jobs'. Actual: " + currentUrl);
    }

    @Test(description = "Verify Notifications link is present in the navigation bar")
    public void testNotificationsNavLinkPresent() {
        loginToLinkedIn();
        WebElement notifLink = getWait().until(
                ExpectedConditions.presenceOfElementLocated(
                        By.xpath("//a[contains(@href,'/notifications')] | //span[text()='Notifications']/ancestor::a"))
        );
        Assert.assertTrue(notifLink.isDisplayed(), "'Notifications' nav link should be visible");
    }
}
