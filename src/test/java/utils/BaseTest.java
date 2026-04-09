package utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.time.Duration;

/**
 * Base test class that all test classes extend.
 * - Sets up Firefox WebDriver with GeckoDriver (auto-downloaded)
 * - Uses ThreadLocal for parallel test execution safety
 * - Provides a login helper for tests that need authentication
 */
public class BaseTest {

    // ThreadLocal ensures each parallel thread gets its own WebDriver instance
    private static final ThreadLocal<WebDriver> driverThread = new ThreadLocal<>();
    private static final ThreadLocal<WebDriverWait> waitThread = new ThreadLocal<>();

    @BeforeMethod
    public void setUp() {
        // WebDriverManager auto-downloads the correct GeckoDriver
        WebDriverManager.firefoxdriver().setup();

        FirefoxOptions options = new FirefoxOptions();
        // Uncomment for headless mode (CI/CD or screen recording fallback):
        // options.addArguments("--headless");
        options.addArguments("--width=1920");
        options.addArguments("--height=1080");

        WebDriver webDriver = new FirefoxDriver(options);
        webDriver.manage().window().maximize();
        webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        webDriver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));

        driverThread.set(webDriver);
        waitThread.set(new WebDriverWait(webDriver, Duration.ofSeconds(15)));
    }

    @AfterMethod
    public void tearDown() {
        if (getDriver() != null) {
            getDriver().quit();
            driverThread.remove();
            waitThread.remove();
        }
    }

    /** Get the WebDriver for the current thread */
    protected WebDriver getDriver() {
        return driverThread.get();
    }

    /** Get the WebDriverWait for the current thread */
    protected WebDriverWait getWait() {
        return waitThread.get();
    }

    /**
     * Helper: Logs into LinkedIn using credentials from config.properties.
     * Call this at the start of any test that needs an authenticated session.
     */
    protected void loginToLinkedIn() {
        getDriver().get(ConfigReader.getBaseUrl() + "/login");

        WebElement emailField = getWait().until(
                ExpectedConditions.visibilityOfElementLocated(By.id("username"))
        );
        emailField.clear();
        emailField.sendKeys(ConfigReader.getEmail());

        WebElement passwordField = getDriver().findElement(By.id("password"));
        passwordField.clear();
        passwordField.sendKeys(ConfigReader.getPassword());

        WebElement signInButton = getDriver().findElement(By.xpath("//button[@type='submit']"));
        signInButton.click();

        // Wait for feed page to load (confirms successful login)
        getWait().until(ExpectedConditions.urlContains("/feed"));
    }
}
