package master;


import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import java.io.*;
import java.util.*;


public class BuyerSendTest {
    private WebDriver driver;
    private JavascriptExecutor js;

    @Before
    public void setUp() {
        // 设置系统属性，指向你的chromedriver.exe的路径
        System.setProperty("webdriver.chrome.driver", "./tool/chromedriver.exe");
        // 创建ChromeDriver实例
        driver = new ChromeDriver();
        js = (JavascriptExecutor) driver;
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void testBuyerPurchase() throws IOException {
        // 读取CSV文件
//        List<BuyerData> testData = readTestData("./tool/buyercontent.csv");

        // 遍历测试数据
//        for (BuyerData data : testData) {
//            driver.get("http://localhost:8080/shop/index.jsp"); // 确保URL是正确的
//            driver.manage().window().setSize(new Dimension(1280, 1024));
//
//            // 填写购买信息
//            driver.findElement(By.name("userName")).sendKeys(data.getUsername());
//            driver.findElement(By.name("userContact")).sendKeys(data.getUsercontact());
//            driver.findElement(By.name("transactionAddress")).sendKeys(data.getTransactionAddress());
//
//            // 提交购买
//            driver.findElement(By.cssSelector("button[type='submit']")).click();
//
//            // 验证结果
//            if (data.getResult()) {
//                verifySuccess();
//            } else {
//                verifyError();
//            }
        }
    

    private void verifySuccess() {
        // 验证成功情况，例如检查URL是否包含成功信息
        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("?success=true"));
    }

    private void verifyError() {
        // 验证错误情况，例如检查URL是否包含错误信息
        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("?error="));
    }

//    private List<BuyerData> readTestData(String filePath) throws IOException {
//        List<BuyerData> testData = new ArrayList<>();
//        try (BufferedReader reader = new BufferedReader(new FileReader(filePath));
//             CSVParser parser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withTrim())) {
//
//            for (CSVRecord record : parser) {
//                BuyerData data = new BuyerData();
//                data.setId(Integer.parseInt(record.get("id")));
//                data.setUsername(record.get("username"));
//                data.setUsercontact(record.get("usercontact"));
//                data.setTransactionAddress(record.get("transactionAddress"));
//                data.setResult(Boolean.parseBoolean(record.get("result")));
//                testData.add(data);
//            }
//        }
//        return testData;
//    }

    // 内部类用于存储测试数据
    private static class BuyerData {
        private int id;
        private String username;
        private String usercontact;
        private String transactionAddress;
        private boolean result;

        // Getter和Setter方法
        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getUsercontact() {
            return usercontact;
        }

        public void setUsercontact(String usercontact) {
            this.usercontact = usercontact;
        }

        public String getTransactionAddress() {
            return transactionAddress;
        }

        public void setTransactionAddress(String transactionAddress) {
            this.transactionAddress = transactionAddress;
        }

        public boolean getResult() {
            return result;
        }

        public void setResult(boolean result) {
            this.result = result;
        }
    }
}