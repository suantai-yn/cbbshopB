package cbbtestB;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginTest {
	private WebDriver driver;
    @BeforeEach
    public void setUp() {
        System.setProperty("webdriver.edge.driver", "D:\\yanizhang\\cbb-shop\\msedgedriver.exe");
        driver = new EdgeDriver();
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }
	@Test
	public void lOGIN1() {
	    driver.get("http://localhost:8086/");
	    driver.manage().window().setSize(new Dimension(1008, 1040));
	    driver.findElement(By.cssSelector("button:nth-child(2)")).click();
	    driver.findElement(By.cssSelector(".input-group:nth-child(2) > input")).click();
	    driver.findElement(By.cssSelector(".input-group:nth-child(2) > input")).sendKeys("zyn");
	    driver.findElement(By.cssSelector(".input-group:nth-child(3) > input")).click();
	    driver.findElement(By.cssSelector(".input-group:nth-child(3) > input")).sendKeys("123456");
	    driver.findElement(By.cssSelector("button:nth-child(3)")).click();
	    
	 // 使用WebDriverWait等待页面加载完成
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        wait.until(ExpectedConditions.urlToBe("http://localhost:8086/user-dashboard"));

        // 检查当前页面的URL是否与登录成功的URL匹配
        assertEquals("http://localhost:8086/user-dashboard", driver.getCurrentUrl());
        
	  }
	@Test
	public void lOGIN2() {
	    driver.get("http://localhost:8086/");
	    driver.manage().window().setSize(new Dimension(1008, 1040));
	    driver.findElement(By.cssSelector("button:nth-child(2)")).click();
	    driver.findElement(By.cssSelector(".input-group:nth-child(2) > input")).click();
	    driver.findElement(By.cssSelector(".input-group:nth-child(2) > input")).sendKeys("zyn1");
	    driver.findElement(By.cssSelector(".input-group:nth-child(3) > input")).click();
	    driver.findElement(By.cssSelector(".input-group:nth-child(3) > input")).sendKeys("123456");
	    driver.findElement(By.cssSelector("button:nth-child(3)")).click();
	    
	    try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	    
        try {
            WebElement element = driver.findElement(By.xpath("//*[@id='app']/div/div/div[2]/span"));
            assertTrue(element.isDisplayed(), "Element is not displayed");
        } catch (NoSuchElementException e) {
            assertTrue(false, "Element does not exist");
        }
	  }
	@Test
	public void lOGIN3() {
	    driver.get("http://localhost:8086/");
	    driver.manage().window().setSize(new Dimension(1008, 1040));
	    driver.findElement(By.cssSelector("button:nth-child(2)")).click();
	    driver.findElement(By.cssSelector(".input-group:nth-child(2) > input")).click();
	    driver.findElement(By.cssSelector(".input-group:nth-child(2) > input")).sendKeys("zyn");
	    driver.findElement(By.cssSelector(".input-group:nth-child(3) > input")).click();
	    driver.findElement(By.cssSelector(".input-group:nth-child(3) > input")).sendKeys("1234567");
	    driver.findElement(By.cssSelector("button:nth-child(3)")).click();
	    
	    try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	    
        try {
            WebElement element = driver.findElement(By.xpath("//*[@id=\"app\"]/div/div/div[2]/span"));
            assertTrue(element.isDisplayed(), "Element is not displayed");
        } catch (NoSuchElementException e) {
            assertTrue(false, "Element does not exist");
        }
        
	  }
}
