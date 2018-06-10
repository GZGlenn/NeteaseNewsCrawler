package com.glenn.test;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.xml.bind.Element;
import java.util.List;


public class TestWebDriver {

    public static void main(String[] args) throws InterruptedException {        // TODO Auto-generated method stub
        System.setProperty("webdriver.chrome.driver","./chromedriver");
        WebDriver driver=new ChromeDriver();
        driver.get("http://sports.163.com/");
//        driver.manage().window().maximize();
//        Thread.sleep(2000);

        JavascriptExecutor js = (JavascriptExecutor)driver;
        js.executeScript("scrollTo(0,10000)");//----------------------------------------------向下拉到底
        Thread.sleep(2000);


        WebElement addMore = driver.findElement(By.className("load_more_btn"));
        Actions action = new Actions(driver);
        action.click(addMore).build().perform();

        List<WebElement> webElementList = driver.findElements(By.className("data_row"));
        for (WebElement webElement : webElementList) {
            WebElement href = webElement.findElement(By.tagName("a"));
            System.out.println(href.getText());
        }

        driver.close();
    }


    public void testGetElement() {
        System.getProperties().setProperty("webdriver.chrome.driver",
                "./chromedriver");
        WebDriver driver = new ChromeDriver();
        driver.get("http://news.sina.com.cn/c/2015-07-04/023532071740.shtml");
        WebDriverWait wait = new WebDriverWait(driver,10);
        wait.until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver webDriver) {
                System.out.println("Searching ...");
                return webDriver.findElement(By.id("commentCount1")).getText().length() != 0;
            }
        });

        WebElement element = driver.findElement(By.id("commentCount1"));
        System.out.println("element="+element.getText());
    }

}
