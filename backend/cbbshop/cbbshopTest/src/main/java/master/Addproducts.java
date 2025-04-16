package master;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Addproducts{
    private WebDriver driver;
    private final String BASE_URL = "http://localhost:8080/shop/addproduct.jsp"; // 请使用实际的URL

    @Before
    public void setUp() {
        // 设置EdgeDriver路径
        System.setProperty("webdriver.edge.driver", "D:\\matong\\学习相关\\software\\edgedriver_win64\\msedgedriver.exe");
        driver = new EdgeDriver();
        driver.get(BASE_URL);
    }

    @Test
//    public void testAddProduct() throws IOException {
//        BufferedReader br = new BufferedReader(new FileReader("test.csv"));
//        String line;
//
//        // Skip CSV header if exists
//        br.readLine();
//
//        while ((line = br.readLine()) != null) {
//            String[] data = line.split(",");
//            String name = data[0].trim().replace("\"", "");
//            String productDescription = data[1].trim().replace("\"", "");
//            String price = data[2].trim();
//            String imagePath = data[3].trim();
//            String expectedResult = data[4].trim();
//
//            // 填充表单
//            WebDriverWait wait = new WebDriverWait(driver, 10);
//            
//            // 首先重置表单
//            WebElement resetButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("button[type='reset']")));
//            resetButton.click();
//
//            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("name"))).clear(); 
//            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("name"))).sendKeys(name);
//            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("description"))).clear();
//            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("description"))).sendKeys(productDescription);
//            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("price"))).clear();
//            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("price"))).sendKeys(price);
//
//            // 注：确保 imagePath 是绝对路径并指向一张有效的图片
//            WebElement uploadElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("image")));
//            uploadElement.sendKeys(imagePath);
//
//            // 提交表单
//            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("button[type='submit']"))).click();
//
//            // 等待重定向结果
//            wait.until(ExpectedConditions.urlContains("admin.jsp"));
//
//            // 验证结果
//            String currentUrl = driver.getCurrentUrl();
//
//            if (expectedResult.equals("成功注册商品")) {
//                assert currentUrl.contains("success=true");
//            } else {
//                assert currentUrl.contains("success=false") && currentUrl.contains(expectedResult.substring(expectedResult.indexOf("=") + 1));
//            }
//
//            // 返回主页，准备下一个测试
//            driver.navigate().to(BASE_URL);
//        }
//        
//        br.close();
//    }

    @After
    public void tearDown() {
        driver.quit();
    }
}