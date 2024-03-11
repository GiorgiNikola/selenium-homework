package util;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class Util {
    public static void universalSelector(Object element, String visibleText){
        if (element instanceof Select){
            ((Select) element).selectByVisibleText(visibleText);
        }else if (element instanceof WebElement){
            By locator = By.xpath("//*[contains(text(), '" + visibleText + "')]");
            ((WebElement) element).findElement(locator).click();
        }else throw new IllegalArgumentException("Illegal element is passed!");
    }
}
