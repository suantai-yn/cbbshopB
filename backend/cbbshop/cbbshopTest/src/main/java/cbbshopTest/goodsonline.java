package cbbshopTest;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.is;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.JavascriptExecutor;

public class goodsonline {
    private WebDriver driver;
    private JavascriptExecutor js; // 声明JavascriptExecutor对象

    @Before
    public void setUp() {
        System.setProperty("webdriver.edge.driver", "D:\\yanizhang\\cbb-shop\\msedgedriver.exe");
        driver = new EdgeDriver();
        js = (JavascriptExecutor) driver; // 初始化js对象
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void buygoods() {
        driver.get("http://localhost:8086/");
        driver.manage().window().setSize(new Dimension(1300, 1300));
        driver.findElement(By.cssSelector("button:nth-child(2)")).click();
        driver.findElement(By.cssSelector(".input-group:nth-child(2) > input")).click();
        driver.findElement(By.cssSelector(".input-group:nth-child(2) > input")).sendKeys("admin");
        driver.findElement(By.cssSelector(".input-group:nth-child(3) > input")).click();
        driver.findElement(By.cssSelector(".input-group:nth-child(3) > input")).sendKeys("123456");
        driver.findElement(By.cssSelector("button:nth-child(3)")).click();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        driver.findElement(By.linkText("发布商品")).click();
        {
            WebElement element = driver.findElement(By.linkText("发布商品"));
            Actions builder = new Actions(driver);
            builder.moveToElement(element).perform();
        }
	    try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
        {
            WebElement element = driver.findElement(By.tagName("body"));
            Actions builder = new Actions(driver);
            builder.moveToElement(element, 0, 0).perform();
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        {
            WebElement element = driver.findElement(By.id("w-e-textarea-1"));
            js.executeScript("if(arguments[0].contentEditable === 'true') {arguments[0].innerText = '<p id=\"w-e-element-3\" data-slate-node=\"element\" data-slate-inline=\"false\"><span id=\"w-e-text-4\" data-slate-node=\"text\"><span data-slate-leaf=\"true\"><span data-slate-zero-width=\"n\" data-slate-length=\"0\">﻿<br></span></span></span></p>'}", element);
        }
        driver.findElement(By.cssSelector(".form-group:nth-child(1) > input")).click();
        driver.findElement(By.cssSelector(".form-group:nth-child(1) > input")).sendKeys("大红色秋衣");
        driver.findElement(By.cssSelector(".form-group:nth-child(2) > input")).click();
        driver.findElement(By.cssSelector(".form-group:nth-child(2) > input")).sendKeys("100");
        driver.findElement(By.cssSelector(".form-group:nth-child(3) > input")).click();
        driver.findElement(By.cssSelector(".form-group:nth-child(3) > input")).sendKeys("200");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        driver.findElement(By.cssSelector("select:nth-child(2)")).click();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        {
            WebElement dropdown = driver.findElement(By.cssSelector("select:nth-child(2)"));
            dropdown.findElement(By.xpath("//*[@id=\"app\"]/div/div[2]/div/form/div[4]/select[1]/option[2]")).click();
        }
        
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        driver.findElement(By.cssSelector("select:nth-child(2)")).click();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        {
            WebElement dropdown = driver.findElement(By.cssSelector("select:nth-child(2)"));
            dropdown.findElement(By.xpath("//*[@id=\"app\"]/div/div[2]/div/form/div[4]/select[1]/option[1]")).click();
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        driver.findElement(By.cssSelector("select:nth-child(3)")).click();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        {
            WebElement dropdown = driver.findElement(By.cssSelector("select:nth-child(3)"));
            dropdown.findElement(By.xpath("//*[@id=\"app\"]/div/div[2]/div/form/div[4]/select[2]/option")).click();
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        WebElement fileInput = driver.findElement(By.cssSelector(".form-group:nth-child(5) > input"));
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
     
     fileInput.sendKeys("D:\\ecli\\cbb_shop\\docs\\测试\\goods\\秋衣.jpg");
     try {
         Thread.sleep(2000);
     } catch (InterruptedException e) {
         e.printStackTrace();
     }
        driver.findElement(By.id("w-e-textarea-1")).click();
        
        {
            WebElement element = driver.findElement(By.id("w-e-textarea-1"));
            js.executeScript("if(arguments[0].contentEditable === 'true') {arguments[0].innerText = '这是一件漂亮的秋衣'}", element);
        }
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        WebElement element = driver.findElement(By.cssSelector("button:nth-child(7)"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
	    try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
        assertThat(driver.switchTo().alert().getText(), is("商品发布成功！"));
    }
}