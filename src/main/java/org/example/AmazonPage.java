package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
// import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


import java.util.List;

public class AmazonPage {
  private WebDriver driver;
  private WebDriverWait wait;

  public AmazonPage(WebDriver driver) {
    this.driver = driver;
    wait = new WebDriverWait(driver, Config.DURATION);
  }

  public void goToHomePage() {
    driver.get(Config.AMAZON_URL);
  }
  public void applyCategoryFilter(String category) {
    WebElement categoryFilter  = driver.findElement(By.cssSelector("#nav-search-dropdown-card"));
    categoryFilter .click();
    WebElement categoryOption = driver.findElement(
            By.cssSelector("#searchDropdownBox option[value='search-alias=" + category + "-intl-ship']"));
    categoryOption.click();
  }
  public void searchFor(String keyword) {
    WebElement searchInput = driver.findElement(By.cssSelector("#twotabsearchtextbox"));
    searchInput.sendKeys(keyword);
    searchInput.submit();
  }
  public List<WebElement> getSearchResults() {
  //   return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
  //           // By.cssSelector("[data-component-type='s-search-result']")));
  //           By.xpath("//div[@data-component-type='s-search-result']")));

    return driver.findElements(By.xpath("//div[@data-component-type='s-search-result']"));
  }
}
