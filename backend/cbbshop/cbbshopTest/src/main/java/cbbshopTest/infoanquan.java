package cbbshopTest;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.JavascriptExecutor;
public class infoanquan{
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
  public void buygoods() {
    driver.get("http://localhost:8086/seller-dashboard/add-product");
    driver.manage().window().setSize(new Dimension(1089, 796));
    driver.findElement(By.cssSelector(".form-group:nth-child(1) > input")).click();
    driver.findElement(By.cssSelector(".form-group:nth-child(1) > input")).click();
    driver.findElement(By.cssSelector(".form-group:nth-child(1) > input")).sendKeys("瓷碗");
    driver.findElement(By.cssSelector(".form-group:nth-child(2) > input")).click();
    driver.findElement(By.cssSelector(".form-group:nth-child(2) > input")).sendKeys("100");
    driver.findElement(By.cssSelector(".form-group:nth-child(3) > input")).click();
    driver.findElement(By.cssSelector(".form-group:nth-child(3) > input")).sendKeys("10000");
    driver.findElement(By.cssSelector("select:nth-child(2)")).click();
    {
      WebElement dropdown = driver.findElement(By.cssSelector("select:nth-child(2)"));
      dropdown.findElement(By.xpath("//option[. = '电子产品']")).click();
    }
    driver.findElement(By.cssSelector(".form-group:nth-child(3) > input")).click();
    driver.findElement(By.cssSelector(".form-group:nth-child(3) > input")).sendKeys("100");
    driver.findElement(By.cssSelector("select:nth-child(3)")).click();
    driver.findElement(By.cssSelector("select:nth-child(2)")).click();
    {
      WebElement dropdown = driver.findElement(By.cssSelector("select:nth-child(2)"));
      dropdown.findElement(By.xpath("//option[. = '家居用品']")).click();
    }
    driver.findElement(By.cssSelector("select:nth-child(3)")).click();
    {
      WebElement dropdown = driver.findElement(By.cssSelector("select:nth-child(3)"));
      dropdown.findElement(By.xpath("//option[. = '厨房用品']")).click();
    }
    driver.findElement(By.cssSelector("button:nth-child(7)")).click();

  }
}
