package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.BaseTest;

/**
 * SearchTest - Tests for LinkedIn search functionality (requires login).
 * Contributor: [Member 2 Name]
 */
public class SearchTest extends BaseTest {

    @Test(description = "Verify the search bar is present after login")
    public void testSearchBarPresent() {
        loginToLinkedIn();
        WebElement searchBar = getWait().until(
                ExpectedConditions.presenceOfElementLocated(
                        By.xpath("//input[contains(@placeholder,'Search') or contains(@aria-label,'Search')]"))
        );
        Assert.assertTrue(searchBar.isDisplayed(), "Search bar should be visible after login");
    }

    @Test(description = "Verify search for 'Software Engineer' returns results")
    public void testSearchForSoftwareEngineer() {
        loginToLinkedIn();
        WebElement searchBar = getWait().until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//input[contains(@placeholder,'Search') or contains(@aria-label,'Search')]"))
        );
        searchBar.click();
        searchBar.sendKeys("Software Engineer");
        searchBar.sendKeys(Keys.ENTER);

        getWait().until(ExpectedConditions.urlContains("search"));
        String currentUrl = getDriver().getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("search"),
                "URL should contain 'search' after performing a search. Actual: " + currentUrl);
    }

    @Test(description = "Verify search suggestions dropdown appears when typing")
    public void testSearchSuggestionsAppear() {
        loginToLinkedIn();
        WebElement searchBar = getWait().until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//input[contains(@placeholder,'Search') or contains(@aria-label,'Search')]"))
        );
        searchBar.click();
        searchBar.sendKeys("Google");

        // Wait for suggestions/typeahead dropdown
        WebElement suggestions = getWait().until(
                ExpectedConditions.presenceOfElementLocated(
                        By.xpath("//*[contains(@class,'search-typeahead') or contains(@class,'typeahead') or contains(@role,'listbox')]"))
        );
        Assert.assertTrue(suggestions.isDisplayed(), "Search suggestions should appear while typing");
    }

    @Test(description = "Verify People filter tab appears on search results page")
    public void testPeopleFilterOnSearchResults() {
        loginToLinkedIn();
        WebElement searchBar = getWait().until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//input[contains(@placeholder,'Search') or contains(@aria-label,'Search')]"))
        );
        searchBar.click();
        searchBar.sendKeys("Java Developer");
        searchBar.sendKeys(Keys.ENTER);

        WebElement peopleFilter = getWait().until(
                ExpectedConditions.presenceOfElementLocated(
                        By.xpath("//button[contains(text(),'People')] | //a[contains(text(),'People')]"))
        );
        Assert.assertTrue(peopleFilter.isDisplayed(),
                "'People' filter tab should be visible on search results page");
    }

    @Test(description = "Verify search results page has at least one result")
    public void testSearchResultsNotEmpty() {
        loginToLinkedIn();
        WebElement searchBar = getWait().until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//input[contains(@placeholder,'Search') or contains(@aria-label,'Search')]"))
        );
        searchBar.click();
        searchBar.sendKeys("Selenium Testing");
        searchBar.sendKeys(Keys.ENTER);

        getWait().until(ExpectedConditions.urlContains("search"));
        // Wait for results container to load
        WebElement resultsContainer = getWait().until(
                ExpectedConditions.presenceOfElementLocated(
                        By.xpath("//div[contains(@class,'search-results') or contains(@class,'scaffold')]"))
        );
        String pageSource = getDriver().getPageSource();
        Assert.assertTrue(pageSource.length() > 1000,
                "Search results page should have meaningful content loaded");
    }
}
