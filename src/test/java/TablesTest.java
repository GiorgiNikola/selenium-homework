import ge.tbcitacademy.data.Constants;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import util.Util;

import java.io.File;

public class TablesTest {
    WebDriver driver;
    @BeforeClass
    public void setup(){
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addExtensions(new File("C:/Users/giorg/OneDrive/Desktop/TBC-Test-Automation-course/Extentions/Adblock.crx"));
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
    }


    // აქ იმ თეიბლების ტესტია რომელიც დავალების პირობაში იყო.
    @Test
    public void tablesTest(){
        JavascriptExecutor js = (JavascriptExecutor) driver;
        driver.get(Constants.techListicLink);
        js.executeScript("window.scrollBy(0,300)", "");
        Util.getTableElementAsText(driver, "//table[@id='customers']", Constants.helenBennettText);
        js.executeScript("window.scrollBy(0,200)", "");
        Util.getTableElementAsText(driver, "//table[@class='tsc_table_s13']", Constants.mecca);
        driver.get(Constants.techCanvasslink);
        Util.getTableElementAsText(driver, "//table[@id='t01']", Constants.baleno);
        driver.get(Constants.challengingDomLink);
        Util.getTableElementAsText(driver,"//div[@class='large-10 columns']//table", Constants.consequuntur7Text);
    }

    // აქ იმ ჩემ მიერ ნაპოვნ თეიბლებზე გავტესტე.
    @Test
    public void myTableTest(){
        driver.get(Constants.cosmoCodeLink);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,500)", "");
        Util.getTableElementAsText(driver, "//table[@id='countries']", Constants.algerianDinar);
        driver.get(Constants.qavBoxLink);
        Util.getTableElementAsText(driver,"//table[@id='table02']",Constants.sanFranciscoText);
    }

    @AfterClass
    public void tearDown(){
        driver.close();
    }
}
