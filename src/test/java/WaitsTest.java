import ge.tbcitacademy.data.Constants;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class WaitsTest {
    WebDriver driver;
    WebDriverWait wait;
    @BeforeClass
    public void setup(){
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver,10);
    }

    @Test
    public void waitForDisappearance(){
        driver.get(Constants.dynamicControlsLink);
        WebElement enable = driver.findElement(By.cssSelector("form#input-example button"));
        enable.click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loading")));
        Assert.assertEquals(enable.getText(), Constants.disableBtn);
        WebElement inputField = driver.findElement(By.cssSelector("form#input-example button"));
        inputField.sendKeys(Constants.accessGranted);
    }

    @Test
    public void waitForText(){
        driver.get(Constants.progressBarLink);
        WebElement startStopButton = driver.findElement(By.id("startStopButton"));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,150)", "");
        startStopButton.click();
        WebElement progressBar = driver.findElement(By.id("progressBar"));
        wait.until(ExpectedConditions.textToBePresentInElement(progressBar, Constants.hundredPercent));
        System.out.println(Constants.hundredPercent);
    }

    @AfterClass
    public void tearDown(){
        driver.close();
    }
}
