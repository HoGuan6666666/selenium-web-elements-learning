
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class PinpineatTest {
    private WebDriver driver;
    private String baseUrl;
    private StringBuffer verificationErrors = new StringBuffer();

    @BeforeClass
    public void setUp() throws Exception {
        //chose driver type
        String os = (System.getProperty("os.name"));

        if (os.equalsIgnoreCase("Mac OS X"))
            System.setProperty("webdriver.chrome.driver", "src/test/resources/drivers/chromedriver");
        else System.setProperty("webdriver.chrome.driver", "src/test/resources/drivers/chromedriver.exe");
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--kiosk");

        driver = new ChromeDriver(chromeOptions);

        baseUrl = "https://www.pinpineat.com/#!/";
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    @Test
    public void testLogin() throws Exception {
        System.out.println("Start login test");
        //nevigate to home page of pinpin eat
        driver.get(baseUrl);

        //find login button and click it
        driver.findElement(By.xpath("//a[@href=\"#!/login\"]")).click();

        //enter username and password in text field and click on login button
        driver.findElement(By.xpath("//input[@id=\"username\"]")).sendKeys("hoggg@gmail.com");
        driver.findElement(By.xpath("//input[@id=\"password\"]")).sendKeys("123456");
        driver.findElement(By.xpath("//button[@type=\"submit\"]")).click();

        //wait 2 seconds for loading page completely,
        // then validate login successfully by compareing the replacement button(user) of login
        Thread.sleep(2000);
        String user = driver.findElement(By.xpath("/html/body/nav/div/div[2]/ul/li[2]/a/span/span[2]")).getText();
        Assert.assertEquals("User", user);
        System.out.println("login succeed");
    }

    @Test(dependsOnMethods = "testLogin")
    public void testSearchShop() throws Exception {
        System.out.println("Start Search Shop test");
        //enter post code in text field and click on search button
        driver.findElement(By.id("mapsearchInput")).sendKeys("H3H");
        driver.findElement(By.id("mapsearchbtn")).click();
        Thread.sleep(2000);
        String oldTab = driver.getWindowHandle();
        //find restuarant "Restaurant Mr. Gao"
        String imgLoc = "//img[@src=\"https://pinpinmarket.s3.amazonaws.com/i/shop62_%E5%B7%9D%E5%91%B3%E9%A6%99shop_image.png\"]";
//        String imgOut = "//img[@ng-src=\"https://pinpinmarket.s3.amazonaws.com/i/shop88_shop68_thai_shop_image.png\"]";
        driver.findElement(By.xpath(imgLoc)).click();
        ArrayList<String> newTab = new ArrayList<String>(driver.getWindowHandles());
        newTab.remove(oldTab);
        driver.switchTo().window(newTab.get(0));
        Thread.sleep(3000);
        driver.getCurrentUrl().equals("https://www.pinpineat.com/#!/shop");
        //wait 3 seconds for loading restaurant page completely by reastaurant address
        //then validate loading restaurant page successfully
        String address = driver.findElement(By.id("shopAddr")).getText();
        assertEquals("2350 Guy", address);
        System.out.println("Search Shop complete");

    }

    @Test(dependsOnMethods = "testSearchShop")
    public void testAddItem() throws Exception {
        System.out.println("Start Add Item in shop cart Test");
        //add some dishes in the cart
        driver.findElement(By.cssSelector("span.glyphicon.glyphicon-plus")).click();
        driver.findElement(By.cssSelector("span.glyphicon.glyphicon-plus")).click();
        driver.findElement(By.cssSelector("span.glyphicon.glyphicon-plus")).click();
        driver.findElement(By.cssSelector("span.glyphicon.glyphicon-plus")).click();

        driver.findElement(By.id("submitcartbtn")).click();
        System.out.println("Add Item in shop cart complete");
    }

    @Test(dependsOnMethods = "testAddItem")
    public void testCheckout() throws Exception {
        System.out.println("Start Check Out Test");
        System.out.println(driver.getCurrentUrl());
        //fill in delivery information step - 1
        driver.findElement(By.xpath("//input[@id=\"aptinput\"]")).sendKeys("1111");
        driver.findElement(By.xpath("//input[@id=\"streetinput\"]")).sendKeys("du fort");
        driver.findElement(By.xpath("//input[@id=\"cityinput\"]")).sendKeys("montreal");
        driver.findElement(By.xpath("//input[@id=\"postalinput\"]")).sendKeys("H3H");
        driver.findElement(By.xpath("//button[@id=\"step1btn\"]")).click();

        //fill in delivery information step - 2
        driver.findElement(By.xpath("//input[@id=\"fnameinput\"]")).sendKeys("Ho");
        driver.findElement(By.xpath("//input[@id=\"lnameinput\"]")).sendKeys("GGG");
        driver.findElement(By.xpath("//input[@id=\"phoneinput\"]")).sendKeys("5141111111");
        driver.findElement(By.xpath("//input[@id=\"emailinput\"]")).sendKeys("hoggg@gmail.com");
        driver.findElement(By.xpath("//button[@id=\"step2btn\"]")).click();

        //validate transaction is processed to last step
        String toggle = driver.findElement(By.xpath("//a[@id=\"step3toggle\"]")).getText();
        assertEquals("3. PAYMENT", toggle);
        System.out.println(toggle);
        System.out.println("Check out complete");
    }

    @AfterClass
    public void tearDown() throws Exception {
        driver.quit();
        String verificationErrorString = verificationErrors.toString();
        if (!"".equals(verificationErrorString)) {
            fail(verificationErrorString);
        }
    }
}
