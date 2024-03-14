package util;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import java.util.List;

public class Util {
    public static void universalSelector(Object element, String visibleText){
        if (element instanceof Select){
            ((Select) element).selectByVisibleText(visibleText);
        }else if (element instanceof WebElement){
            By locator = By.xpath("//*[contains(text(), '" + visibleText + "')]");
            ((WebElement) element).findElement(locator).click();
        }else throw new IllegalArgumentException("Illegal element is passed!");
        System.out.println("I AM A FIX");
    }


    // ელემენტს ვპოულობ ტექსტის მიხედვით თუ დამეთხვა ვპრინტავ.
    public static void getTableElementAsText(WebDriver driver, String tableXPath, String text) {
        WebElement table = driver.findElement(By.xpath(tableXPath));
        List<WebElement> tableRows = table.findElements(By.tagName("tr"));
        for (WebElement row : tableRows) {
            List<WebElement> rowEntries = row.findElements(By.xpath(".//th | .//td"));
            for (WebElement entry : rowEntries) {
                String elementText = entry.getText();
                if (elementText.equals(text)) {
                    System.out.println(elementText);
                    return;
                }
            }
        }
        throw new NoSuchElementException("Element with text '" + text + "' not found in the table.");
    }
}
