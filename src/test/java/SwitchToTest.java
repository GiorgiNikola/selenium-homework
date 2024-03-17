import ge.tbcitacademy.data.Constants;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;

public class SwitchToTest {
    WebDriver driver;
    @BeforeClass
    public void setup(){
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addExtensions(new File("src/Extensions/Adblock.crx"));
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
    }

    @Test
    public void framesTest(){
        driver.get(Constants.iFrameLink);
        WebElement iFrame = driver.findElement(By.cssSelector("iframe[title='Rich Text Area']"));
        driver.switchTo().frame(iFrame);
        WebDriverWait wait = new WebDriverWait(driver, 5);
        // ელემენტს ზოგ გაშვებაზე პოულობდა ზოგზე ვერა, როგორც მივხვდი ჩატვირთვას ვერ ასწრებდა და ვეითმა გამოასწორა ეს პრობლემა.
        WebElement inputField = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//p[text()='Your content goes here.']")));
        inputField.clear();
        inputField.sendKeys(Constants.hereGoesText);
        driver.switchTo().defaultContent();
        WebElement alignCenterButton = driver.findElement(By.cssSelector("button[title='Align center']"));
        alignCenterButton.click();
    }

    @Test
    public void alertsTest(){
        driver.get(Constants.alertsLink);
        WebElement alertButton = driver.findElement(By.cssSelector("button#alertButton"));
        alertButton.click();
        driver.switchTo().alert().accept();
    }

    @AfterClass
    public void tearDown(){
        driver.close();
    }
}
