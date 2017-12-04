import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.internal.TextListener;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.fail;

public class AddressTest {
    private WebDriver driver;
    private String baseUrl;
    private StringBuffer verificationErrors = new StringBuffer();

    @Before
    public void setUp() throws Exception {
        //chose driver type
        String os = (System.getProperty("os.name"));

        if (os.equalsIgnoreCase("Mac OS X"))
            System.setProperty("webdriver.chrome.driver", "src/test/resources/drivers/chromedriver");
        else System.setProperty("webdriver.chrome.driver", "src/test/resources/drivers/chromedriver.exe");

        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--kiosk");

        driver = new ChromeDriver(chromeOptions);

        baseUrl = "https://gololocal.com/";

        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

        System.out.println("setup done.");

    }

    @Test
    public void testGOLO() throws Exception {

        driver.get(baseUrl + "en-ca/");
        driver.findElement(By.xpath("//input[@type='text']")).clear();
        driver.findElement(By.xpath("//input[@type='text']")).sendKeys("1411 Rue du Fort");

        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.linkText("1411 Fort Street, Montreal, QC, Canada")));

        driver.findElement(By.linkText("1411 Fort Street, Montreal, QC, Canada")).click();
        driver.findElement(By.cssSelector("button.AddressSearch__button")).click();

        driver.findElement(By.xpath("/html/body/root/div/div/view.shops/div/shops/div/div/div[2]/shops-list/div[1]/shops-list-item[1]/a/div[3]/h3")).click();
        driver.findElement(By.cssSelector("div.ProductItem__details")).click();
        new Select(driver.findElement(By.name("58e7fefdbc55f47ca3520d76"))).selectByVisibleText("100$");
        driver.findElement(By.cssSelector("button.modal-action-button")).click();
        driver.findElement(By.cssSelector("button.action-button")).click();

        //starts from shop list
//        driver.get(baseUrl + "en-ca/shops/");
//        driver.findElement(By.cssSelector("div.overlay")).click();
//        driver.findElement(By.cssSelector("div.product-description")).click();
//        driver.findElement(By.cssSelector("button.modal-action-button")).click();
//        driver.findElement(By.cssSelector("button.action-button")).click();

        System.out.println("testing done.");


    }

    @After
    public void tearDown() throws Exception {
        if (driver != null)
        driver.quit();

        String verificationErrorString = verificationErrors.toString();
        if (!"".equals(verificationErrorString)) {
            fail(verificationErrorString);
        }

        System.out.println("Tear down done.");

    }

    public static void main(String args[]){
        JUnitCore junit = new JUnitCore();
        junit.addListener(new TextListener(System.out));
        Result result = junit.run(AddressTest.class);
//        Result result = JUnitCore.runClasses(AddressTest.class);
        if(result.getFailureCount() > 0){
            System.out.println("Test failed.");
            System.exit(1);
        }else {
            System.out.println("Test finished successfully");
            System.exit(0);
        }
    }
}
