package master;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.*;
public class passwordreset {
  private WebDriver driver;
  private Map<String, Object> vars;
  JavascriptExecutor js;
  @Before
  public void setUp() {
	  System.setProperty("webdriver.edge.driver", ".\\tool\\msedgedriver.exe");
    driver = new EdgeDriver();
    js = (JavascriptExecutor) driver;
    vars = new HashMap<String, Object>();
  }
  @After
  public void tearDown() {
    driver.quit();
  }
  
  /*
  public void login() {
	    driver.get("http://localhost:8080/shop/index.jsp");
	    driver.manage().window().setSize(new Dimension(952, 1040));
	    driver.findElement(By.linkText("卖家登录")).click();
	    driver.findElement(By.id("username")).click();
	    driver.findElement(By.id("username")).sendKeys("admin");
	    driver.findElement(By.id("password")).click();
	    driver.findElement(By.id("password")).sendKeys("123456");
	    driver.findElement(By.cssSelector(".login-button")).click();  
  }
  */
  
  @Test
  public void check() {
	  driver.get("http://localhost:8080/shop/index.jsp");
	    driver.manage().window().setSize(new Dimension(955, 1040));
	    driver.findElement(By.linkText("卖家登录")).click();
	    driver.findElement(By.id("username")).click();
	    driver.findElement(By.id("username")).sendKeys("admin");
	    driver.findElement(By.id("password")).click();
	    driver.findElement(By.id("password")).sendKeys("123456");
	    driver.findElement(By.name("action")).click();
	    driver.findElement(By.name("oldPassword")).click();
	    driver.findElement(By.name("oldPassword")).sendKeys("123456");
	    driver.findElement(By.name("newPassword")).click();
	    driver.findElement(By.name("newPassword")).sendKeys("1234566");
	    driver.findElement(By.cssSelector("form:nth-child(12) > button")).click();
	    driver.findElement(By.linkText("返回首页")).click();
	    driver.findElement(By.linkText("卖家登录")).click();
	    driver.findElement(By.id("username")).click();
	    driver.findElement(By.id("username")).sendKeys("admin");
	    driver.findElement(By.id("password")).click();
	    driver.findElement(By.id("password")).click();
	    driver.findElement(By.id("password")).sendKeys("1234566");
	    driver.findElement(By.name("action")).click();
	    // 等待页面加载并验证跳转
        String expectedUrl = "http://localhost:8080/shop/admin.jsp"; // 替换为实际的跳转页面 URL
        String actualUrl = driver.getCurrentUrl();

        // 断言判断是否跳转到预期页面
        assertEquals("密码不正确", expectedUrl, actualUrl);
	    
}
}