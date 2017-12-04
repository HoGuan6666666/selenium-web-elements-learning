import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.internal.TextListener;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.internal.TextListener;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import sun.jvm.hotspot.debugger.Address;

import java.util.concurrent.TimeUnit;

        import static org.junit.Assert.fail;

public class AngularJSTest {
    private WebDriver wd;
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

        wd = new ChromeDriver(chromeOptions);

        wd.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

        System.out.println("setup done.");

    }

//    @Test
//    public void testGOLO() throws Exception {
//
//        wd.get("http://docs.angularjs.org/cookbook/advancedform");
//
//        WebElement cityField = wd.findElement(By.cssSelector("input[ng-model='form.address.city']"));
//        WebElement theForm = wd.findElement(By.cssSelector("div[ng-controller='UserForm']"));
//
//        assertThat(cityField.getAttribute("value"), is("Anytown"));
//
//        cityField.clear();
//        cityField.sendKeys("Chicago");
//
//// returns a GoogleCollections Map ordinarily ... note the toString() at the end..
//        String addr = wd.executeScript("return angular.element(arguments[0]).scope().form.address;", theForm).toString();
//
//// Not JSON, but close enough perhaps:
//        assertThat(addr, is("{zip=12345, state=AA, line1=123 Main St., city=Chicago}"));
//
//// returns a JSON String:
//        addr = wd.executeScript("return angular.toJson(angular.element(arguments[0]).scope().form.address);", theForm);
//
//
//
//        wd.executeScript("angular.element(arguments[0]).scope().form.address.city = 'New York';" +
//                "angular.element(document.body).injector().get('$rootScope').$apply();", theForm);
//
//        assertThat(cityField.getAttribute("value"), is("New York"));
//
//
//        System.out.println("testing done.");
//
//
//    }

    @After
    public void tearDown() throws Exception {
        if (wd != null)
            wd.quit();

        String verificationErrorString = verificationErrors.toString();
        if (!"".equals(verificationErrorString)) {
            fail(verificationErrorString);
        }

        System.out.println("Tear down done.");

    }

//    public static void main(String args[]){
//        JUnitCore junit = new JUnitCore();
//        junit.addListener(new TextListener(System.out));
//        Result result = junit.run(AddressTest.class);
////        Result result = JUnitCore.runClasses(AddressTest.class);
//        if(result.getFailureCount() > 0){
//            System.out.println("Test failed.");
//            System.exit(1);
//        }else {
//            System.out.println("Test finished successfully");
//            System.exit(0);
//        }
//    }
}

