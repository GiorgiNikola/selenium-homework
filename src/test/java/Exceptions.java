import ge.tbcitacademy.data.Constants;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.MoveTargetOutOfBoundsException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.io.File;
import java.util.Set;

public class Exceptions {
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
    public void noSuchElementExceptionTest(){
        driver.get(Constants.testAutoLink);
        WebElement addButton = driver.findElement(By.cssSelector("button#add_btn"));
        addButton.click();
        try {
            WebElement inputField = driver.findElement(By.cssSelector("div#row2 input.input-field"));

            //დაკლიკვის შემდეგ დრო სჭირდება რომ ელემენტი გამოჩნდეს, ამიტომ უბრალოდ დავავეითებთ, სოლუშენი:
            //WebDriverWait wait = new WebDriverWait(driver, 5);
            //WebElement inputField = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div#row2 input.input-field")));
        }catch (NoSuchElementException e){
            System.out.println(Constants.exceptionText + e.getMessage());
        }
    }

    @Test
    public void timeoutExceptionTest(){
        driver.get(Constants.testAutoLink);
        WebElement addButton = driver.findElement(By.cssSelector("button#add_btn"));
        addButton.click();
        WebDriverWait wait = new WebDriverWait(driver, 2);
        try {
            WebElement inputField = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div#row2 input.input-field")));
            inputField.sendKeys(Constants.accessGranted);

            // უფრო დიდი ხანია საჭირო დავეითება ამიტომ უბრალოდ წამებია გასაზრდელი.
            //WebDriverWait wait = new WebDriverWait(driver, 5);
        }catch (TimeoutException e){
            System.out.println(Constants.exceptionText + e.getMessage());
        }
    }

    @Test
    public void noSuchFrameExceptionTest(){
        driver.get(Constants.nestedFramesLink);
        try {
            driver.switchTo().frame("frame-middle");

            //მიდელ ფრეიმი ჩანესთილია ტოპ ფრეიმში ამიტომ ჯერ ტოპ ფრეიმზე უნდა გადავიდეთ და შემდეგ მიდლ ფრეიმზე.
            //driver.switchTo().frame("frame-top");
        }catch (NoSuchFrameException e){
            System.out.println(Constants.exceptionText + e.getMessage());
        }
    }

    @Test
    public void noSuchWindowExceptionTest(){
        driver.get(Constants.windowsLink);
        WebElement clickButton = driver.findElement(By.cssSelector("a[href='/windows/new']"));
        WebElement seleniumLink = driver.findElement(By.xpath("//a[text()='Elemental Selenium']"));
        seleniumLink.click();
        clickButton.click();
        Set<String> windows = driver.getWindowHandles();
        try {
            driver.switchTo().window("w");
        }catch (NoSuchWindowException e) {
            System.out.println(Constants.exceptionText + e.getMessage());
        }
        // ერორის გამოსასწორებლად ვინდოუს უნდა გადავცეთ ვალიდური სტრინგი.
        //for (String wind : windows){
        //    driver.switchTo().window(wind);
        //}
    }

    @Test
    public void staleElementReferenceExceptionTest(){
        driver.get(Constants.dynamicControlsLink);
        WebElement checkBox = driver.findElement(By.cssSelector("form#checkbox-example input"));
        WebElement removeButton = driver.findElement(By.cssSelector("form#checkbox-example button"));
        removeButton.click();
        WebDriverWait wait = new WebDriverWait(driver, 6);
        wait.until(ExpectedConditions.invisibilityOf(checkBox));
        try {
            checkBox.click();

            // რემუვ ბათონზე დაკლიკვიდან ცოტა ხანში ჩეკბოქსი ქრება რაც იწვეს ექსეფშენს.
            // შემდეგ რემუვ ბათონი ხდება ადდ ბათონი რომლის დაჭერის შემდეგაც ისევ ბრუნდება ჩეკბოქსი.
        }catch (StaleElementReferenceException e){
            System.out.println(Constants.exceptionText + e.getMessage());
        }
    }

    @Test
    public void noAlertPresentExceptionTest(){
        driver.get(Constants.anotherAlertsLink);
        WebElement alertButton = driver.findElement(By.cssSelector("button[onclick='jsAlert()']"));
        // ალერტს ჯერ უნდა დავაკლიკოთ და შემდეგ დავაექსეფთოთ:
        // alertButton.click();
        try{
            driver.switchTo().alert().accept();
        }catch (NoAlertPresentException e){
            System.out.println(Constants.exceptionText + e.getMessage());
        }
    }

    @Test
    public void moveTargetOutOfBoundsExceptionTest(){
        driver.get(Constants.infiniteScrollLink);
        By elementLocator = By.cssSelector("div.infinite-scroll");
        try {
            Actions actions = new Actions(driver);
            actions.moveByOffset(100000, 100000).perform();

            // სოლუშენი არის ის რომ შევცვალოთ xOffset da yOffset მნიშვნელობები საიტისთვის ვალიდური მნიშვნელობებზე
        } catch (MoveTargetOutOfBoundsException e) {
            System.out.println(Constants.exceptionText + e.getMessage());
        }
    }

    @Test
    public void unhandledAlertExceptionTest(){
        driver.get(Constants.anotherAlertsLink);
        WebElement alertButton = driver.findElement(By.cssSelector("button[onclick='jsAlert()']"));
        alertButton.click();
        //სანამ ამ ალერტს არ დავჰენდლავთ მეორე ბათონს ვერ დავაჭერთ
        //driver.switchTo().alert().accept();
        try {
            WebElement confirmButton = driver.findElement(By.cssSelector("button[onclick='jsConfirm()']"));
            confirmButton.click();
        }catch (UnhandledAlertException e){
            System.out.println(Constants.exceptionText + e.getMessage());
        }

    }


    @AfterClass
    public void tearDown(){
        driver.quit();
    }
}
