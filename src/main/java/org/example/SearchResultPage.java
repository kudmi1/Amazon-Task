package org.example;

import org.example.Book;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;

public class SearchResultPage {
  private List<WebElement> searchResults;
  private WebDriverWait wait;

  public SearchResultPage(List<WebElement> searchResults, WebDriver driver) {
    this.searchResults = searchResults;
    this.wait = new WebDriverWait(driver, Config.DURATION);
  }

  private String formatAuthorText(String authorText) {
    int byIndex = authorText.indexOf("by");
    if (byIndex != -1) {
      authorText = authorText.substring(byIndex + 3).trim();
    }

    int commaIndex = authorText.indexOf(",");
    if (commaIndex != -1) {
      authorText = authorText.substring(0, commaIndex).trim();
    }

    int pipeIndex = authorText.indexOf("|");
    if (pipeIndex != -1) {
      authorText = authorText.substring(0, pipeIndex).trim();
    }
    return authorText;
  }

  public ArrayList<Book> extractBooks() {
    ArrayList<Book> listOfBooks = new ArrayList<>();
    for (WebElement element : searchResults) {
      String title = wait.until(ExpectedConditions.visibilityOf(element.findElement(By.cssSelector("h2.a-size-mini")))).getText();
      String authorText = wait.until(ExpectedConditions.visibilityOf(element.findElement(By.cssSelector(".a-row.a-size-base.a-color-secondary")))).getText();
      authorText = formatAuthorText(authorText);

      boolean isBestseller = !element.findElements(By.xpath(".//span[@class='a-badge-text']")).isEmpty();

//      String priceWhole = element.findElement(By.cssSelector("span.a-price-whole")).getText();
      String priceWhole = element.findElement(By.xpath(".//span[@class='a-price-whole']")).getText();
      String priceFraction = element.findElement(By.xpath(".//span[@class='a-price-fraction']")).getText();
//      String priceFraction = element.findElement(By.cssSelector("span.a-price-fraction")).getText();
      String price = priceWhole + "." + priceFraction;

      listOfBooks.add(new Book(title, authorText, price, isBestseller));
    }
    return listOfBooks;
  }

  public void printBooks(ArrayList<Book> books) {
    for (Book book : books) {
      System.out.println(book);
    }
  }
}
