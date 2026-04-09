package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import utils.BaseTest;

public class ProfilePageTest extends BaseTest {

    @BeforeClass
    @Override
    public void setUp() {
        super.setUp();
        loginToLinkedIn();
        pause(2000);
        getDriver().get("https://www.linkedin.com/in/me/");
        getWait().until(ExpectedConditions.urlContains("/in/"));
        pause(3000);
    }

    @Test(priority = 1, description = "Verify profile URL contains /in/")
    public void testProfileUrlCorrect() {
        Assert.assertTrue(getDriver().getCurrentUrl().contains("/in/"), "URL should contain /in/");
        takeScreenshot("profile_loaded");
    }

    @Test(priority = 2, description = "Verify profile name heading is displayed")
    public void testProfileNameDisplayed() {
        WebElement name = getWait().until(ExpectedConditions.presenceOfElementLocated(By.xpath("//h1")));
        Assert.assertTrue(name.isDisplayed(), "Name heading should be visible");
        String text = name.getText().trim();
        Assert.assertFalse(text.isEmpty(), "Name should not be empty");
        Assert.assertTrue(text.length() >= 2, "Name should have 2+ chars. Actual: " + text);
        System.out.println("Profile name: " + text);
    }

    @Test(priority = 3, description = "Scroll down to Experience or About section")
    public void testScrollToExperienceSection() {
        scrollDownByPixels(500);
        pause(1500);
        scrollDownByPixels(500);
        pause(1500);
        String page = getDriver().getPageSource().toLowerCase();
        SoftAssert sa = new SoftAssert();
        sa.assertTrue(page.contains("experience") || page.contains("about") || page.contains("education"),
                "Should contain Experience, About, or Education");
        takeScreenshot("profile_sections");
        sa.assertAll();
    }

    @Test(priority = 4, description = "Scroll to bottom and verify page has content")
    public void testScrollToBottomOfProfile() {
        scrollToBottom();
        pause(2000);
        Long height = (Long) getJs().executeScript("return document.body.scrollHeight");
        Assert.assertTrue(height > 1000, "Profile should have significant height. Actual: " + height);
        takeScreenshot("profile_bottom");
    }

    @Test(priority = 5, description = "Scroll back to top and verify name visible")
    public void testScrollBackToProfileTop() {
        scrollToTop();
        pause(1500);
        WebElement name = getWait().until(ExpectedConditions.presenceOfElementLocated(By.xpath("//h1")));
        Assert.assertTrue(name.isDisplayed(), "Name should be visible after scrolling to top");
        takeScreenshot("profile_back_to_top");
    }
}
