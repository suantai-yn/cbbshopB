package cbbtestB;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class RegisterTest {

    private WebDriver driver;

    @BeforeEach
    public void setUp() {
        // 设置 EdgeDriver 路径
        System.setProperty("webdriver.edge.driver", "D:\\yanizhang\\cbb-shop\\msedgedriver.exe");
        driver = new EdgeDriver();
        driver.manage().window().maximize(); // 最大化浏览器窗口
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit(); // 关闭浏览器
        }
    }

    @ParameterizedTest
    @CsvSource({
        "张1, 031130, 19357391594, 家, 你喜欢什么颜色, 蓝色, 注册成功！",
        "张1, 031130, 19357391594, 家, 你喜欢什么颜色, 蓝色, 注册失败，请稍后重试",
        "张娅妮011, 031130, 19357391594, 家, 你喜欢什么颜色, 蓝色, 注册成功！",
        "张娅妮012, p@ssword, 19357391594, 家, 你喜欢什么颜色, 蓝色, 注册成功！",
        "张娅妮, 123456, 19357391596, 123SpringfieldStMAin456AptUSA12345-6789OR123@#$%^&*()_+-=/\\|<>?, 你喜欢什么颜色, 蓝色,注册成功！"
    })
    public void register(String username, String password, String phone, String name, String answer1, String answer2, String expectedAlert) {
        try {
            // 打开注册页面
            openRegistrationPage();

            // 填写注册表单
            fillRegistrationForm(username, password, phone, name, answer1, answer2);

            // 提交表单并验证弹窗
            submitFormAndVerifyAlert(expectedAlert);

        } catch (Exception e) {
            // 捕获异常并打印日志
            System.err.println("测试失败: " + e.getMessage());
            throw e; // 重新抛出异常，确保测试标记为失败
        }
    }

    @ParameterizedTest
    @CsvSource({
        "张*, 031130, 19357391594, 家, 你喜欢什么颜色, 蓝色, true",  // 元素存在
        "张娅妮03, 031130, 1935739159, 家, 你喜欢什么颜色, 蓝色, true" ,
        "张娅妮03, 031130, 19357391594z, 家, 你喜欢什么颜色, 蓝色, true" ,
        "张娅妮03, 031130, 19357391594, 家, 你喜欢什么颜色?, 蓝色, true" ,
        "张娅妮03, 031130, 19357391594, 家, 你喜欢什么颜色, 蓝色?, true" ,
    })
    public void registerAndCheckElement(String username, String password, String phone, String name, String answer1, String answer2, boolean expectedElementExists) {
        try {
            // 打开注册页面
            openRegistrationPage();

            // 填写注册表单
            fillRegistrationForm(username, password, phone, name, answer1, answer2);

            // 提交表单
            driver.findElement(By.cssSelector("button:nth-child(1)")).click();

            // 判断元素是否存在
            boolean isElementPresent = isElementPresent(By.xpath("//*[@id='app']/div/div/div[8]/span"));

            // 验证元素是否存在
            assertThat(isElementPresent, is(expectedElementExists));

        } catch (Exception e) {
            // 捕获异常并打印日志
            System.err.println("测试失败: " + e.getMessage());
            throw e; // 重新抛出异常，确保测试标记为失败
        }
    }

    /**
     * 打开注册页面
     */
    private void openRegistrationPage() {
        driver.get("http://localhost:8086/");
        driver.findElement(By.cssSelector("button:nth-child(2)")).click(); // 点击注册按钮
        driver.findElement(By.cssSelector("button:nth-child(2)")).click(); // 确认进入注册页面
    }

    /**
     * 填写注册表单
     *
     * @param username 用户名
     * @param password 密码
     * @param phone    手机号
     * @param name     姓名
     * @param answer1  安全问题答案1
     * @param answer2  安全问题答案2
     */
    private void fillRegistrationForm(String username, String password, String phone, String name, String answer1, String answer2) {
        driver.findElement(By.cssSelector(".input-group:nth-child(2) > input")).sendKeys(username);
        driver.findElement(By.cssSelector(".input-group:nth-child(3) > input")).sendKeys(password);
        driver.findElement(By.cssSelector(".input-group:nth-child(4) > input")).sendKeys(phone);
        driver.findElement(By.cssSelector(".input-group:nth-child(5) > input")).sendKeys(name);
        driver.findElement(By.cssSelector("label:nth-child(1) > input")).click(); // 选择性别
        driver.findElement(By.cssSelector(".input-group:nth-child(7) > input")).sendKeys(answer1);
        driver.findElement(By.cssSelector(".input-group:nth-child(8) > input")).sendKeys(answer2);
    }

    /**
     * 提交表单并验证弹窗
     *
     * @param expectedAlert 预期的弹窗消息
     */
    private void submitFormAndVerifyAlert(String expectedAlert) {
        driver.findElement(By.cssSelector("button:nth-child(1)")).click(); // 提交表单

        // 等待弹窗出现并验证消息
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.alertIsPresent());
        String alertText = driver.switchTo().alert().getText();
        assertThat(alertText, is(expectedAlert));
    }

    /**
     * 判断元素是否存在
     *
     * @param by 定位方式（如 By.xpath、By.id 等）
     * @return 如果元素存在返回 true，否则返回 false
     */
    private boolean isElementPresent(By by) {
        try {
            driver.findElement(by); // 查找元素
            return true; // 元素存在
        } catch (org.openqa.selenium.NoSuchElementException e) {
            return false; // 元素不存在
        }
    }
}