import ge.tbcitacademy.data.Constants;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JSexecutor {
    WebDriver driver;
    @BeforeTest
    @Parameters("browser")
    public void setup(String browser){
        if (browser.equalsIgnoreCase(Constants.chromeName)){
            WebDriverManager.chromedriver().setup();
            ChromeOptions options = new ChromeOptions();
            options.addExtensions(new File(Constants.extensionPath));
            driver = new ChromeDriver(options);
        }else if (browser.equalsIgnoreCase(Constants.firefoxName)){
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();
        }else if (browser.equalsIgnoreCase(Constants.edgeName)){
            WebDriverManager.edgedriver().setup();
            driver = new EdgeDriver();
        }
        driver.manage().window().maximize();
    }

    @Test
    public void deleteTest(){
        driver.get(Constants.webDriverUniversityLink);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        WebElement elementToHover = driver.findElement(By.xpath("//li[contains(text(),'Practice magic')]"));
        Actions actions = new Actions(driver);
        actions.moveToElement(elementToHover).perform();
        WebElement deleteButton = driver.findElement(By.xpath("//li[text()=' Practice magic']//span"));
        js.executeScript("arguments[0].click();", deleteButton);
        WebDriverWait wait = new WebDriverWait(driver, 3);
        // ვავეითებთ რადგან დავრწმუნდეთ რომ ელემენტი ნამდვილად გაქრა
        wait.until(ExpectedConditions.invisibilityOf(elementToHover));
    }

    @Test
    public void scrollTest(){
        driver.get(Constants.techListicLink);
        List<WebElement> sectionNames = driver.findElements(By.xpath("//h3/span/parent::h3"));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        for (int i = 0; i < sectionNames.size(); i++){
            String sectionString = sectionNames.get(i).getText();
            // ხელით მომიწია იმ სექციების წაშლა რომლებშიც კოდის სექციები არ არის.
            if (sectionString.contains(Constants.techlistic)) sectionNames.remove(sectionNames.get(i));
            else if (sectionString.contains(Constants.webTableText)) sectionNames.remove(sectionNames.get(i));
        }
        List<WebElement> sectionCodes = driver.findElements(By.xpath("//div[@class='bg-black rounded-md mb-4']"));
        // ეს კოდის სექციები განსხვავებული იყო სხვებისგან ამიტომ ცალკე მომიწია წამოღება.
        WebElement secondSection = driver.findElement(By.xpath("//*[@id='post-body-1325137018292710854']/div[15]/div[1]"));
        WebElement lastSection = driver.findElement(By.xpath("//*[@id='post-body-1325137018292710854']/div[23]/div[1]"));
        // ერთი ზედმეტი სექცია წამოიღო
        sectionCodes.remove(1);
        // ასევე ერთერთ სექციაში იყოს კოდის ორი ვერსია ერთი ჯავაში დაწერილი მეორე პითონში, მაპში ორივეს ვერჩავსვამდი ამიტომ ერთი წავშალე
        sectionCodes.remove(4);
        sectionCodes.add(1, secondSection);
        sectionCodes.add(lastSection);
        sectionNames.remove(0);
        Map<String, String> codeExamples = new HashMap<>();
        // ვამოწმებ ზომები რომ ემთხვევა
        Assert.assertEquals(sectionCodes.size(), sectionNames.size());
        for (int i = 0; i < sectionCodes.size(); i++){
            // კოდის მაგალითებზე სქროლს აქ ვაკეთებ
            js.executeScript("arguments[0].scrollIntoView();", sectionCodes.get(i));
            codeExamples.put(sectionNames.get(i).getText(), sectionCodes.get(i).getText());
        }
        List<WebElement> popularLinkElements = driver.findElements(By.xpath("//a[text()='Appium Tutorial']/parent::span/parent::li/parent::ul/child::li//a"));
        Map<String, String> popularLinksMap = new HashMap<>();
        for (WebElement element : popularLinkElements) {
            popularLinksMap.put(element.getText(), element.getAttribute(Constants.hrefText));
        }
        js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
    }

    @Test
    public void anotherScrollTest(){
        driver.get(Constants.webDriverUniversityScrollingLink);
        WebElement entriesBox = driver.findElement(By.cssSelector("h1#zone2-entries"));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView();", entriesBox);
        String boxText = (String) js.executeScript("return arguments[0].textContent;", entriesBox);
        Assert.assertEquals(boxText, Constants.zeroEntries);
    }

    @AfterClass
    public void tearDown(){
        driver.close();
    }
}
