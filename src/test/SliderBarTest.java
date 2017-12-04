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

public class SliderBarTest {
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

        baseUrl = "https://gleec.com/";

        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

        System.out.println("setup done.");

    }

    @Test
    public void testSliderBar() throws Exception {

        driver.get(baseUrl);
        driver.findElement(By.xpath("/html/body/main/div[3]/div/div/a/span[1]")).click();
        driver.findElement(By.xpath("/html/body/main/div[4]/div/div/div/div[3]/div/div[2]/a")).click();
        driver.findElement(By.xpath("/html/body/main/div[4]/div/div/div/div[3]/div/div[3]/a")).click();
        driver.findElement(By.xpath("/html/body/main/div[4]/div/div/div/div[3]/div/div[4]/a")).click();;

        //perform hover over the menu
        Actions builder = new Actions(driver);
        WebElement hoverElement = driver.findElement(By.xpath("/html/body/main/div[4]/div/span/div[2]/span[2]/span[2]"));
        builder.moveToElement(hoverElement).perform();

        //wait until terms & conditions visible
        By link = By.linkText("TERMS & CONDITIONS");
        WebElement locator = (new WebDriverWait(driver, 5)).until(ExpectedConditions.visibilityOfElementLocated(link));
        locator.click();

        By titleLocator = By.xpath("/html/body/main/div[3]/div/div/div/div[2]/div[1]");
        WebElement title = (new WebDriverWait(driver, 5)).until(ExpectedConditions.visibilityOfElementLocated(titleLocator));
        assertEquals("Gleec SL is the producer of the Gleec application", title.getText());
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


}
