package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.BaseTest;

/**
 * MessagingTest - Tests for LinkedIn Messaging functionality (requires login).
 * Contributor: [Member 3 Name]
 */
public class MessagingTest extends BaseTest {

    private void navigateToMessaging() {
        loginToLinkedIn();
        getDriver().get("https://www.linkedin.com/messaging/");
        getWait().until(ExpectedConditions.urlContains("/messaging"));
    }

    @Test(description = "Verify Messaging page loads successfully")
    public void testMessagingPageLoads() {
        navigateToMessaging();
        String currentUrl = getDriver().getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("/messaging"),
                "URL should contain '/messaging'. Actual: " + currentUrl);
    }

    @Test(description = "Verify Messaging page title is correct")
    public void testMessagingPageTitle() {
        navigateToMessaging();
        String title = getDriver().getTitle();
        Assert.assertTrue(title.toLowerCase().contains("messaging") || title.toLowerCase().contains("linkedin"),
                "Messaging page title should reference messaging or LinkedIn. Actual: " + title);
    }

    @Test(description = "Verify message compose button or 'new message' icon exists")
    public void testComposeMessageButtonExists() {
        navigateToMessaging();
        WebElement composeBtn = getWait().until(
                ExpectedConditions.presenceOfElementLocated(
                        By.xpath("//*[contains(@aria-label,'new message') or contains(@aria-label,'Compose') " +
                                "or contains(@aria-label,'compose') or contains(@class,'msg-overlay-bubble')]" +
                                " | //a[contains(@href,'messaging/new')]"))
        );
        Assert.assertNotNull(composeBtn, "Compose/New Message button should exist on Messaging page");
    }

    @Test(description = "Verify messaging conversation list area is present")
    public void testConversationListPresent() {
        navigateToMessaging();
        WebElement conversationList = getWait().until(
                ExpectedConditions.presenceOfElementLocated(
                        By.xpath("//*[contains(@class,'msg-conversations-container') " +
                                "or contains(@class,'msg-thread') or contains(@role,'list')]"))
        );
        Assert.assertTrue(conversationList.isDisplayed(),
                "Conversation list area should be visible on the Messaging page");
    }

    @Test(description = "Verify messaging search/filter input is available")
    public void testMessagingSearchInput() {
        navigateToMessaging();
        WebElement searchInput = getWait().until(
                ExpectedConditions.presenceOfElementLocated(
                        By.xpath("//input[contains(@placeholder,'Search') or contains(@aria-label,'Search')]"))
        );
        Assert.assertTrue(searchInput.isDisplayed(),
                "Search/filter input should be available in Messaging page");
    }
}
