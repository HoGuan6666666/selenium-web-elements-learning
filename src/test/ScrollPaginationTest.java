import gherkin.lexer.Th;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class ScrollPaginationTest {
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

    @Test
    public void testScrollPage() throws InterruptedException {
        driver.get(baseUrl);

        Actions actions = new Actions(driver);
        WebElement menuPC = driver.findElement(By.xpath("/html/body/div[4]/ul/li[4]"));
        actions.moveToElement(menuPC).perform();
        Thread.sleep(3000);
        WebElement seeAll = driver.findElement(By.xpath("//a[@href=\"/SearchResult/QuickSearchHeaderPlatform?platform=30\"]"));
        String button = seeAll.getText();
        actions.moveToElement(seeAll);
        actions.click().build().perform();
        String filterText = driver.findElement(By.xpath(
                "/html/body/div[5]/div[1]/div[2]/div[2]/div/div[1]/div[2]/h4[1]")).getText();
        assertEquals("Genre", filterText);
        System.out.println("Went to PC game list page successful!");

        int pageNumber = 7;
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        while (pageNumber > 0) {
            jse.executeScript("window.scrollTo" +
                    "(0,Math.max(document.documentElement.scrollHeight," +
                    "document.body.scrollHeight,document.documentElement.clientHeight));");
            Thread.sleep(3000);
            pageNumber--;
        }
        String gameText = driver.findElement(By.xpath(
                "/html/body/div[5]/div[1]/div[2]/div[3]/div[3]/div[7]/div[5]/div[1]/h4/span/strong")).getText();
        assertEquals("Blizzard Entertainment", gameText);
        System.out.println("Test done");
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
