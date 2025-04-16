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
public class buyercheck {
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
	    driver.findElement(By.name("userName")).click();
	    driver.findElement(By.name("userName")).sendKeys("zyn");
	    driver.findElement(By.name("userContact")).sendKeys("11111111111");
	    driver.findElement(By.cssSelector("button")).click();
	    driver.findElement(By.linkText("卖家登录")).click();
	    driver.findElement(By.id("username")).click();
	    driver.findElement(By.id("username")).sendKeys("admin");
	    driver.findElement(By.id("password")).click();
	    driver.findElement(By.id("password")).sendKeys("1234566");
	    driver.findElement(By.cssSelector(".login-button")).click();
	    // 验证意向购买人表格是否存在
        WebElement buyerIntentTable = driver.findElement(By.cssSelector("table[border='1']"));
        assertNotNull("意向购买人表格不存在", buyerIntentTable);

        // 查找意向购买人行
        List<WebElement> buyerIntentRows = driver.findElements(By.cssSelector("table[border='1'] tr"));
        boolean hasBuyerIntent = buyerIntentRows.size() > 1; // 至少有标题行和一条意向购买人信息行

        // 断言至少存在一个意向购买人
        assertTrue("未找到意向购买人信息", hasBuyerIntent);
	    
}
}