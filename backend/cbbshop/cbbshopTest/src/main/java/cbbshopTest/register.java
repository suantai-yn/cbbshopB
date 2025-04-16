package cbbshopTest;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
public class register {
    private WebDriver driver;

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
  public void register() {
    driver.get("http://localhost:8086/");
    driver.manage().window().setSize(new Dimension(1168, 1023));
    driver.findElement(By.cssSelector("button:nth-child(2)")).click();
    driver.findElement(By.cssSelector("button:nth-child(3)")).click();
    {
      WebElement element = driver.findElement(By.cssSelector("button:nth-child(3)"));
      Actions builder = new Actions(driver);
      builder.moveToElement(element).perform();
    }
    {
      WebElement element = driver.findElement(By.tagName("body"));
      Actions builder = new Actions(driver);
      builder.moveToElement(element, 0, 0).perform();
    }
    driver.findElement(By.cssSelector("button:nth-child(2)")).click();
    {
      WebElement element = driver.findElement(By.cssSelector("button:nth-child(2)"));
      Actions builder = new Actions(driver);
      builder.moveToElement(element).perform();
    }
    driver.findElement(By.cssSelector(".input-group:nth-child(2) > input")).click();
    driver.findElement(By.cssSelector(".input-group:nth-child(2) > input")).sendKeys("张");
    driver.findElement(By.cssSelector(".input-group:nth-child(3) > input")).click();
    driver.findElement(By.cssSelector(".input-group:nth-child(3) > input")).sendKeys("031130");
    driver.findElement(By.cssSelector(".input-group:nth-child(4) > input")).click();
    driver.findElement(By.cssSelector(".input-group:nth-child(4) > input")).sendKeys("19357391594");
    driver.findElement(By.cssSelector(".input-group:nth-child(5) > input")).click();
    driver.findElement(By.cssSelector(".input-group:nth-child(5) > input")).sendKeys("家");
    driver.findElement(By.cssSelector("label:nth-child(1) > input")).click();
    driver.findElement(By.cssSelector(".input-group:nth-child(7) > input")).click();
    driver.findElement(By.cssSelector(".input-group:nth-child(7) > input")).sendKeys("你喜欢什么颜色");
    driver.findElement(By.cssSelector(".input-group:nth-child(8) > input")).click();
    driver.findElement(By.cssSelector(".input-group:nth-child(8) > input")).sendKeys("蓝色");
    driver.findElement(By.cssSelector("button:nth-child(1)")).click();
  }
  @Test
  public void register2() {
    driver.get("http://localhost:8086/");
    driver.manage().window().setSize(new Dimension(1168, 1023));
    driver.findElement(By.cssSelector("button:nth-child(2)")).click();
    driver.findElement(By.cssSelector("button:nth-child(3)")).click();
    {
      WebElement element = driver.findElement(By.cssSelector("button:nth-child(3)"));
      Actions builder = new Actions(driver);
      builder.moveToElement(element).perform();
    }
    {
      WebElement element = driver.findElement(By.tagName("body"));
      Actions builder = new Actions(driver);
      builder.moveToElement(element, 0, 0).perform();
    }
    driver.findElement(By.cssSelector("button:nth-child(2)")).click();
    {
      WebElement element = driver.findElement(By.cssSelector("button:nth-child(2)"));
      Actions builder = new Actions(driver);
      builder.moveToElement(element).perform();
    }
    driver.findElement(By.cssSelector(".input-group:nth-child(2) > input")).click();
    driver.findElement(By.cssSelector(".input-group:nth-child(2) > input")).sendKeys("Traveler2024ExploringWorld0");
    driver.findElement(By.cssSelector(".input-group:nth-child(3) > input")).click();
    driver.findElement(By.cssSelector(".input-group:nth-child(3) > input")).sendKeys("123456");
    driver.findElement(By.cssSelector(".input-group:nth-child(4) > input")).click();
    driver.findElement(By.cssSelector(".input-group:nth-child(4) > input")).sendKeys("19357391594");
    driver.findElement(By.cssSelector(".input-group:nth-child(5) > input")).click();
    driver.findElement(By.cssSelector(".input-group:nth-child(5) > input")).sendKeys("家");
    driver.findElement(By.cssSelector("label:nth-child(1) > input")).click();
    driver.findElement(By.cssSelector(".input-group:nth-child(7) > input")).click();
    driver.findElement(By.cssSelector(".input-group:nth-child(7) > input")).sendKeys("你喜欢什么颜色");
    driver.findElement(By.cssSelector(".input-group:nth-child(8) > input")).click();
    driver.findElement(By.cssSelector(".input-group:nth-child(8) > input")).sendKeys("蓝色");
    driver.findElement(By.cssSelector("button:nth-child(1)")).click();
  }
  @Test
  public void register3() {
    driver.get("http://localhost:8086/");
    driver.manage().window().setSize(new Dimension(1168, 1023));
    driver.findElement(By.cssSelector("button:nth-child(2)")).click();
    driver.findElement(By.cssSelector("button:nth-child(3)")).click();
    {
      WebElement element = driver.findElement(By.cssSelector("button:nth-child(3)"));
      Actions builder = new Actions(driver);
      builder.moveToElement(element).perform();
    }
    {
      WebElement element = driver.findElement(By.tagName("body"));
      Actions builder = new Actions(driver);
      builder.moveToElement(element, 0, 0).perform();
    }
    driver.findElement(By.cssSelector("button:nth-child(2)")).click();
    {
      WebElement element = driver.findElement(By.cssSelector("button:nth-child(2)"));
      Actions builder = new Actions(driver);
      builder.moveToElement(element).perform();
    }
    driver.findElement(By.cssSelector(".input-group:nth-child(2) > input")).click();
    driver.findElement(By.cssSelector(".input-group:nth-child(2) > input")).sendKeys("张*");
    driver.findElement(By.cssSelector(".input-group:nth-child(3) > input")).click();
    driver.findElement(By.cssSelector(".input-group:nth-child(3) > input")).sendKeys("123456");
    driver.findElement(By.cssSelector(".input-group:nth-child(4) > input")).click();
    driver.findElement(By.cssSelector(".input-group:nth-child(4) > input")).sendKeys("19357391594");
    driver.findElement(By.cssSelector(".input-group:nth-child(5) > input")).click();
    driver.findElement(By.cssSelector(".input-group:nth-child(5) > input")).sendKeys("浙江省杭州市白杨街道");
    driver.findElement(By.cssSelector("label:nth-child(1) > input")).click();
    driver.findElement(By.cssSelector(".input-group:nth-child(7) > input")).click();
    driver.findElement(By.cssSelector(".input-group:nth-child(7) > input")).sendKeys("你喜欢什么颜色");
    driver.findElement(By.cssSelector(".input-group:nth-child(8) > input")).click();
    driver.findElement(By.cssSelector(".input-group:nth-child(8) > input")).sendKeys("蓝色");
    driver.findElement(By.cssSelector("button:nth-child(1)")).click();
  }
  @Test
  public void register4() {
    driver.get("http://localhost:8086/");
    driver.manage().window().setSize(new Dimension(1168, 1023));
    driver.findElement(By.cssSelector("button:nth-child(2)")).click();
    driver.findElement(By.cssSelector("button:nth-child(3)")).click();
    {
      WebElement element = driver.findElement(By.cssSelector("button:nth-child(3)"));
      Actions builder = new Actions(driver);
      builder.moveToElement(element).perform();
    }
    {
      WebElement element = driver.findElement(By.tagName("body"));
      Actions builder = new Actions(driver);
      builder.moveToElement(element, 0, 0).perform();
    }
    driver.findElement(By.cssSelector("button:nth-child(2)")).click();
    {
      WebElement element = driver.findElement(By.cssSelector("button:nth-child(2)"));
      Actions builder = new Actions(driver);
      builder.moveToElement(element).perform();
    }
    driver.findElement(By.cssSelector(".input-group:nth-child(2) > input")).click();
    driver.findElement(By.cssSelector(".input-group:nth-child(2) > input")).sendKeys("张");
    driver.findElement(By.cssSelector(".input-group:nth-child(3) > input")).click();
    driver.findElement(By.cssSelector(".input-group:nth-child(3) > input")).sendKeys("031130");
    driver.findElement(By.cssSelector(".input-group:nth-child(4) > input")).click();
    driver.findElement(By.cssSelector(".input-group:nth-child(4) > input")).sendKeys("1935739159");
    driver.findElement(By.cssSelector(".input-group:nth-child(5) > input")).click();
    driver.findElement(By.cssSelector(".input-group:nth-child(5) > input")).sendKeys("浙江省杭州市白杨街道");
    driver.findElement(By.cssSelector("label:nth-child(1) > input")).click();
    driver.findElement(By.cssSelector(".input-group:nth-child(7) > input")).click();
    driver.findElement(By.cssSelector(".input-group:nth-child(7) > input")).sendKeys("你喜欢什么颜色");
    driver.findElement(By.cssSelector(".input-group:nth-child(8) > input")).click();
    driver.findElement(By.cssSelector(".input-group:nth-child(8) > input")).sendKeys("蓝色");
    driver.findElement(By.cssSelector("button:nth-child(1)")).click();
  }
  @Test
  public void register5() {
    driver.get("http://localhost:8086/");
    driver.manage().window().setSize(new Dimension(1168, 1023));
    driver.findElement(By.cssSelector("button:nth-child(2)")).click();
    driver.findElement(By.cssSelector("button:nth-child(3)")).click();
    {
      WebElement element = driver.findElement(By.cssSelector("button:nth-child(3)"));
      Actions builder = new Actions(driver);
      builder.moveToElement(element).perform();
    }
    {
      WebElement element = driver.findElement(By.tagName("body"));
      Actions builder = new Actions(driver);
      builder.moveToElement(element, 0, 0).perform();
    }
    driver.findElement(By.cssSelector("button:nth-child(2)")).click();
    {
      WebElement element = driver.findElement(By.cssSelector("button:nth-child(2)"));
      Actions builder = new Actions(driver);
      builder.moveToElement(element).perform();
    }
    driver.findElement(By.cssSelector(".input-group:nth-child(2) > input")).click();
    driver.findElement(By.cssSelector(".input-group:nth-child(2) > input")).sendKeys("张");
    driver.findElement(By.cssSelector(".input-group:nth-child(3) > input")).click();
    driver.findElement(By.cssSelector(".input-group:nth-child(3) > input")).sendKeys("031130");
    driver.findElement(By.cssSelector(".input-group:nth-child(4) > input")).click();
    driver.findElement(By.cssSelector(".input-group:nth-child(4) > input")).sendKeys("193573915941");
    driver.findElement(By.cssSelector(".input-group:nth-child(5) > input")).click();
    driver.findElement(By.cssSelector(".input-group:nth-child(5) > input")).sendKeys("浙江省杭州市白杨街道");
    driver.findElement(By.cssSelector("label:nth-child(1) > input")).click();
    driver.findElement(By.cssSelector(".input-group:nth-child(7) > input")).click();
    driver.findElement(By.cssSelector(".input-group:nth-child(7) > input")).sendKeys("你喜欢什么颜色");
    driver.findElement(By.cssSelector(".input-group:nth-child(8) > input")).click();
    driver.findElement(By.cssSelector(".input-group:nth-child(8) > input")).sendKeys("蓝色");
    driver.findElement(By.cssSelector("button:nth-child(1)")).click();
  }
  @Test
  public void register6() {
    driver.get("http://localhost:8086/");
    driver.manage().window().setSize(new Dimension(1168, 1023));
    driver.findElement(By.cssSelector("button:nth-child(2)")).click();
    driver.findElement(By.cssSelector("button:nth-child(3)")).click();
    {
      WebElement element = driver.findElement(By.cssSelector("button:nth-child(3)"));
      Actions builder = new Actions(driver);
      builder.moveToElement(element).perform();
    }
    {
      WebElement element = driver.findElement(By.tagName("body"));
      Actions builder = new Actions(driver);
      builder.moveToElement(element, 0, 0).perform();
    }
    driver.findElement(By.cssSelector("button:nth-child(2)")).click();
    {
      WebElement element = driver.findElement(By.cssSelector("button:nth-child(2)"));
      Actions builder = new Actions(driver);
      builder.moveToElement(element).perform();
    }
    driver.findElement(By.cssSelector(".input-group:nth-child(2) > input")).click();
    driver.findElement(By.cssSelector(".input-group:nth-child(2) > input")).sendKeys("张");
    driver.findElement(By.cssSelector(".input-group:nth-child(3) > input")).click();
    driver.findElement(By.cssSelector(".input-group:nth-child(3) > input")).sendKeys("031130");
    driver.findElement(By.cssSelector(".input-group:nth-child(4) > input")).click();
    driver.findElement(By.cssSelector(".input-group:nth-child(4) > input")).sendKeys("19357391594");
    driver.findElement(By.cssSelector(".input-group:nth-child(5) > input")).click();
    driver.findElement(By.cssSelector(".input-group:nth-child(5) > input")).sendKeys("123 Main Street, Apt 456, Springfield, OR 12345-6789, USA");
    driver.findElement(By.cssSelector("label:nth-child(1) > input")).click();
    driver.findElement(By.cssSelector(".input-group:nth-child(7) > input")).click();
    driver.findElement(By.cssSelector(".input-group:nth-child(7) > input")).sendKeys("你喜欢什么颜色");
    driver.findElement(By.cssSelector(".input-group:nth-child(8) > input")).click();
    driver.findElement(By.cssSelector(".input-group:nth-child(8) > input")).sendKeys("蓝色");
    driver.findElement(By.cssSelector("button:nth-child(1)")).click();
  }
  @Test
  public void register7() {
    driver.get("http://localhost:8086/");
    driver.manage().window().setSize(new Dimension(1168, 1023));
    driver.findElement(By.cssSelector("button:nth-child(2)")).click();
    driver.findElement(By.cssSelector("button:nth-child(3)")).click();
    {
      WebElement element = driver.findElement(By.cssSelector("button:nth-child(3)"));
      Actions builder = new Actions(driver);
      builder.moveToElement(element).perform();
    }
    {
      WebElement element = driver.findElement(By.tagName("body"));
      Actions builder = new Actions(driver);
      builder.moveToElement(element, 0, 0).perform();
    }
    driver.findElement(By.cssSelector("button:nth-child(2)")).click();
    {
      WebElement element = driver.findElement(By.cssSelector("button:nth-child(2)"));
      Actions builder = new Actions(driver);
      builder.moveToElement(element).perform();
    }
    driver.findElement(By.cssSelector(".input-group:nth-child(2) > input")).click();
    driver.findElement(By.cssSelector(".input-group:nth-child(2) > input")).sendKeys("张");
    driver.findElement(By.cssSelector(".input-group:nth-child(3) > input")).click();
    driver.findElement(By.cssSelector(".input-group:nth-child(3) > input")).sendKeys("12345");
    driver.findElement(By.cssSelector(".input-group:nth-child(4) > input")).click();
    driver.findElement(By.cssSelector(".input-group:nth-child(4) > input")).sendKeys("19357391594");
    driver.findElement(By.cssSelector(".input-group:nth-child(5) > input")).click();
    driver.findElement(By.cssSelector(".input-group:nth-child(5) > input")).sendKeys("家");
    driver.findElement(By.cssSelector("label:nth-child(1) > input")).click();
    driver.findElement(By.cssSelector(".input-group:nth-child(7) > input")).click();
    driver.findElement(By.cssSelector(".input-group:nth-child(7) > input")).sendKeys("你喜欢什么颜色");
    driver.findElement(By.cssSelector(".input-group:nth-child(8) > input")).click();
    driver.findElement(By.cssSelector(".input-group:nth-child(8) > input")).sendKeys("蓝色");
    driver.findElement(By.cssSelector("button:nth-child(1)")).click();
  }
}
