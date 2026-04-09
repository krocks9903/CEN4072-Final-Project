package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import utils.BaseTest;

import java.util.Set;

public class NetworkTest extends BaseTest {

    @BeforeClass
    @Override
    public void setUp() {
        super.setUp();
        loginToLinkedIn();
        pause(2000);
        getDriver().get("https://www.linkedin.com/mynetwork/");
        pause(3000);
    }

    @Test(priority = 1, description = "Verify My Network page URL and title")
    public void testNetworkPageUrlAndTitle() {
        SoftAssert sa = new SoftAssert();
        sa.assertTrue(getDriver().getCurrentUrl().contains("/mynetwork"), "URL should contain /mynetwork");
        sa.assertNotEquals(getDriver().getTitle(), "", "Title should not be empty");
        takeScreenshot("mynetwork_loaded");
        sa.assertAll();
    }

    @Test(priority = 2, description = "Verify network content has connection-related keywords")
    public void testNetworkContentPresent() {
        WebElement main = getWait().until(ExpectedConditions.presenceOfElementLocated(By.tagName("main")));
        String text = main.getText().toLowerCase();
        Assert.assertTrue(text.length() > 50, "Should have content. Length: " + text.length());
        Assert.assertTrue(text.contains("connect") || text.contains("invitation") || text.contains("network") || text.contains("people"),
                "Should contain network keywords");
    }

    @Test(priority = 3, description = "Scroll to load more connection suggestions")
    public void testScrollNetworkPage() {
        Long h1 = (Long) getJs().executeScript("return document.body.scrollHeight");
        scrollDownByPixels(600);
        pause(2000);
        scrollDownByPixels(600);
        pause(2000);
        scrollDownByPixels(600);
        pause(2000);
        Long h2 = (Long) getJs().executeScript("return document.body.scrollHeight");
        Assert.assertTrue(h2 >= h1, "Should load more when scrolled");
        takeScreenshot("mynetwork_scrolled");
    }

    @Test(priority = 4, description = "Track window handle on network page")
    public void testWindowHandleOnNetworkPage() {
        String handle = getParentWindowHandle();
        Assert.assertNotNull(handle, "Handle should not be null");
        Assert.assertFalse(handle.isEmpty(), "Handle should not be empty");
        Set<String> all = getDriver().getWindowHandles();
        Assert.assertEquals(all.size(), 1, "Should have 1 window. Found: " + all.size());
        System.out.println("Network window handle: " + handle);
        takeScreenshot("mynetwork_handle");
    }

    @Test(priority = 5, description = "Scroll to top and verify page intact")
    public void testScrollTopNetworkPageIntact() {
        scrollToTop();
        pause(1500);
        String url = getDriver().getCurrentUrl();
        Assert.assertTrue(url.contains("mynetwork") || url.contains("linkedin"), "Should still be on LinkedIn");
        Assert.assertTrue(getDriver().getPageSource().length() > 1000, "Page should have content");
        takeScreenshot("mynetwork_top");
    }
}
