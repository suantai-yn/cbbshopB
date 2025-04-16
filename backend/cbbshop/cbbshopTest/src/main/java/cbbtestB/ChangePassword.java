package cbbtestB;

import static org.junit.Assert.assertEquals;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ChangePassword {
    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeEach
    public void setUp() {
        System.setProperty("webdriver.edge.driver", "D:\\yanizhang\\cbb-shop\\msedgedriver.exe");
        driver = new EdgeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // 设置显式等待时间为10秒
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void changepassword() {
        driver.get("http://localhost:8086/");
        driver.manage().window().setSize(new Dimension(1010, 1040));

        // 登录操作
        driver.findElement(By.cssSelector("button:nth-child(2)")).click();
        driver.findElement(By.cssSelector(".input-group:nth-child(2) > input")).sendKeys("admin");
        driver.findElement(By.cssSelector(".input-group:nth-child(3) > input")).sendKeys("123456");
        driver.findElement(By.cssSelector("button:nth-child(3)")).click();

        // 等待页面加载完成
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("修改密码")));

        // 点击修改密码
        driver.findElement(By.linkText("修改密码")).click();

        // 等待修改密码页面加载完成
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("current-password")));

        // 输入当前密码和新密码
        driver.findElement(By.id("current-password")).sendKeys("1234567");
        driver.findElement(By.id("new-password")).sendKeys("031130");
        driver.findElement(By.cssSelector("button:nth-child(5)")).click();

        // 等待弹窗出现并验证弹窗文本
        wait.until(ExpectedConditions.alertIsPresent());
        assertThat(driver.switchTo().alert().getText(), is("修改密码失败: 密码修改失败: 旧密码不正确"));
    }
    @Test
    public void changepassword1() {
        driver.get("http://localhost:8086/");
        driver.manage().window().setSize(new Dimension(1010, 1040));

        // 登录操作
        driver.findElement(By.cssSelector("button:nth-child(2)")).click();
        driver.findElement(By.cssSelector(".input-group:nth-child(2) > input")).sendKeys("admin");
        driver.findElement(By.cssSelector(".input-group:nth-child(3) > input")).sendKeys("123456");
        driver.findElement(By.cssSelector("button:nth-child(3)")).click();

        // 等待页面加载完成
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("修改密码")));

        // 点击修改密码
        driver.findElement(By.linkText("修改密码")).click();

        // 等待修改密码页面加载完成
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("current-password")));

        // 输入当前密码和新密码
        driver.findElement(By.id("current-password")).sendKeys("123456");
        driver.findElement(By.id("new-password")).sendKeys("12345");
        driver.findElement(By.cssSelector("button:nth-child(5)")).click();

        // 等待弹窗出现并验证弹窗文本
        wait.until(ExpectedConditions.alertIsPresent());
        assertThat(driver.switchTo().alert().getText(), is("修改密码失败: 新密码必须至少6个字符。"));
    }
    @Test
    public void changepassword2() {
        driver.get("http://localhost:8086/");
        driver.manage().window().setSize(new Dimension(1010, 1040));

        // 登录操作
        driver.findElement(By.cssSelector("button:nth-child(2)")).click();
        driver.findElement(By.cssSelector(".input-group:nth-child(2) > input")).sendKeys("admin");
        driver.findElement(By.cssSelector(".input-group:nth-child(3) > input")).sendKeys("123456");
        driver.findElement(By.cssSelector("button:nth-child(3)")).click();

        // 等待页面加载完成
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("修改密码")));

        // 点击修改密码
        driver.findElement(By.linkText("修改密码")).click();

        // 等待修改密码页面加载完成
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("current-password")));

        // 输入当前密码和新密码
        driver.findElement(By.id("current-password")).sendKeys("123456");
        driver.findElement(By.id("new-password")).sendKeys("031130");
        driver.findElement(By.cssSelector("button:nth-child(5)")).click();

        // 等待弹窗出现并验证弹窗文本
        wait.until(ExpectedConditions.alertIsPresent());
        assertThat(driver.switchTo().alert().getText(), is("密码修改成功！"));
    }
    @Test
	public void changepassword3() {
	    driver.get("http://localhost:8086/");
	    driver.manage().window().setSize(new Dimension(1008, 1040));
	    driver.findElement(By.cssSelector("button:nth-child(2)")).click();
	    driver.findElement(By.cssSelector(".input-group:nth-child(2) > input")).click();
	    driver.findElement(By.cssSelector(".input-group:nth-child(2) > input")).sendKeys("admin");
	    driver.findElement(By.cssSelector(".input-group:nth-child(3) > input")).click();
	    driver.findElement(By.cssSelector(".input-group:nth-child(3) > input")).sendKeys("031130");
	    driver.findElement(By.cssSelector("button:nth-child(3)")).click();
	    
	 // 使用WebDriverWait等待页面加载完成
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlToBe("http://localhost:8086/seller-dashboard"));

        // 检查当前页面的URL是否与登录成功的URL匹配
        assertEquals("http://localhost:8086/seller-dashboard", driver.getCurrentUrl());
        
	  }
    
    
}