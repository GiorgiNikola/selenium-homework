import ge.tbcitacademy.data.Constants;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import util.Util;

public class FormsTest {
    WebDriver driver;
    @BeforeClass
    public void setup(){
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @Test
    public void customDropDownTest(){
        driver.get(Constants.dropDownLink);
        WebElement element = driver.findElement(By.cssSelector("a[href='index2.html']"));
        element.click();
        WebElement dropDown = driver.findElement(By.cssSelector("ul.dropdown"));
        WebElement dropDownToggle = driver.findElement(By.cssSelector("div.wrapper-dropdown-2"));
        Assert.assertFalse(dropDown.isDisplayed());
        dropDownToggle.click();
        Assert.assertTrue(dropDown.isDisplayed());
        Util.universalSelector(dropDown, Constants.githubText);
    }

    @Test
    public void nativeDropDownTest(){
        driver.get(Constants.registerLink);
        WebElement radioButton = driver.findElement(By.cssSelector("input[value='male']"));
        if (!radioButton.isSelected()) radioButton.click();
        Select modelSelect = new Select(driver.findElement(By.cssSelector("select[name='model']")));
        Util.universalSelector(modelSelect, Constants.modelVisibleText);
        driver.findElement(By.cssSelector("input[value='First Name']")).sendKeys(Constants.firstName);
        driver.findElement(By.cssSelector("input[value='Last Name']")).sendKeys(Constants.lastName);
        driver.findElement(By.cssSelector("input[value='Address1']")).sendKeys(Constants.userAddress);
        driver.findElement(By.cssSelector("input[value='Address2']")).sendKeys(Constants.userAddress);
        driver.findElement(By.cssSelector("input[value='City']")).sendKeys(Constants.userCity);
        driver.findElement(By.cssSelector("input[value='Contact1']")).sendKeys(Constants.userPhone);
        driver.findElement(By.cssSelector("input[value='Contact2']")).sendKeys(Constants.userPhone);
        WebElement register = driver.findElement(By.cssSelector("input[value='Register']"));
        register.click();
        driver.switchTo().alert().accept();
    }

    @AfterClass
    public void tearDown(){
        driver.close();
    }
}
