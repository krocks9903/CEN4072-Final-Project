package utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

public class BaseTest {

    private static final ThreadLocal<WebDriver> driverThread = new ThreadLocal<>();
    private static final ThreadLocal<WebDriverWait> waitThread = new ThreadLocal<>();
    private static final ThreadLocal<JavascriptExecutor> jsThread = new ThreadLocal<>();

    @BeforeClass
    public void setUp() {
        WebDriverManager.firefoxdriver().setup();
        FirefoxOptions options = new FirefoxOptions();
        options.addArguments("--width=1920");
        options.addArguments("--height=1080");
        WebDriver webDriver = new FirefoxDriver(options);
        webDriver.manage().window().maximize();
        webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
        webDriver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(45));
        driverThread.set(webDriver);
        waitThread.set(new WebDriverWait(webDriver, Duration.ofSeconds(20)));
        jsThread.set((JavascriptExecutor) webDriver);
    }

    @AfterClass
    public void tearDown() {
        if (getDriver() != null) {
            getDriver().quit();
            driverThread.remove();
            waitThread.remove();
            jsThread.remove();
        }
    }

    @AfterMethod
    public void captureScreenshotOnFailure(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            takeScreenshot(result.getName());
        }
    }

    protected WebDriver getDriver() { return driverThread.get(); }
    protected WebDriverWait getWait() { return waitThread.get(); }
    protected JavascriptExecutor getJs() { return jsThread.get(); }

    protected void loginToLinkedIn() {
        getDriver().get(ConfigReader.getBaseUrl() + "/login");
        WebElement emailField = getWait().until(
                ExpectedConditions.visibilityOfElementLocated(By.id("username")));
        emailField.clear();
        emailField.sendKeys(ConfigReader.getEmail());
        WebElement passwordField = getDriver().findElement(By.id("password"));
        passwordField.clear();
        passwordField.sendKeys(ConfigReader.getPassword());
        WebElement signInButton = getDriver().findElement(By.xpath("//button[@type='submit']"));
        signInButton.click();
        getWait().until(ExpectedConditions.urlContains("/feed"));
    }

    protected void takeScreenshot(String testName) {
        try {
            TakesScreenshot screenshot = (TakesScreenshot) getDriver();
            File src = screenshot.getScreenshotAs(OutputType.FILE);
            File dest = new File("screenshots/" + testName + "_" + System.currentTimeMillis() + ".png");
            dest.getParentFile().mkdirs();
            FileHandler.copy(src, dest);
            System.out.println("Screenshot saved: " + dest.getAbsolutePath());
        } catch (IOException e) {
            System.out.println("Failed to capture screenshot: " + e.getMessage());
        }
    }

    protected void scrollDownByPixels(int pixels) {
        getJs().executeScript("window.scrollBy(0," + pixels + ")");
    }

    protected void scrollUpByPixels(int pixels) {
        getJs().executeScript("window.scrollBy(0,-" + pixels + ")");
    }

    protected void scrollToElement(WebElement element) {
        getJs().executeScript("arguments[0].scrollIntoView({behavior:'smooth',block:'center'});", element);
    }

    protected void scrollToBottom() {
        getJs().executeScript("window.scrollTo(0,document.body.scrollHeight)");
    }

    protected void scrollToTop() {
        getJs().executeScript("window.scrollTo(0,0)");
    }

    protected String getParentWindowHandle() {
        return getDriver().getWindowHandle();
    }

    protected void switchToNewWindow(String parentHandle) {
        for (String handle : getDriver().getWindowHandles()) {
            if (!handle.equals(parentHandle)) {
                getDriver().switchTo().window(handle);
                break;
            }
        }
    }

    protected void switchToParentWindow(String parentHandle) {
        getDriver().switchTo().window(parentHandle);
    }

    protected void pause(int milliseconds) {
        try { Thread.sleep(milliseconds); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
    }
}
