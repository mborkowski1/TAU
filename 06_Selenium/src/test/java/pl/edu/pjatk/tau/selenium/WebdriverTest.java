package pl.edu.pjatk.tau.selenium;

import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;

public class WebdriverTest {

    private  WebDriver driver;

    @Before
    public void setup() {
        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver");
        driver = new ChromeDriver();
        driver.get("http://automationpractice.com");
        driver.manage().window().fullscreen();
    }

    @Test
    public void homePage() throws IOException {
        driver.findElement(By.linkText("Sign in")).click();

        if (driver instanceof TakesScreenshot) {
            File f = ((TakesScreenshot) driver).
                    getScreenshotAs(OutputType.FILE);
            FileHandler.copy(f,
                    new File("screenshots/homePage.png"));
        }
    }

    @Test
    public void correctLogin() {
        driver.findElement(By.linkText("Sign in")).click();
        driver.findElement(By.name("email")).click();
        driver.findElement(By.name("email")).sendKeys("testowy@pjwstk.edu.pl");
        driver.findElement(By.xpath("//form[@id='login_form']/div")).click();
        driver.findElement(By.name("passwd")).click();
        driver.findElement(By.name("passwd")).sendKeys("testowy123");
        driver.findElement(By.xpath("//button[@id='SubmitLogin']/span")).click();
        Assert.assertEquals("Testowy User" ,driver.findElement(By.className("header_user_info")).getText());
    }

    @Test
    public void incorrectLogin() {
        driver.findElement(By.linkText("Sign in")).click();
        driver.findElement(By.name("email")).click();
        driver.findElement(By.name("email")).sendKeys("test@pjwstk.edu.pl");
        driver.findElement(By.xpath("//form[@id='login_form']/div")).click();
        driver.findElement(By.name("passwd")).click();
        driver.findElement(By.name("passwd")).sendKeys("test123");
        driver.findElement(By.xpath("//button[@id='SubmitLogin']/span")).click();
        Assert.assertTrue(driver.findElement(By.className("alert-danger")).isDisplayed());
    }

    @Test
    public void incorrectRegistration() {
        driver.findElement(By.linkText("Sign in")).click();
        driver.findElement(By.name("email_create")).click();
        driver.findElement(By.name("email_create")).sendKeys("test@pjwstk.edu.pl");
        driver.findElement(By.xpath("//button[@id='SubmitCreate']/span")).click();
        WebDriverWait wait = new WebDriverWait(driver, 100);
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@id='submitAccount']/span"))).click();
        Assert.assertTrue(driver.findElement(By.className("alert-danger")).isDisplayed());
    }

    @After
    public void cleanup() {
        driver.quit();
    }

}
