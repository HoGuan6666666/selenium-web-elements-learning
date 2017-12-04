import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.fail;

public class ResizableTextboxTest {
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

        baseUrl = "https://jqueryui.com/resizable/";

        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

        driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);

        System.out.println("setup done.");

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

    @Test
    public void testResizableTextbox() throws InterruptedException {
        driver.get(baseUrl);
        driver.switchTo().frame(driver.findElement(By.className("demo-frame")));
        WebElement resizeElement = driver.findElement(By.xpath("//*[@id='resizable']/div[3]"));
        new Actions(driver).dragAndDropBy(resizeElement, 100, 200).build().perform();
        Thread.sleep(5000);
        new Actions(driver).clickAndHold(resizeElement).moveByOffset(200,100).release().perform();
        Thread.sleep(5000);
        System.out.println("Test done");
    }
}
