package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utils.BaseTest;

import java.util.Set;

public class NavigationTest extends BaseTest {

    @BeforeClass
    @Override
    public void setUp() {
        super.setUp();
        loginToLinkedIn();
        pause(3000);
    }

    @Test(priority = 1, description = "Navigate to Jobs page")
    public void testNavigateToJobs() {
        WebElement jobs = getWait().until(ExpectedConditions.elementToBeClickable(
                By.xpath("//a[contains(@href,'/jobs')] | //span[text()='Jobs']/ancestor::a")));
        jobs.click();
        pause(2000);
        getWait().until(ExpectedConditions.urlContains("/jobs"));
        Assert.assertTrue(getDriver().getCurrentUrl().contains("/jobs"), "URL should contain /jobs");
        takeScreenshot("nav_jobs");
    }

    @Test(priority = 2, description = "Navigate back to Home/feed")
    public void testNavigateBackToHome() {
        WebElement home = getWait().until(ExpectedConditions.elementToBeClickable(
                By.xpath("//a[contains(@href,'/feed')] | //span[text()='Home']/ancestor::a")));
        home.click();
        pause(2000);
        getWait().until(ExpectedConditions.urlContains("/feed"));
        Assert.assertTrue(getDriver().getCurrentUrl().contains("/feed"), "URL should contain /feed");
    }

    @Test(priority = 3, description = "Navigate to My Network")
    public void testNavigateToMyNetwork() {
        WebElement net = getWait().until(ExpectedConditions.elementToBeClickable(
                By.xpath("//a[contains(@href,'/mynetwork')] | //span[text()='My Network']/ancestor::a")));
        net.click();
        pause(2000);
        getWait().until(ExpectedConditions.urlContains("/mynetwork"));
        Assert.assertTrue(getDriver().getCurrentUrl().contains("/mynetwork"), "URL should contain /mynetwork");
        takeScreenshot("nav_mynetwork");
    }

    @Test(priority = 4, description = "Verify window handle consistency")
    public void testWindowHandleConsistency() {
        String handle = getParentWindowHandle();
        Assert.assertNotNull(handle, "Window handle should not be null");
        Set<String> handles = getDriver().getWindowHandles();
        Assert.assertEquals(handles.size(), 1, "Only one window should be open");
        System.out.println("Window handle: " + handle);
        takeScreenshot("window_handle");
    }

    @Test(priority = 5, description = "Navigate to Notifications")
    public void testNavigateToNotifications() {
        WebElement notif = getWait().until(ExpectedConditions.elementToBeClickable(
                By.xpath("//a[contains(@href,'/notifications')] | //span[text()='Notifications']/ancestor::a")));
        notif.click();
        pause(2000);
        getWait().until(ExpectedConditions.urlContains("/notifications"));
        Assert.assertTrue(getDriver().getCurrentUrl().contains("/notifications"), "URL should contain /notifications");
        takeScreenshot("nav_notifications");
    }
}
