import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.concurrent.TimeUnit;

import static java.lang.Thread.*;
import static org.junit.Assert.fail;

public class ScrollUpDownTest {
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

        baseUrl = "http://www.firewatchgame.com/";

        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

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
    public void testPageScrollUpDown() throws InterruptedException {
        driver.get(baseUrl);
        //cast webDriver element to Javasript executor
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        //scroll down
        jse.executeScript("scroll(0,1000)", "");
        System.out.println("Scroll Down complete");
        Thread.sleep(3000);
        //scroll up
        jse.executeScript("scroll(500,0)", "");
        System.out.println("Scroll Up complete");
        Thread.sleep((3000));

        System.out.println("First Test done");
    }

    @Test
    public void testScrollDownToButtom() throws InterruptedException {
        driver.get(baseUrl);
        //cast webDriver element to Javasript executor
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("window.scrollTo" +
                "(0,Math.max(document.documentElement.scrollHeight," +
                "document.body.scrollHeight,document.documentElement.clientHeight));");
        Thread.sleep(3000);

        System.out.println("Second Test done");
    }
}
