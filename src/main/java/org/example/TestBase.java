import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class TestBase {
  protected WebDriver driver;
  protected WebDriverWait wait;

  @Before
  public void setUp() {
    ChromeOptions options = new ChromeOptions();
    options.addArguments("--headless");
    driver = new ChromeDriver(options);
    wait = new WebDriverWait(driver, Config.DURATION);
  }

  @After
  public void tearDown() {
    if (driver != null) {
      driver.quit();
    }
  }
}
