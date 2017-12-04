import org.apache.poi.ss.formula.functions.T;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class LeftRightSliderTest {
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

        baseUrl = "http://www.mcgill.ca/";

        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

        System.out.println("setup done.");

    }
    //todo need to be improved!!!!
    @Test
    public void testSlider() throws Exception {

        driver.get(baseUrl);

        By next = By.xpath("//*[@id=\"block-views-slideshows-block-2\"]/div/div/div[2]/div/div[3]");
        By previous = By.xpath("//*[@id=\"block-views-slideshows-block-2\"]/div/div/div[2]/div/div[2]");

        for (int i = 0; i < 4; i ++){
            driver.findElement(next).click();
            Thread.sleep(1000);
        }
        for (int i = 0; i < 2; i ++) {
            driver.findElement(previous).click();
            Thread.sleep(1000);
        }

        By titleContent = By.xpath("/html/body/div[5]/div/div/div/div/div[1]/div/div/div[5]/div[3]/div/div[2]/a");
        WebElement title = (new WebDriverWait(driver, 5)).until(ExpectedConditions.visibilityOfElementLocated(titleContent));
        assertEquals("About this image:", title.getText());
        System.out.println("test done.");

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


}
