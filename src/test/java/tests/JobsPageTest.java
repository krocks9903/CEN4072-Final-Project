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

public class JobsPageTest extends BaseTest {

    @BeforeClass
    @Override
    public void setUp() {
        super.setUp();
        loginToLinkedIn();
        pause(2000);
        getDriver().get("https://www.linkedin.com/jobs/");
        pause(3000);
    }

    @Test(priority = 1, description = "Verify Jobs page loads correctly")
    public void testJobsPageLoadsCorrectly() {
        SoftAssert sa = new SoftAssert();
        sa.assertTrue(getDriver().getCurrentUrl().contains("/jobs"), "URL should contain /jobs");
        sa.assertFalse(getDriver().getTitle().isEmpty(), "Title should not be empty");
        takeScreenshot("jobs_loaded");
        sa.assertAll();
    }

    @Test(priority = 2, description = "Verify job search field and type into it")
    public void testJobSearchFieldInput() {
        WebElement field = getWait().until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//input[contains(@aria-label,'Search') or contains(@placeholder,'Search') or contains(@placeholder,'title')]")));
        Assert.assertTrue(field.isEnabled(), "Search field should be enabled");
        field.click();
        field.clear();
        field.sendKeys("Selenium Tester");
        pause(1000);
        Assert.assertNotNull(field.getAttribute("value"), "Field should have value after typing");
        takeScreenshot("jobs_search_typed");
    }

    @Test(priority = 3, description = "Submit job search and verify URL changes")
    public void testJobSearchSubmit() {
        String urlBefore = getDriver().getCurrentUrl();
        WebElement field = getWait().until(ExpectedConditions.elementToBeClickable(
                By.xpath("//input[contains(@aria-label,'Search') or contains(@placeholder,'Search') or contains(@placeholder,'title')]")));
        field.click();
        field.clear();
        field.sendKeys("Software Tester");
        field.sendKeys(Keys.ENTER);
        pause(3000);
        Assert.assertNotEquals(getDriver().getCurrentUrl(), urlBefore, "URL should change after search");
        takeScreenshot("jobs_search_results");
    }

    @Test(priority = 4, description = "Scroll job results to load more")
    public void testScrollJobResults() {
        Long h1 = (Long) getJs().executeScript("return document.body.scrollHeight");
        scrollDownByPixels(700);
        pause(2000);
        scrollDownByPixels(700);
        pause(2000);
        Long h2 = (Long) getJs().executeScript("return document.body.scrollHeight");
        Assert.assertTrue(h2 >= h1, "Should load more when scrolled");
        takeScreenshot("jobs_scrolled");
    }

    @Test(priority = 5, description = "Scroll to top and verify search bar visible")
    public void testScrollTopJobSearchVisible() {
        scrollToTop();
        pause(1500);
        WebElement field = getWait().until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//input[contains(@aria-label,'Search') or contains(@placeholder,'Search')]")));
        Assert.assertTrue(field.isDisplayed(), "Search field visible after scrolling to top");
        takeScreenshot("jobs_back_to_top");
    }
}
