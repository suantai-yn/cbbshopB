package master;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class changepassword {

    private WebDriver driver;
    private Map<Integer, Map<String, String>> testCases;

    @BeforeEach
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "./tool/chromedriver"); // 设置ChromeDriver路径
        driver = new ChromeDriver();
        driver.manage().window().maximize(); // 最大化窗口
        driver.get("http://localhost:8080/shop/admin.jsp"); // 替换为你的Web应用的URL

        // 读取CSV文件
        readTestCases("./tool/changepassword.csv"); // 设置CSV文件路径
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void testChangePassword() {
        for (Map.Entry<Integer, Map<String, String>> testCase : testCases.entrySet()) {
            Map<String, String> data = testCase.getValue();
            driver.findElement(By.name("oldPassword")).sendKeys(data.get("oldpassword"));
            driver.findElement(By.name("newPassword")).sendKeys(data.get("newpassword"));
            driver.findElement(By.tagName("button")).click(); // 点击提交按钮

            // 验证结果
            String expectedResult = data.get("result");
            String actualResult = driver.getCurrentUrl(); // 假设成功或错误会重定向到不同的URL
            if ("success".equals(expectedResult)) {
                assert actualResult.contains("?success=changed") : "密码修改失败";
            } else {
                assert actualResult.contains("?error=") : "密码修改未按预期失败";
            }
        }
    }

    private void readTestCases(String filePath) {
        testCases = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(new File(filePath)))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length >= 5) {
                    int id = Integer.parseInt(parts[0].split(":")[1].trim());
                    String oldPassword = parts[1].split(":")[1].trim();
                    String newPassword = parts[2].split(":")[1].trim();
                    String result = parts[3].split(":")[1].trim();
                    testCases.put(id, Map.of("oldpassword", oldPassword, "newpassword", newPassword, "result", result));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
