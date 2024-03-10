package org.example;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestBase {
  // protected WebDriver driver;
  protected WebDriver driver;
  protected WebDriverWait wait;

  @BeforeEach
  public void setUp() {
    // ChromeOptions options = new ChromeOptions();
    // options.addArguments("--headless");
    // driver = new ChromeDriver(options);
    driver = new ChromeDriver();
    wait = new WebDriverWait(driver, Config.DURATION);
  }

  @AfterAll
  public void tearDown() {
    if (driver != null) {
      driver.quit();
    }
  }
}
