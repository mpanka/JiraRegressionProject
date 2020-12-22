import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import pageObjects.LoginPage;
import pageObjects.MainPage;
import utils.Util;

import java.net.MalformedURLException;
import java.net.URL;

public class TestScript {

    public TestScript() throws MalformedURLException {
    }

    private MutableCapabilities setOption() {
        MutableCapabilities mutableCapabilities;

        if (System.getenv("STAGE_NAME").equals("run with chrome")) {
            mutableCapabilities = new ChromeOptions();
        } else {
            mutableCapabilities = new FirefoxOptions();
        }
        return mutableCapabilities;
    }

    MutableCapabilities mutCapAsOptions = setOption();

    private final WebDriver driver = new RemoteWebDriver(new URL("https://selenium:" + Util.SELENIUM_PASSWORD + "@seleniumhub.codecool.codecanvas.hu/wd/hub"), mutCapAsOptions);

    private final LoginPage login = new LoginPage(driver);
    private final MainPage mainPage = new MainPage(driver);

    @BeforeSuite
    public void setup() {
        System.setProperty(Util.WEBDRIVER, Util.CHROME_DRIVER_LOCATION);
        login.openBaseUrl();
        mainPage.resetSidebar();
        login.openBaseUrl();
    }

    @Test
    public void successfulLogin () {
        login.login();
        mainPage.pressProfileImage();
        Assert.assertTrue("logout button should be available", mainPage.validateLogoutButtonIsAvailable());
        mainPage.pressLogoutButton();
    }

    @Test
    public void loginWithoutUserInfos () {
        login.openBaseUrl();
        login.pressLoginButton();
        Assert.assertEquals(login.validateErrorMessage(), "Sorry, your username and password are incorrect - please try again.");
    }

    @Test
    public void loginWithoutPassword () {
        login.openBaseUrl();
        login.enterValidUsername();
        login.pressLoginButton();
        Assert.assertEquals(login.validateErrorMessage(), "Sorry, your username and password are incorrect - please try again.");
    }

    @Test
    public void loginWithInvalidPassword () {
        login.openBaseUrl();
        login.enterValidUsername();
        login.enterInvalidPassword();
        login.pressLoginButton();
        Assert.assertEquals(login.validateErrorMessage(), "Sorry, your username and password are incorrect - please try again.");
    }

    @Test
    public void successfulLogout () {
        login.login();
        mainPage.logout();
        Assert.assertTrue("a login button should be available", mainPage.validateLogoutTitle());
    }

    @AfterSuite
    public void cleanUp() {
        driver.manage().deleteAllCookies();
        driver.quit();
    }
}
