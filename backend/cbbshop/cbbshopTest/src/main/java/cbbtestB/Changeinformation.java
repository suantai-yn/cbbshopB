package cbbtestB;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class Changeinformation {
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

    @ParameterizedTest
    @CsvSource({
        "zyn, 123456, 193573915941, 浙江省白杨街道, 你喜欢什么颜色, 蓝色, 请确保输入符合要求！",
        "zyn, 123456, 1935739159, 浙江省白杨街道, 你喜欢什么颜色, 蓝色, 请确保输入符合要求！",
        "zyn, 123456, 19357391594, 浙江省白杨街道, 你喜欢什么颜色？, 蓝色, 加载个人信息失败，请稍后重试！",
        "zyn, 123456, 19357391594, 浙江省白杨街道, 你喜欢什么颜色, 蓝色！, 加载个人信息失败，请稍后重试！",
        "zyn, 123456, 19357391594, 浙江省杭州市白杨街道, 你喜欢什么颜色, 蓝色, 用户信息更新成功！",
        "zyn, 123456, 19357391594, 浙江省白杨街道！@#￥%……&）（）*&……%￥#, 你喜欢什么颜色, 蓝色, 用户信息更新成功！",
    })
    public void changebuyer(String username, String password, String phone, String address, String question, String answer, String expectedAlertMessage) {
        // 打开网页并设置窗口大小
        driver.get("http://localhost:8086/");
        driver.manage().window().setSize(new Dimension(1010, 1040));

        // 登录操作
        driver.findElement(By.cssSelector("button:nth-child(2)")).click();
        driver.findElement(By.cssSelector(".input-group:nth-child(2) > input")).sendKeys(username);
        driver.findElement(By.cssSelector(".input-group:nth-child(3) > input")).sendKeys(password);
        driver.findElement(By.cssSelector("button:nth-child(3)")).click();

        // 等待登录成功并跳转到目标页面
        wait.until(ExpectedConditions.urlContains("dashboard")); // 假设登录后跳转到包含 "dashboard" 的页面

        // 点击修改个人信息
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("修改个人信息")));
        driver.findElement(By.linkText("修改个人信息")).click();

        // 等待修改个人信息页面加载完成
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("label:nth-child(1) > input")));

        // 填写表单
        driver.findElement(By.cssSelector("label:nth-child(1) > input")).clear();
        driver.findElement(By.cssSelector("label:nth-child(1) > input")).sendKeys(password); // 使用登录密码作为新密码
        driver.findElement(By.cssSelector("label:nth-child(2) > input")).clear();
        driver.findElement(By.cssSelector("label:nth-child(2) > input")).sendKeys(phone);
        driver.findElement(By.cssSelector("label:nth-child(3) > input")).clear();
        driver.findElement(By.cssSelector("label:nth-child(3) > input")).sendKeys(address);
        driver.findElement(By.cssSelector("label:nth-child(4) > input")).clear();
        driver.findElement(By.cssSelector("label:nth-child(4) > input")).sendKeys(question);
        driver.findElement(By.cssSelector("label:nth-child(5) > input")).clear();
        driver.findElement(By.cssSelector("label:nth-child(5) > input")).sendKeys(answer);

        // 提交表单
        driver.findElement(By.cssSelector("button:nth-child(6)")).click();

        // 等待弹窗出现并验证弹窗文本
        wait.until(ExpectedConditions.alertIsPresent());
        assertThat(driver.switchTo().alert().getText(), is(expectedAlertMessage));
    }
}