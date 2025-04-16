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
import java.util.concurrent.TimeUnit;
public class Changebuyer{
    private WebDriver driver;
    JavascriptExecutor js;
    @Before
    public void setUp() {
        System.setProperty("webdriver.edge.driver", "D:\\yanizhang\\cbb-shop\\msedgedriver.exe");
        driver = new EdgeDriver();
    }
  @After
  public void tearDown() {
    driver.quit();
  }
  @Test
  public void changebuyer() {

	    driver.get("http://localhost:8086/");
	    driver.manage().window().setSize(new Dimension(1310, 992));
	    driver.findElement(By.cssSelector("button:nth-child(2)")).click();
	    driver.findElement(By.cssSelector(".input-group:nth-child(2) > input")).click();
	    driver.findElement(By.cssSelector(".input-group:nth-child(2) > input")).sendKeys("zyn");
	    driver.findElement(By.cssSelector(".input-group:nth-child(3) > input")).click();
	    driver.findElement(By.cssSelector(".input-group:nth-child(3) > input")).sendKeys("123456");
	    driver.findElement(By.cssSelector("button:nth-child(3)")).click();
	    try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	    driver.findElement(By.linkText("修改个人信息")).click();
	    {
	      WebElement element = driver.findElement(By.linkText("修改个人信息"));
	      Actions builder = new Actions(driver);
	      builder.moveToElement(element).perform();
	    }
	    {
	      WebElement element = driver.findElement(By.tagName("body"));
	      Actions builder = new Actions(driver);
	      builder.moveToElement(element, 0, 0).perform();
	    }
	    driver.findElement(By.cssSelector("label:nth-child(1) > input")).click();
	    driver.findElement(By.cssSelector("label:nth-child(1) > input")).sendKeys("zyn");
	    driver.findElement(By.cssSelector("label:nth-child(2) > input")).click();
	    driver.findElement(By.cssSelector("label:nth-child(2) > input")).sendKeys("031130");
	    driver.findElement(By.cssSelector("form")).click();
	    driver.findElement(By.cssSelector("label:nth-child(3) > input")).click();
	    driver.findElement(By.cssSelector("label:nth-child(3) > input")).sendKeys("19357931594");
	    driver.findElement(By.cssSelector("label:nth-child(4) > input")).click();
	    driver.findElement(By.cssSelector("label:nth-child(4) > input")).sendKeys("浙江省白杨街道");
	    driver.findElement(By.cssSelector("button:nth-child(5)")).click();
	    try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	    assertThat(driver.switchTo().alert().getText(), is("用户信息更新成功！"));
  }
}
