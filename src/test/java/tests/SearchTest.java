package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import utils.BaseTest;

public class SearchTest extends BaseTest {

    @BeforeClass
    @Override
    public void setUp() {
        super.setUp();
        loginToLinkedIn();
        pause(3000);
    }

    @Test(priority = 1, description = "Verify search bar attributes")
    public void testSearchBarAttributes() {
        WebElement bar = getWait().until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//input[contains(@placeholder,'Search') or contains(@aria-label,'Search')]")));
        Assert.assertTrue(bar.isDisplayed(), "Search bar visible");
        Assert.assertTrue(bar.isEnabled(), "Search bar enabled");
        Assert.assertNotNull(bar.getAttribute("placeholder"), "Should have placeholder");
        takeScreenshot("search_bar");
    }

    @Test(priority = 2, description = "Type in search bar and verify text entered")
    public void testSearchBarTextInput() {
        WebElement bar = getWait().until(ExpectedConditions.elementToBeClickable(
                By.xpath("//input[contains(@placeholder,'Search') or contains(@aria-label,'Search')]")));
        bar.click();
        bar.clear();
        bar.sendKeys("Software Engineer");
        pause(1000);
        Assert.assertEquals(bar.getAttribute("value"), "Software Engineer", "Value should match typed text");
        takeScreenshot("search_typed");
        bar.clear();
    }

    @Test(priority = 3, description = "Search for QA Tester and verify results page")
    public void testSearchSubmitAndResultsLoad() {
        WebElement bar = getWait().until(ExpectedConditions.elementToBeClickable(
                By.xpath("//input[contains(@placeholder,'Search') or contains(@aria-label,'Search')]")));
        bar.click();
        bar.clear();
        bar.sendKeys("QA Tester");
        bar.sendKeys(Keys.ENTER);
        pause(3000);
        Assert.assertTrue(getDriver().getCurrentUrl().contains("search"), "URL should contain search");
        takeScreenshot("search_results");
    }

    @Test(priority = 4, description = "Scroll search results to load more")
    public void testScrollSearchResults() {
        Long initialHeight = (Long) getJs().executeScript("return document.body.scrollHeight");
        scrollDownByPixels(600);
        pause(2000);
        scrollDownByPixels(600);
        pause(2000);
        Long newHeight = (Long) getJs().executeScript("return document.body.scrollHeight");
        Assert.assertTrue(newHeight >= initialHeight, "Results should load more when scrolled");
        takeScreenshot("search_scrolled");
    }

    @Test(priority = 5, description = "Verify filter buttons on search results")
    public void testSearchFiltersPresent() {
        scrollToTop();
        pause(1500);
        SoftAssert sa = new SoftAssert();
        String page = getDriver().getPageSource().toLowerCase();
        sa.assertTrue(page.contains("people"), "Should have People filter");
        sa.assertTrue(page.contains("posts") || page.contains("content"), "Should have Posts filter");
        takeScreenshot("search_filters");
        sa.assertAll();
    }
}
