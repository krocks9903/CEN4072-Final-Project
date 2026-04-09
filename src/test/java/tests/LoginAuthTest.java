package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import utils.BaseTest;

public class LoginAuthTest extends BaseTest {

    @BeforeClass
    @Override
    public void setUp() {
        super.setUp();
        loginToLinkedIn();
        pause(3000);
    }

    @Test(priority = 1, description = "Verify login redirects to feed with correct title")
    public void testLoginRedirectAndTitle() {
        SoftAssert sa = new SoftAssert();
        sa.assertTrue(getDriver().getCurrentUrl().contains("/feed"), "URL should contain /feed");
        sa.assertTrue(getDriver().getTitle().toLowerCase().contains("linkedin"), "Title should contain LinkedIn");
        takeScreenshot("feed_after_login");
        sa.assertAll();
    }

    @Test(priority = 2, description = "Verify nav bar has expected links")
    public void testNavBarLinksPresent() {
        SoftAssert sa = new SoftAssert();
        WebElement nav = getWait().until(ExpectedConditions.presenceOfElementLocated(By.id("global-nav")));
        String navText = nav.getText().toLowerCase();
        sa.assertTrue(navText.contains("home"), "Nav should have Home");
        sa.assertTrue(navText.contains("jobs"), "Nav should have Jobs");
        sa.assertTrue(navText.contains("messaging"), "Nav should have Messaging");
        sa.assertAll();
    }

    @Test(priority = 3, description = "Scroll feed and verify dynamic content loads")
    public void testScrollFeedLoadsContent() {
        Long initialHeight = (Long) getJs().executeScript("return document.body.scrollHeight");
        scrollDownByPixels(800);
        pause(2000);
        scrollDownByPixels(800);
        pause(2000);
        scrollDownByPixels(800);
        pause(2000);
        Long newHeight = (Long) getJs().executeScript("return document.body.scrollHeight");
        Assert.assertTrue(newHeight > initialHeight, "Page should grow as feed loads. Initial: " + initialHeight + " New: " + newHeight);
        takeScreenshot("feed_scrolled");
    }

    @Test(priority = 4, description = "Scroll back to top and verify search bar visible")
    public void testScrollBackToTopOfFeed() {
        scrollToTop();
        pause(1500);
        WebElement searchBar = getWait().until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//input[contains(@placeholder,'Search') or contains(@aria-label,'Search')]")));
        Assert.assertTrue(searchBar.isDisplayed(), "Search bar should be visible at top");
    }

    @Test(priority = 5, description = "Verify Me menu is accessible")
    public void testMeMenuAccessible() {
        WebElement me = getWait().until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//span[text()='Me'] | //img[contains(@class,'global-nav__me-photo')]")));
        Assert.assertTrue(me.isDisplayed(), "Me menu should be visible");
        takeScreenshot("feed_me_menu");
    }
}
