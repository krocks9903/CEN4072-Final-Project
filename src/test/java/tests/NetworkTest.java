package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.BaseTest;

/**
 * NetworkTest - Tests for the LinkedIn My Network page (requires login).
 * Contributor: [Member 3 Name]
 */
public class NetworkTest extends BaseTest {

    private void navigateToMyNetwork() {
        loginToLinkedIn();
        getDriver().get("https://www.linkedin.com/mynetwork/");
        getWait().until(ExpectedConditions.urlContains("/mynetwork"));
    }

    @Test(description = "Verify My Network page loads successfully")
    public void testMyNetworkPageLoads() {
        navigateToMyNetwork();
        String currentUrl = getDriver().getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("/mynetwork"),
                "URL should contain '/mynetwork'. Actual: " + currentUrl);
    }

    @Test(description = "Verify My Network page has a proper title")
    public void testMyNetworkPageTitle() {
        navigateToMyNetwork();
        String title = getDriver().getTitle();
        Assert.assertTrue(title.toLowerCase().contains("network") || title.toLowerCase().contains("linkedin"),
                "My Network page title should reference 'Network' or 'LinkedIn'. Actual: " + title);
    }

    @Test(description = "Verify 'Manage my network' or connections section is present")
    public void testManageNetworkSectionPresent() {
        navigateToMyNetwork();
        WebElement manageSection = getWait().until(
                ExpectedConditions.presenceOfElementLocated(
                        By.xpath("//*[contains(text(),'Manage my network') or contains(text(),'Connections') " +
                                "or contains(text(),'manage') or contains(@class,'mn-community')]"))
        );
        Assert.assertTrue(manageSection.isDisplayed(),
                "'Manage my network' or connections section should be visible");
    }

    @Test(description = "Verify invitation/connection suggestions area exists")
    public void testConnectionSuggestionsExist() {
        navigateToMyNetwork();
        WebElement suggestions = getWait().until(
                ExpectedConditions.presenceOfElementLocated(
                        By.xpath("//*[contains(text(),'people you may know') or contains(text(),'People you may know')" +
                                " or contains(text(),'Invitation') or contains(@class,'discover-entity')]"))
        );
        Assert.assertNotNull(suggestions,
                "Connection suggestions or invitations section should be present");
    }

    @Test(description = "Verify the page body has loaded meaningful content")
    public void testNetworkPageHasContent() {
        navigateToMyNetwork();
        WebElement body = getWait().until(
                ExpectedConditions.presenceOfElementLocated(By.tagName("main"))
        );
        String bodyText = body.getText();
        Assert.assertTrue(bodyText.length() > 50,
                "My Network page should have meaningful content. Content length: " + bodyText.length());
    }
}
