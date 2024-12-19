package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class TipTopPage {
    WebDriver driver;

    // Locators
    public By disabledInput = By.xpath(".//input[@name='my-disabled']");
    public By readonlyInput1 = By.xpath(".//input[@value='Readonly input']");
    public By dropdown = By.xpath(".//*[@class = 'form-select']");
    public By dropdownOptions = By.xpath(".//select[@name='color']/option");
    public By nameField = By.xpath(".//input[@name='my-name']");
    public By passwordField = By.xpath(".//input[@name='my-password']");
    public By submitButton = By.xpath(".//button[@type='submit']");
    public By receivedText = By.xpath(".//*[text()='Received!']");

    // Constructor
    public TipTopPage(WebDriver driver) {
        this.driver = driver;
    }
}
