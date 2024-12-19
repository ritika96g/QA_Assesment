package tests;

import com.selenium.project.utils.ConfigUtils;
import com.selenium.project.utils.DriverUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.TipTopPage;

import java.time.Duration;
import java.util.List;

public class TipTopTests {
    private static final Logger logger = LogManager.getLogger(TipTopTests.class);

    WebDriver driver;
    TipTopPage page;

    private static String name;
    private static String password;

    @BeforeClass
    public void setup() {
        logger.info("Initializing WebDriver...");
        driver = DriverUtils.getDriver();

        String baseUrl = ConfigUtils.getProperty("baseURL");
        logger.info("Navigating to base URL: {}", baseUrl);
        driver.get(baseUrl);

        logger.info("Initializing TipTopPage...");
        page = new TipTopPage(driver);
    }

    @Test(priority = 1)
    public void verifyDisabledInput() {
        logger.info("Verifying that the input field is disabled...");
        Assert.assertFalse(driver.findElement(page.disabledInput).isEnabled(), "Input should be disabled");
        logger.info("Disabled input field verification passed.");
    }

    @Test(priority = 2)
    public void verifyReadonlyInput() {
        logger.info("Verifying that the input field is read-only...");
        Assert.assertEquals(driver.findElement(page.readonlyInput1).getAttribute("readonly"), "true");
        logger.info("Read-only input field verification passed.");
    }

    @Test(priority = 3)
    public void verifyDropdownOptions() {
        logger.info("Verifying the dropdown options...");
        WebElement dropdown = driver.findElement(page.dropdown);
        Select select = new Select(dropdown);

        List<WebElement> allOptions = select.getOptions();
        int optionsCount = allOptions.size();
        logger.info("Dropdown contains {} options.", optionsCount);
        Assert.assertEquals(optionsCount, 8, "Dropdown should have 8 elements");
        logger.info("Dropdown options verification passed.");
    }

    @Test(priority = 4)
    public void verifySubmitDisabledWhenNameEmpty() {
        logger.info("Verifying that the submit button is disabled when the name field is empty...");
        Assert.assertFalse(driver.findElement(page.submitButton).isEnabled(), "Submit button should be disabled");
        logger.info("Submit button disabled state verification passed.");
    }

    @Test(priority = 5)
    public void verifySubmitEnabledWhenFieldsEntered() {
        name = "John";
        password = "password123";

        logger.info("Entering name: {}", name);
        driver.findElement(page.nameField).sendKeys(name);

        logger.info("Entering password: {}", password);
        driver.findElement(page.passwordField).sendKeys(password);

        logger.info("Verifying that the submit button is enabled...");
        Assert.assertTrue(driver.findElement(page.submitButton).isEnabled(), "Submit button should be enabled");
        logger.info("Submit button enabled state verification passed.");
    }

    @Test(priority = 6)
    public void verifyReceivedTextAfterSubmit() {
        logger.info("Submitting the form...");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(page.submitButton));
        element.click();

        logger.info("Verifying that the 'Received' text is displayed...");
        Assert.assertTrue(driver.findElement(page.receivedText).isDisplayed(), "'Received' text should be visible");
        logger.info("'Received' text verification passed.");
    }

    @Test(priority = 7)
    public void verifyDataPassedToURLOnSubmit() {
        logger.info("Verifying that the data is passed to the URL on form submission...");
        String currentUrl = driver.getCurrentUrl();
        logger.info("Current URL: {}", currentUrl);

        Assert.assertTrue(currentUrl.contains("name=" + name), "URL should contain the 'name' parameter with the correct value");
        Assert.assertTrue(currentUrl.contains("password=" + password), "URL should contain the 'password' parameter with the correct value");
        logger.info("Data passed to URL verification passed.");
    }

    @AfterClass
    public void tearDown() {
        logger.info("Quitting the WebDriver...");
        DriverUtils.quitDriver();
        logger.info("WebDriver quit successfully.");
    }
}
