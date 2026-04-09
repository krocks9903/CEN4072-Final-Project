package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import utils.BaseTest;

public class HomepageTest extends BaseTest {

    private static final String HOME_URL = "https://www.linkedin.com/";

    @BeforeClass
    @Override
    public void setUp() {
        super.setUp();
        getDriver().get(HOME_URL);
        pause(2000);
    }

    @Test(priority = 1, description = "Verify homepage title and URL using soft assertions")
    public void testHomepageTitleAndUrl() {
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(getDriver().getTitle().toLowerCase().contains("linkedin"), "Title should contain LinkedIn");
        softAssert.assertTrue(getDriver().getCurrentUrl().contains("linkedin.com"), "URL should contain linkedin.com");
        softAssert.assertAll();
    }

    @Test(priority = 2, description = "Verify Sign In link attributes")
    public void testSignInLinkAttributes() {
        WebElement signInLink = getWait().until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//a[contains(@href,'/login') or contains(text(),'Sign in')]")));
        Assert.assertTrue(signInLink.isDisplayed(), "Sign in link should be visible");
        String href = signInLink.getAttribute("href");
        Assert.assertNotNull(href, "Sign in should have href");
        Assert.assertTrue(href.contains("login"), "href should contain login");
        takeScreenshot("homepage_sign_in");
    }

    @Test(priority = 3, description = "Verify Join Now button")
    public void testJoinNowButton() {
        WebElement joinBtn = getWait().until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//a[contains(text(),'Join now') or contains(text(),'join now')]")));
        Assert.assertTrue(joinBtn.isDisplayed(), "Join now should be visible");
        Assert.assertTrue(joinBtn.getText().toLowerCase().contains("join"), "Button text should contain join");
    }

    @Test(priority = 4, description = "Scroll down homepage and verify footer loads")
    public void testScrollDownToFooter() {
        scrollDownByPixels(500);
        pause(1000);
        scrollDownByPixels(500);
        pause(1000);
        scrollToBottom();
        pause(1500);
        WebElement footer = getWait().until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//footer | //*[contains(@class,'footer')]")));
        Assert.assertTrue(footer.isDisplayed(), "Footer should be visible after scrolling");
        takeScreenshot("homepage_footer");
    }

    @Test(priority = 5, description = "Scroll back to top and verify page returned")
    public void testScrollBackToTop() {
        scrollToTop();
        pause(1000);
        WebElement topElement = getWait().until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//a[contains(@href,'/login') or contains(text(),'Sign in')]")));
        Assert.assertTrue(topElement.isDisplayed(), "Sign In should be visible after scrolling to top");
        takeScreenshot("homepage_back_to_top");
    }
}
