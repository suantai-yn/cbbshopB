package cbbtestB;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;


public class ResetPassword {

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
        "admin, cbbshop, 123456, 密码重置成功！, true", // 正确的用户名、安全问题答案和新密码
        "zyn, 蓝色, 123456, 密码重置成功！, true", // 错误的安全问题答案
        "admin, cbbshop1, 123456, 验证问题回答错误,false", 
        "zyn, 黄色, 123456, 密码重置失败，答案不正确。, true",
    })
    public void resetPassword(String username, String securityAnswer, String newPassword, String expectedMessage, boolean isSuccess) {
        try {
            // 打开重置密码页面
            openResetPasswordPage();

            // 填写重置密码表单
            fillResetPasswordForm(username, securityAnswer, newPassword);

            // 提交表单
            driver.findElement(By.cssSelector(".button-group > button:nth-child(2)")).click();

            // 根据是否成功，判断是弹窗还是页面元素
            if (isSuccess) {
                // 成功时验证弹窗
                verifyAlert(expectedMessage);
            } else {
                // 失败时验证页面元素
                verifyPageMessage(expectedMessage);
            }

        } catch (Exception e) {
            // 捕获异常并打印日志
            System.err.println("测试失败: " + e.getMessage());
            throw e; // 重新抛出异常，确保测试标记为失败
        }
    }

    /**
     * 打开重置密码页面
     */
    private void openResetPasswordPage() {
        driver.get("http://localhost:8086/");
        driver.findElement(By.cssSelector("button:nth-child(2)")).click(); // 点击登录按钮
        driver.findElement(By.linkText("忘记密码？")).click(); // 点击忘记密码链接
    }

    /**
     * 填写重置密码表单
     *
     * @param username       用户名
     * @param securityAnswer 安全问题答案
     * @param newPassword    新密码
     */
    private void fillResetPasswordForm(String username, String securityAnswer, String newPassword) {
        driver.findElement(By.cssSelector(".reset-content input")).sendKeys(username); // 输入用户名
        WebElement element = driver.findElement(By.cssSelector(".reset-content"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        WebElement inputElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".input-group:nth-child(4) > input")));
        inputElement.sendKeys(securityAnswer);
        driver.findElement(By.cssSelector(".input-group:nth-child(5) > input")).sendKeys(newPassword); // 输入新密码
    }

    /**
     * 验证弹窗消息
     *
     * @param expectedMessage 预期的弹窗消息
     */
    private void verifyAlert(String expectedMessage) {
        // 等待弹窗出现并验证消息
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.alertIsPresent());
        String alertText = driver.switchTo().alert().getText();
        assertThat(alertText, is(expectedMessage));
    }

    /**
     * 验证页面元素消息
     *
     * @param expectedMessage 预期的页面元素消息
     */
    private void verifyPageMessage(String expectedMessage) {
        // 等待页面元素出现并验证消息
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement messageElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='app']/div/div[2]/div/div[5]/span")));
        String actualMessage = messageElement.getText();
        assertThat(actualMessage, is(expectedMessage));
    }
}