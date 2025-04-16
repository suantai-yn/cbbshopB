package cbbshopTest;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPageTest {

    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void setUp() {
        System.setProperty("webdriver.edge.driver", "D:\\yanizhang\\cbb-shop\\msedgedriver.exe");
        driver = new EdgeDriver();

    }

    @Test
    public void loginseller() {
        driver.get("http://localhost:8086");
        driver.manage().window().setSize(new Dimension(1310, 992));
        driver.findElement(By.cssSelector("button:nth-child(1)")).click();
        driver.findElement(By.cssSelector(".input-group:nth-child(2) > input")).click();
        driver.findElement(By.cssSelector(".input-group:nth-child(2) > input")).sendKeys("admin");
        driver.findElement(By.cssSelector(".input-group:nth-child(3) > input")).click();
        driver.findElement(By.cssSelector(".input-group:nth-child(3) > input")).sendKeys("031130");
        driver.findElement(By.cssSelector("button:nth-child(3)")).click();
        {
          WebElement element = driver.findElement(By.cssSelector("button:nth-child(3)"));
          Actions builder = new Actions(driver);
          builder.moveToElement(element).perform();
        }
        driver.findElement(By.cssSelector(".page-wrapper")).click();
        assertEquals("Login failed: expected URL not matched", "http://localhost:8086/seller-dashboard", driver.getCurrentUrl());
    
    }
    @Test
    public void loginbuyer() {
        driver.get("http://localhost:8086");
        driver.manage().window().setSize(new Dimension(1310, 992));
        driver.findElement(By.cssSelector("button:nth-child(1)")).click();
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
	    
        {
          WebElement element = driver.findElement(By.cssSelector("button:nth-child(3)"));
          Actions builder = new Actions(driver);
          builder.moveToElement(element).perform();
        }
        driver.findElement(By.cssSelector(".page-wrapper")).click();
        assertEquals("Login failed: expected URL not matched", "http://localhost:8086/dashboard", driver.getCurrentUrl());
    
    }
   
    @After
    public void tearDown() {
        driver.quit();
    }
}