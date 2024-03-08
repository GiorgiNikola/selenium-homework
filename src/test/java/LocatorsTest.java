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
import java.util.List;

public class LocatorsTest {
    WebDriver driver;
    @BeforeClass
    public void setup(){
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @Test
    public void unorderedListTest(){
        driver.get(Constants.jqueryLink);
        WebElement aside = driver.findElement(By.xpath("//h3[contains(text(), 'Effects')]//parent::aside"));
        List<WebElement> liElements = aside.findElements(By.cssSelector("ul li"));
        List<WebElement> filteredLiElements = liElements.stream().filter(e -> e.getText().contains("o")).toList();
        filteredLiElements.parallelStream().forEach(e -> {
            WebElement element = e.findElement(By.tagName("a"));
            String hrefValue = element.getAttribute(Constants.hrefText);
            if (!hrefValue.contains(Constants.animateText)){
                System.out.println(hrefValue);
            }
        });
    }

    @Test
    public void buttonsTest(){
        driver.get(Constants.addRemoveElementsLink);
        WebElement addElement = driver.findElement(By.cssSelector("button[onclick='addElement()']"));
        for (int i = 0; i < 3; i++) addElement.click();
        WebElement deleteElement = driver.findElement(By.cssSelector("div#elements button:last-child"));
        Assert.assertEquals(deleteElement.getAttribute(Constants.classText), Constants.addedManuallyText);
        List<WebElement> deleteButtons = driver.findElements(By.cssSelector("button[class^='added-manually']"));
        WebElement lastDeleteElement = deleteButtons.get(deleteButtons.size() - 1);
        Assert.assertEquals(lastDeleteElement.getAttribute(Constants.onClickText), Constants.deleteElementText);
    }

    @Test
    public void challengingDomTest(){
        driver.get(Constants.challengingDomLink);
        List<WebElement> tableHeaders = driver.findElements(By.xpath("//th[text()='Lorem']/parent::tr/child::th"));
        int indexOfLorem = 0;
        for (int i = 0; i < tableHeaders.size(); i++){
            if (tableHeaders.get(i).getText().contains(Constants.loremText)) indexOfLorem = i;
        }
        List<WebElement> targetRow = driver.findElements(By.xpath("//td[text()='Apeirian9']/parent::tr/child::td"));
        System.out.println(targetRow.get(indexOfLorem).getText());
    }

    @AfterClass
    public void tearDown(){
        driver.close();
    }
}
