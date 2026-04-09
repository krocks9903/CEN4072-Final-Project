package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.BaseTest;

/**
 * ProfilePageTest - Tests for the user's own LinkedIn profile page (requires login).
 * Contributor: [Member 3 Name]
 */
public class ProfilePageTest extends BaseTest {

    private void navigateToProfile() {
        loginToLinkedIn();
        // Click the "Me" icon to access profile
        WebElement meIcon = getWait().until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//a[contains(@href,'/in/')] | //img[contains(@class,'global-nav__me-photo')]/ancestor::a | //span[text()='Me']/ancestor::button"))
        );
        meIcon.click();

        // Click "View Profile" from dropdown if it appears
        try {
            WebElement viewProfile = getWait().until(
                    ExpectedConditions.elementToBeClickable(
                            By.xpath("//a[contains(text(),'View Profile') or contains(text(),'View profile')]"))
            );
            viewProfile.click();
        } catch (Exception e) {
            // If "Me" was a direct link to profile, we may already be there
        }

        getWait().until(ExpectedConditions.urlContains("/in/"));
    }

    @Test(description = "Verify profile page loads and URL contains '/in/'")
    public void testProfilePageLoads() {
        navigateToProfile();
        String currentUrl = getDriver().getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("/in/"),
                "Profile URL should contain '/in/'. Actual: " + currentUrl);
    }

    @Test(description = "Verify profile name/heading is displayed")
    public void testProfileNameDisplayed() {
        navigateToProfile();
        WebElement profileName = getWait().until(
                ExpectedConditions.presenceOfElementLocated(
                        By.xpath("//h1[contains(@class,'text-heading') or contains(@class,'inline')]"))
        );
        Assert.assertTrue(profileName.isDisplayed(), "Profile name heading should be visible");
        Assert.assertTrue(profileName.getText().length() > 0, "Profile name should not be empty");
    }

    @Test(description = "Verify profile photo or avatar section exists")
    public void testProfilePhotoSection() {
        navigateToProfile();
        WebElement profilePhoto = getWait().until(
                ExpectedConditions.presenceOfElementLocated(
                        By.xpath("//img[contains(@class,'profile') or contains(@class,'pv-top-card')]" +
                                " | //div[contains(@class,'pv-top-card')]//img"))
        );
        Assert.assertNotNull(profilePhoto, "Profile photo or avatar should be present on the page");
    }

    @Test(description = "Verify Experience or About section is present on profile")
    public void testExperienceOrAboutSectionPresent() {
        navigateToProfile();
        WebElement section = getWait().until(
                ExpectedConditions.presenceOfElementLocated(
                        By.xpath("//*[contains(@id,'experience') or contains(@id,'about') " +
                                "or contains(text(),'Experience') or contains(text(),'About')]"))
        );
        Assert.assertTrue(section.isDisplayed(),
                "An 'Experience' or 'About' section should be visible on the profile page");
    }

    @Test(description = "Verify the profile page title contains LinkedIn")
    public void testProfilePageTitle() {
        navigateToProfile();
        String title = getDriver().getTitle();
        Assert.assertTrue(title.toLowerCase().contains("linkedin"),
                "Profile page title should contain 'LinkedIn'. Actual: " + title);
    }
}
