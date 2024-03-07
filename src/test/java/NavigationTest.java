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

public class NavigationTest {
    WebDriver driver;
    @BeforeClass
    public void setup(){
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get(Constants.ultimateQALink);
    }

    @Test
    public void goToSuccessStoriesAndBack(){
        // აიდი შეიძლება შეიცვალოს ამიტომ ვიყენებ xpath-ს
        WebElement successLink = driver.findElement(By.xpath("//ul[@id='menu-main-menu']//a[normalize-space()='Success Stories']"));
        successLink.click();
        Assert.assertEquals(driver.getCurrentUrl(), Constants.successLink);
        driver.navigate().back();
        Assert.assertEquals(driver.getCurrentUrl(), Constants.ultimateQALink);
    }

    @AfterClass
    public void tearDown(){
        driver.close();
    }
}
