import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.internal.TextListener;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Iterator;
import java.util.List;
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

        baseUrl = "https://www.just-eat.ca/";

        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

        System.out.println("setup done.");

    }

    @Test
    public void testJusteat() throws Exception {

        driver.get(baseUrl);
        driver.findElement(By.id("homepage-search-fullAddress")).clear();
        driver.findElement(By.id("homepage-search-fullAddress")).sendKeys("1411 du Fort");

        WebElement addressTable = driver.findElement(By.className("addressLookup-suggestions"));
        List addressList = addressTable.findElements(By.tagName("li"));
        Iterator iterator = addressList.iterator();

        while (iterator.hasNext()){
            WebElement address = (WebElement) iterator.next();
            System.out.println(address.getText());
        }
        System.out.println("testing done.");
        System.out.println("test git trigger");
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
