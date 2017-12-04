import gherkin.lexer.Th;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.fail;

public class ZoomInOutTest {
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

        baseUrl = "https://www.ebgames.ca/";

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
    public void testZoomInOut() throws InterruptedException {
        driver.get(baseUrl);
        ((JavascriptExecutor)driver).executeScript("document.body.style.zoom='200%';");
        Thread.sleep(3000);
        ((JavascriptExecutor)driver).executeScript("document.body.style.zoom='100%';");
        Thread.sleep(3000);

        //Don't know why this method doesn't work
//        //To zoom In page 4 time using CTRL/COMMAND and + keys.
//        for(int i=0; i<4; i++){
//            //zoom in method for Windows
////            driver.findElement(By.tagName("html")).sendKeys(Keys.chord(Keys.CONTROL,Keys.ADD));
//
//            //zoom in method for Mac
//            driver.findElement(By.tagName("html")).sendKeys(Keys.chord(Keys.COMMAND, Keys.ADD));
//
//        }
//        Thread.sleep(3000);
//
//        //To zoom out page 4 time using CTRL/COMMAND and - keys.
//        for(int i=0; i<4; i++){
//            //zoom out method for windows
////            driver.findElement(By.tagName("html")).sendKeys(Keys.chord(Keys.CONTROL,Keys.SUBTRACT));
//
//            //zoom out method for Mac
//            driver.findElement(By.tagName("html")).sendKeys(Keys.chord(Keys.COMMAND, Keys.SUBTRACT));
//        }
//        Thread.sleep(3000);
//
//        //Windows: To set browser to default zoom level 100%
////        driver.findElement(By.tagName("html")).sendKeys(Keys.chord(Keys.CONTROL, "0"));
//
//        //Mac: To set browser to default zoom level 100%
//        driver.findElement(By.tagName("html")).sendKeys(Keys.chord(Keys.COMMAND, "0"));
        System.out.println("Test Done");
    }
}
