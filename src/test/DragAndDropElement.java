import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.fail;

public class DragAndDropElement {
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

        baseUrl = "http://marcojakob.github.io/";

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
    public void dragAndDropTest() throws InterruptedException {
        driver.get(baseUrl + "dart-dnd/custom-avatar/web/");

        //locate which element you want to drag
        ArrayList<WebElement> spitballs = new ArrayList<WebElement>();
        spitballs.add(driver.findElement(By.xpath("/html/body/div/img[1]")));
        spitballs.add(driver.findElement(By.xpath("/html/body/div/img[2]")));
        spitballs.add(driver.findElement(By.xpath("/html/body/div/img[3]")));
        spitballs.add(driver.findElement(By.xpath("/html/body/div/img[4]")));

        //locate which element you want to drop
        WebElement trash = driver.findElement(By.xpath("/html/body/div/div"));

        Actions builder = new Actions(driver);
        for(int i = 0; i < spitballs.size(); i ++) {

            //release method: Release the pressed left mouse button at the current mouse location pointer
            Action dragAndDrop = builder.clickAndHold(spitballs.get(i)).moveToElement(trash).release(trash).build();
            dragAndDrop.perform();
            Thread.sleep(500);
        }
    }
}
