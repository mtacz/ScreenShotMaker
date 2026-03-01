package page;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.Duration;

public class ScreenShotPage {

    private final WebDriver driver;
    private final WebDriverWait wait;
    private final By searchBar = By.name("q");

    public ScreenShotPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    public ScreenShotPage openPage() {
        driver.get("https://duckduckgo.com/");
        wait.until(ExpectedConditions.visibilityOfElementLocated(searchBar));
        return this;
    }

    public ScreenShotPage clickOnSearchBar() {
        WebElement searchInput = waitForElement(searchBar);
        searchInput.click();
        return this;
    }

    public ScreenShotPage enterSearch(String query) {
        WebElement searchInput = waitForElement(searchBar);
        searchInput.clear();
        searchInput.sendKeys(query);
        return this;
    }

    public ScreenShotPage clickSubmit() {
        WebElement searchInput = waitForElement(searchBar);
        searchInput.submit();
        return this;
    }

    public ScreenShotPage waitForLoading(String input) {
        wait.until(ExpectedConditions.titleContains(input));
        return this;
    }

    private WebElement waitForElement(By webElement) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(webElement));
    }

    public ScreenShotPage makeScreenshot(String fileName) {

        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

        Path path = Paths.get("screenshot", fileName + ".png");
        try {
            Files.createDirectories(path.getParent());
            Files.copy(scrFile.toPath(), path, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("Failed to save screenshot" + fileName, e);
        }
        return this;
    }
}