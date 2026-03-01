package test;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import page.ScreenShotPage;

import static org.testng.Assert.assertTrue;

public class ScreenShotMakerTests {

    private WebDriver driver;

    @BeforeMethod
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @Test
    public void screenshotMakerTest() {
        ScreenShotPage screenShotPage = new ScreenShotPage(driver);
        String input = "Kurs Selenium";
        screenShotPage.openPage()
                .clickOnSearchBar()
                .makeScreenshot("BeforeTypingQueryPage")
                .enterSearch(input)
                .makeScreenshot("AfterTypingQueryPage")
                .clickSubmit()
                .waitForLoading(input)
                .makeScreenshot("SearchResultsPage");

        assertTrue(driver.getTitle().contains(input));
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}