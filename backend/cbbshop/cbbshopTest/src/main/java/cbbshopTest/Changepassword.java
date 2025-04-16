package cbbshopTest;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNot.not;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Alert;
import org.openqa.selenium.Keys;
import java.util.*;
import java.net.MalformedURLException;
import java.net.URL;
public class Changepassword{
    private WebDriver driver;
    JavascriptExecutor js;
    @Before
    public void setUp() {
        // 设置系统属性，告诉Selenium使用哪个WebDriver
        System.setProperty("webdriver.edge.driver", "D:\\yanizhang\\cbb-shop\\msedgedriver.exe");
        driver = new EdgeDriver();
    }
  @After
  public void tearDown() {
    driver.quit();
  }
  @Test
  public void changpassword() {
	  driver.get("http://localhost:8086/");
	    driver.manage().window().setSize(new Dimension(1151, 733));
	    driver.findElement(By.cssSelector("button:nth-child(2)")).click();
	    driver.findElement(By.cssSelector(".input-group:nth-child(2) > input")).click();
	    driver.findElement(By.cssSelector(".input-group:nth-child(2) > input")).sendKeys("admin");
	    driver.findElement(By.cssSelector(".input-group:nth-child(3) > input")).click();
	    driver.findElement(By.cssSelector(".input-group:nth-child(3) > input")).sendKeys("031130");
	    driver.findElement(By.cssSelector("button:nth-child(3)")).click();
	    
	    try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	    driver.findElement(By.linkText("修改密码")).click();
	    {
	      WebElement element = driver.findElement(By.linkText("修改密码"));
	      Actions builder = new Actions(driver);
	      builder.moveToElement(element).perform();
	    }
	    {
	      WebElement element = driver.findElement(By.tagName("body"));
	      Actions builder = new Actions(driver);
	      builder.moveToElement(element, 0, 0).perform();
	    }
	    driver.findElement(By.id("current-password")).click();
	    driver.findElement(By.id("current-password")).sendKeys("031130");
	    driver.findElement(By.id("new-password")).click();
	    driver.findElement(By.id("new-password")).sendKeys("123456");
	    driver.findElement(By.cssSelector("button:nth-child(5)")).click();
  }
}
