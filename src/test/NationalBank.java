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

public class NationalBank {
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

        baseUrl = "https://www.nbc.ca/";

        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

        System.out.println("setup done.");

    }

    @Test
    public void testBanner() throws Exception {

        driver.get(baseUrl+"en/personal.html/");

        WebElement dot = driver.findElement(By.xpath("//*[@id=\"main-wrapper\"]/section/div[1]/article/div[1]/div/div/div[2]/ul/li[2]"));
        WebElement link = driver.findElement(By.xpath("//*[@id=\"cqc-banner-carrousel-particuliers-2\"]/a/img"));

        Actions actions = new Actions(driver);

        actions.moveToElement(dot).click().build().perform();
        actions.moveToElement(link);
        actions.click().build().perform();

        String linkText;
        linkText = driver.findElement(By.xpath("//a[@href=\"http://localisateur.bnc.ca/?lang=en\"]")).getText();
        assertEquals("Find a branch", linkText);
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
