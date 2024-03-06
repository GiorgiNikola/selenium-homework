import ge.tbcitacademy.data.Constants;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class WebElementTest {
    WebDriver driver;
    @BeforeClass
    public void setup(){
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get(Constants.dragAndDropLink);
    }

    @Test
    public void dimensionsTest(){
        WebElement elementA = driver.findElement(By.id("column-a"));
        WebElement elementB = driver.findElement(By.id("column-b"));
        Assert.assertEquals(elementA.getLocation().getY(), elementB.getLocation().getY());
        Assert.assertEquals(elementA.getAttribute("draggable"), Constants.trueText);
        Assert.assertEquals(elementB.getAttribute("draggable"), Constants.trueText);
    }

    @Test
    public void linkTest(){
        WebElement linkElement = driver.findElement(By.xpath("//a[@target='_blank']"));
        Assert.assertEquals(linkElement.getAttribute("href"), Constants.link);
    }

    @AfterClass
    public void tearDown(){
        driver.close();
    }
}
