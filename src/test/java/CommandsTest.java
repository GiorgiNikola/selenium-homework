import ge.tbcitacademy.data.Constants;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;

public class CommandsTest {
    WebDriver driver;
    Wait<WebDriver> wait;
    @BeforeClass
    public void setup(){
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get(Constants.dynamicControlsLink);
        wait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(10))
                .pollingEvery(Duration.ofSeconds(1))
                .ignoring(Exception.class);
    }

    @Test
    public void buttonsTest(){
        WebElement enable = driver.findElement(By.xpath("//form[@id='input-example']/button"));
        enable.click();
        WebElement message = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//p[@id='message']")));
        WebElement input = driver.findElement(By.xpath("//form[@id='input-example']/input"));
        Assert.assertEquals(message.getText(), Constants.enableText);
        Assert.assertEquals(enable.getText(), Constants.disableBtn);
        input.sendKeys(Constants.tbcText);
    }

    @Test
    public void labelsTest(){
        WebElement heading = driver.findElement(By.xpath("//h4[normalize-space()='Dynamic Controls']"));
        WebElement description = driver.findElement(By.xpath("//p[contains(text(),'This example demonstrates when elements (e.g., che')]"));
        Assert.assertEquals(heading.getText(), Constants.headingText);
        Assert.assertEquals(description.getText(), Constants.descriptionText);
    }

    @AfterClass
    public void tearDown(){
        driver.close();
    }
}
