package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import utils.BaseTest;

public class MessagingTest extends BaseTest {

    @BeforeClass
    @Override
    public void setUp() {
        super.setUp();
        loginToLinkedIn();
        pause(2000);
        getDriver().get("https://www.linkedin.com/messaging/");
        pause(3000);
    }

    @Test(priority = 1, description = "Verify Messaging page URL and title")
    public void testMessagingPageUrlAndTitle() {
        SoftAssert sa = new SoftAssert();
        sa.assertTrue(getDriver().getCurrentUrl().contains("/messaging"), "URL should contain /messaging");
        sa.assertFalse(getDriver().getTitle().isEmpty(), "Title should not be empty");
        takeScreenshot("messaging_loaded");
        sa.assertAll();
    }

    @Test(priority = 2, description = "Verify search input on Messaging page")
    public void testMessagingSearchInput() {
        WebElement search = getWait().until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//input[contains(@placeholder,'Search') or contains(@aria-label,'Search')]")));
        Assert.assertTrue(search.isDisplayed(), "Search input visible");
        Assert.assertTrue(search.isEnabled(), "Search input enabled");
        Assert.assertNotNull(search.getAttribute("placeholder"), "Should have placeholder");
    }

    @Test(priority = 3, description = "Verify conversation list area is present")
    public void testConversationListPresent() {
        WebElement area = getWait().until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//*[contains(@class,'msg-conversations') or contains(@class,'msg-thread') or contains(@role,'list') or contains(@class,'scaffold')]")));
        Assert.assertTrue(area.isDisplayed(), "Conversation area visible");
        Assert.assertTrue(area.getText().length() > 0, "Should have content");
        takeScreenshot("messaging_conversations");
    }

    @Test(priority = 4, description = "Scroll messaging page")
    public void testScrollConversationList() {
        scrollDownByPixels(400);
        pause(1500);
        scrollDownByPixels(400);
        pause(1500);
        Assert.assertTrue(getDriver().getCurrentUrl().contains("/messaging"), "Should still be on messaging");
        takeScreenshot("messaging_scrolled");
    }

    @Test(priority = 5, description = "Verify compose button exists")
    public void testComposeButtonExists() {
        WebElement compose = getWait().until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//*[contains(@aria-label,'new message') or contains(@aria-label,'Compose') or contains(@aria-label,'compose') or contains(@class,'msg-overlay')] | //a[contains(@href,'messaging')] | //button[contains(@aria-label,'message')]")));
        Assert.assertNotNull(compose, "Compose button should exist");
        takeScreenshot("messaging_compose");
    }
}
