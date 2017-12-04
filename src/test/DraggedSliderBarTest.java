import cucumber.api.java.cs.A;
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

public class DraggedSliderBarTest {
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

//        baseUrl = " http://rangeslider.js.org/";

        baseUrl = "https://jqueryui.com/slider/";

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
    public void draggedSliderTest() throws InterruptedException {
        driver.get(baseUrl);
        //switch to the frame, which contains the target slider bar.
        driver.switchTo().frame(driver.findElement(By.className("demo-frame")));

        //locate the slider pointer
        WebElement dragElementPointer = driver.findElement(By.xpath("//*[@id='slider']/span"));

        //second demo from another website
        //        WebElement dragElementPointer = driver.findElement(By.xpath("/html/body/section[1]/div/div[2]"));


        //to move slider by 200 pixels offset using dragAndDropBy method
        new Actions(driver).dragAndDropBy(dragElementPointer, 100, 0).build().perform();

        Thread.sleep(5000);
        //After 5 seconds, moving slider by 100 pixels offset using clickAndHold and moveByOffset method
        //After perform moving slider option, release methods of actions
        new Actions(driver).clickAndHold(dragElementPointer).moveByOffset(100,0).release().perform();

        Thread.sleep(1000);


    }
}
