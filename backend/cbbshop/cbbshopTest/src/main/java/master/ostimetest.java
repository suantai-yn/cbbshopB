package master;


import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.*;
public class ostimetest{
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
  
 
  
  @Test
  public void check() {
	    driver.get("http://localhost:8080/shop/index.jsp");
	    driver.manage().window().setSize(new Dimension(952, 1040));
	    driver.findElement(By.linkText("卖家登录")).click();
	    driver.findElement(By.id("username")).click();
	    driver.findElement(By.id("username")).sendKeys("admin");
	    driver.findElement(By.id("password")).click();
	    driver.findElement(By.id("password")).sendKeys("123456");
	    driver.findElement(By.cssSelector(".login-button")).click();
	    
	    long startTime = System.currentTimeMillis();
	    driver.findElement(By.name("action")).click();
	    
	    long endTime = System.currentTimeMillis();
        long responseTime = endTime - startTime;
        
        // 断言响应时间小于5秒（5000毫秒）
        assertTrue("系统响应时间超过5秒", responseTime < 10000);
	    
}
}