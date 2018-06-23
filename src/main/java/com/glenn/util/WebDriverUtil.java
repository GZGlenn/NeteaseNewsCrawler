package com.glenn.util;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;

import java.util.ArrayList;
import java.util.List;

public class WebDriverUtil {

//    private String driverPath = WebDriverUtil.class.getResource("/chromedriver").getPath();
    private String driverPath = "./chromedriver";

    private String driverType = "webdriver.chrome.driver";

    private int retryNum = 3;

    private WebDriver driver;

    private JavascriptExecutor js;

    public WebDriverUtil(){
        System.setProperty(driverType, driverPath);
        ChromeOptions options = new ChromeOptions();
        options.addArguments("headless");   //无界面参数
        options.addArguments("no-sandbox"); //禁用沙盒 就是被这个参数搞了一天
        driver = new ChromeDriver(options);
        js = (JavascriptExecutor)driver;

    }

    public WebDriverUtil(String driverPath) {
        this.driverPath = driverPath;
        System.setProperty(driverType, driverPath);
        ChromeOptions options = new ChromeOptions();
        options.addArguments("headless");   //无界面参数
        options.addArguments("no-sandbox"); //禁用沙盒 就是被这个参数搞了一天
        driver = new ChromeDriver(options);
        js = (JavascriptExecutor)driver;
    }

    public void setRetryNum(int num) {
        this.retryNum = num;
    }

    public int getRetryNum() {
        return retryNum;
    }

    public boolean get(String url)  {
        boolean isSuccess = false;
        int tryNum = 0;
        do {
            try {
                driver.get(url);
                isSuccess = true;
            } catch (Exception e) {
                tryNum++;
            }
        } while (tryNum <= retryNum && !isSuccess);

        return isSuccess;
    }

    public boolean maxwindow() {
        boolean isSuccess = false;

        int num = 0;
        while (num < retryNum && !isSuccess) {
            try {
                driver.manage().window().maximize();
                Thread.sleep(2000);
                isSuccess = true;
            } catch (InterruptedException exception) {
                num++;
            }
        }

        return isSuccess;
    }

    public boolean scrollEnd() {
        return scroll("document.body.scrollHeight");
    }

    public boolean scrollHome() {
        return scroll("0");
    }

    public boolean scrollToElement(WebElement element) {
        return scroll("" + element.getLocation().y);
    }

    private boolean scroll(String endPos) {
        boolean isSuccess = false;

        int num = 0;
        while (num < retryNum && !isSuccess) {
            try {
                js.executeScript("scrollTo(0,"+ endPos + ")");
                Thread.sleep(2000);
                isSuccess = true;
            } catch (InterruptedException exception) {
                num++;
            }
        }

        return isSuccess;
    }

    public boolean click(String buttonName) {
        boolean isSuccess = false;

        int num = 0;
        while (num < retryNum && !isSuccess) {
            try {
                WebElement addMore = driver.findElement(By.className(buttonName));
                Actions action = new Actions(driver);
                action.moveToElement(addMore).click(addMore).build().perform();
                Thread.sleep(2000);
                isSuccess = true;
            } catch (InterruptedException exception) {
                num++;
            }
        }

        return isSuccess;
    }

    public void quit() {
        driver.quit();
    }

    public void close() {
        driver.close();
    }

    public List<WebElement> findElementsByClass(String name) {
        return driver.findElements(By.className(name));
    }

    public List<WebElement> findElementsById(String name) {
        return driver.findElements(By.id(name));
    }

    public WebElement findElementByClass(String name) {
        return  driver.findElement(By.className(name));
    }

    public WebElement findElementById(String name) {
        return  driver.findElement(By.id(name));
    }


    public static void main(String[] args) {
        WebDriverUtil webDriverUtil = new WebDriverUtil();
        webDriverUtil.get("http://news.163.com/");

        webDriverUtil.maxwindow();
        webDriverUtil.scrollEnd();
        List<WebElement> elements = webDriverUtil.findElementsByClass("na_pic");
        webDriverUtil.scrollToElement(elements.get(elements.size() - 1));

        webDriverUtil.click("load_more_btn");
        webDriverUtil.quit();

    }
}
