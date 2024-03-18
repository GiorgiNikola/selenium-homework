import ge.tbcitacademy.data.Constants;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.util.Set;
import java.util.stream.Collectors;

public class CookiesTest {
    WebDriver driver;
    @BeforeClass
    public void setup(){
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addExtensions(new File(Constants.extensionPath));
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
    }

    @Test
    public void filterCookies(){
        driver.get(Constants.techListicLink);
        Set<Cookie> cookies = driver.manage().getCookies();
        Cookie cookie = cookies.stream().filter(c -> c.getName().contains(Constants.activeTemplateText)).findFirst().get();
        Assert.assertTrue(cookie.getValue().contains(Constants.pubSiteText));
    }

    @Test
    public void injectCookie(){
        driver.get(Constants.techListicLink);
        // დავამატე სტატიკურად
        driver.manage().addCookie(new Cookie("myCookie1", "myValue1"));
        driver.manage().addCookie(new Cookie("myCookie2", "myValue2"));
        driver.manage().addCookie(new Cookie("myCookie3", "myValue3"));
        driver.manage().addCookie(new Cookie("myCookie4", "myValue4"));
        driver.manage().addCookie(new Cookie("myCookie5", "myValue5"));
        driver.manage().addCookie(new Cookie("myCookie6", "myValue6"));
        driver.manage().addCookie(new Cookie("myCookie7", "myValue7"));
        driver.manage().addCookie(new Cookie("myCookie8", "myValue8"));
        driver.manage().addCookie(new Cookie("myCookie9", "myValue9"));
        driver.manage().addCookie(new Cookie("myCookie10", "myValue10"));

        // ყველა ქუქი წამოვიღე
        Set<Cookie> allCookies = driver.manage().getCookies();
        // ვფილტრავ რომ დარჩეს მხოლოდ ჩემ მიერ დამატებული ქუქიები
        Set<Cookie> myCookies = allCookies.stream().filter(c -> c.getName().contains(Constants.myCookieText)).collect(Collectors.toSet());
        // გადავუყვები სეტს ვბეჭდავ ქუქის და შემდეგ ვშლი
        for (Cookie cookie : myCookies){
            System.out.println(cookie);
            driver.manage().deleteCookie(cookie);
        }
        // მომაქვს ქუქიები ხელახლა
        Set<Cookie> cookiesAfterDeletion = driver.manage().getCookies();

        // ვქმნი ფლეგს და ფორ ლუპით გადავუყვები იმ სეტს რომელშიც ჩემი ქუქიები ყრია
        // და ვადარებ ახლანდელ ქუქიებს თუ რომელიმე დაემთხვა ესეიგი არ წაიშალა და ფლეგი განახლდება
        // თუ ყველა წაშილია ფლეგი არ განახლდება და ასერტს წარმატებით გაივლის
        boolean flag = true;
        for (Cookie cookie : myCookies){
            if (cookiesAfterDeletion.contains(cookie)) {
                flag = false;
                break;
            }
        }
        Assert.assertTrue(flag);
        // ეს სიაუთი გიტის სტეპისთვის
        System.out.println("hotfix branch");
    }

    @Test
    public void autoCompleteTest(){
        driver.get(Constants.autoCompleteLink);
        WebElement inputField = driver.findElement(By.cssSelector("input#searchbox"));
        inputField.sendKeys("Can");
        WebDriverWait wait = new WebDriverWait(driver, 2);
        WebElement desiredField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[text()='Canada']")));
        desiredField.click();
    }

    @AfterClass
    public void tearDown(){
        driver.close();
    }
}
