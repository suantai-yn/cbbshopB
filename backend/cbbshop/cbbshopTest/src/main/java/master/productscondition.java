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

import java.util.*;import org.openqa.selenium.edge.EdgeDriver;

public class productscondition {

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
		    driver.manage().window().setSize(new Dimension(952, 1040));
		    driver.findElement(By.linkText("卖家登录")).click();
		    driver.findElement(By.id("username")).click();
		    driver.findElement(By.id("username")).sendKeys("admin");
		    driver.findElement(By.id("password")).click();
		    driver.findElement(By.id("password")).sendKeys("123456");
		    driver.findElement(By.cssSelector(".login-button")).click();
		    driver.findElement(By.name("action")).click();
		      

	        // 查找“下架商品”按钮
	        List<WebElement> unlistButtons = driver.findElements(By.cssSelector("button[name='action'][value='unlist']"));
	        boolean unlistButtonExists = !unlistButtons.isEmpty();
	        assertTrue("未找到\"下架商品\"按钮", unlistButtonExists);

	        // 查找“恢复商品”按钮
	        List<WebElement> restoreButtons = driver.findElements(By.cssSelector("button[name='action'][value='restore']"));
	        boolean restoreButtonExists = !restoreButtons.isEmpty();
	        assertTrue("未找到\"恢复商品\"按钮", restoreButtonExists);  
	}
}
