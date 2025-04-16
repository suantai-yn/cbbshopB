package cbbtestB;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class GoodsOnline {
    private WebDriver driver;
    private JavascriptExecutor js;
    private WebDriverWait wait;

    @BeforeEach
    public void setUp() {
        System.setProperty("webdriver.edge.driver", "D:\\yanizhang\\cbb-shop\\msedgedriver.exe");
        driver = new EdgeDriver();
        js = (JavascriptExecutor) driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // 设置显式等待时间为10秒
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }

    @ParameterizedTest
    @CsvSource({
        "倍思耳机, 100, 300, 电子产品, 电子产品-耳机, D:\\ecli\\cbb_shop\\docs\\测试\\goods\\耳机1.jpg, 1,更舒适的配感，更惊艳的音质, 商品发布成功！",
        "小米耳机, 200, 150, 电子产品, 电子产品-耳机, D:\\ecli\\cbb_shop\\docs\\测试\\goods\\erji.txt,2,更舒适的配感，更惊艳的音质, 商品发布失败，请稍后重试！",
        "小米耳机, -200, 150, 电子产品, 电子产品-耳机, D:\\ecli\\cbb_shop\\docs\\测试\\goods\\耳机2.jpg, 3,更舒适的配感，更惊艳的音质, 商品发布失败，请稍后重试！"
    })
    public void goodsonline(String productName, String price, String stock, String category1, String category2, String imagePath, String description1, String description2, String expectedAlertMessage) {
    	// 打开网页并设置窗口大小
        driver.get("http://localhost:8086/");
        driver.manage().window().setSize(new Dimension(1010, 1040));

        // 登录操作
        driver.findElement(By.cssSelector("button:nth-child(2)")).click();
        driver.findElement(By.cssSelector(".input-group:nth-child(2) > input")).sendKeys("admin");
        driver.findElement(By.cssSelector(".input-group:nth-child(3) > input")).sendKeys("123456");
        driver.findElement(By.cssSelector("button:nth-child(3)")).click();

        // 等待登录成功并跳转到目标页面
        wait.until(ExpectedConditions.urlContains("dashboard")); // 假设登录后跳转到包含 "dashboard" 的页面

        // 点击发布商品
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("发布商品")));
        driver.findElement(By.linkText("发布商品")).click();

        // 等待发布商品页面加载完成
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".form-group:nth-child(1) > input")));

        // 填写商品名称
        driver.findElement(By.cssSelector(".form-group:nth-child(1) > input")).sendKeys(productName);

        // 填写商品价格
        driver.findElement(By.cssSelector(".form-group:nth-child(2) > input")).sendKeys(price);

        // 填写商品库存
        driver.findElement(By.cssSelector(".form-group:nth-child(3) > input")).sendKeys(stock);

     // 等待一级分类下拉框可点击并点击
        WebElement category1Dropdown = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("/html/body/div/div/div[2]/div/form/div[4]/select[1]")
        ));
        category1Dropdown.click();

        // 等待一级分类选项可点击并选择
        WebElement category1Option = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("/html/body/div/div/div[2]/div/form/div[4]/select[1]/option[2]")
        ));
        category1Option.click();

        // 等待二级分类下拉框可点击并点击
        WebElement category2Dropdown = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("/html/body/div/div/div[2]/div/form/div[4]/select[2]")
        ));
        category2Dropdown.click();

        // 等待二级分类选项可点击并选择
        WebElement category2Option = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("/html/body/div/div/div[2]/div/form/div[4]/select[2]/option[2]")
        ));
        category2Option.click();

        // 上传商品图片
        driver.findElement(By.cssSelector(".form-group:nth-child(5) > input")).sendKeys(imagePath);

        // 填写商品描述
        {
            WebElement element = driver.findElement(By.id("w-e-textarea-1"));
            js.executeScript("if(arguments[0].contentEditable === 'true') {arguments[0].innerText = arguments[1]}", element, description1);
        }

        // 填写商品详情
        {
            WebElement element = driver.findElement(By.id("w-e-textarea-1"));
            js.executeScript("if(arguments[0].contentEditable === 'true') {arguments[0].innerText = arguments[1]}", element, description2);
        }

        // 提交表单
        driver.findElement(By.cssSelector("button:nth-child(7)")).click();

     // 创建 WebDriverWait，设置最大等待时间为 10 秒
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // 等待弹窗出现
        wait.until(ExpectedConditions.alertIsPresent());

        // 验证弹窗文本
        assertThat(driver.switchTo().alert().getText(), is(expectedAlertMessage));
}}