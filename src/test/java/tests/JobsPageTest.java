package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.BaseTest;

/**
 * JobsPageTest - Tests for LinkedIn Jobs page features (requires login).
 * Contributor: [Member 2 Name]
 */
public class JobsPageTest extends BaseTest {

    private void navigateToJobs() {
        loginToLinkedIn();
        getDriver().get("https://www.linkedin.com/jobs/");
        getWait().until(ExpectedConditions.urlContains("/jobs"));
    }

    @Test(description = "Verify Jobs page loads successfully")
    public void testJobsPageLoads() {
        navigateToJobs();
        String title = getDriver().getTitle();
        Assert.assertTrue(title.toLowerCase().contains("job") || title.toLowerCase().contains("linkedin"),
                "Jobs page title should reference 'Job' or 'LinkedIn'. Actual: " + title);
    }

    @Test(description = "Verify job search input field is present")
    public void testJobSearchFieldPresent() {
        navigateToJobs();
        WebElement jobSearchField = getWait().until(
                ExpectedConditions.presenceOfElementLocated(
                        By.xpath("//input[contains(@aria-label,'Search') or contains(@placeholder,'Search')]"))
        );
        Assert.assertTrue(jobSearchField.isDisplayed(), "Job search input field should be visible");
    }

    @Test(description = "Verify searching for 'QA Tester' returns job results")
    public void testSearchForQATester() {
        navigateToJobs();
        WebElement jobSearchField = getWait().until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//input[contains(@aria-label,'Search') or contains(@placeholder,'title')]"))
        );
        jobSearchField.click();
        jobSearchField.clear();
        jobSearchField.sendKeys("QA Tester");
        jobSearchField.sendKeys(Keys.ENTER);

        getWait().until(ExpectedConditions.urlContains("keywords"));
        String currentUrl = getDriver().getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("keywords") || currentUrl.contains("search"),
                "URL should reflect the search query. Actual: " + currentUrl);
    }

    @Test(description = "Verify 'My Jobs' section or link is accessible")
    public void testMyJobsSectionAccessible() {
        navigateToJobs();
        // Look for My Jobs or Saved Jobs link
        WebElement myJobsLink = getWait().until(
                ExpectedConditions.presenceOfElementLocated(
                        By.xpath("//*[contains(text(),'My jobs') or contains(text(),'My Jobs') or contains(text(),'Saved')]"))
        );
        Assert.assertTrue(myJobsLink.isDisplayed(),
                "'My Jobs' or 'Saved' section should be visible on the Jobs page");
    }

    @Test(description = "Verify the Jobs page URL is correct")
    public void testJobsPageUrl() {
        navigateToJobs();
        String currentUrl = getDriver().getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("linkedin.com/jobs"),
                "URL should contain 'linkedin.com/jobs'. Actual: " + currentUrl);
    }
}
