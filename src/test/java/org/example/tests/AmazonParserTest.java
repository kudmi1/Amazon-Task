package org.example.tests;
import org.example.*;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.WebElement;

import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Issue;
import io.qameta.allure.Link;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Step;

import java.util.ArrayList;
import java.util.List;

public class AmazonParserTest extends TestBase {
  @Test
  @Severity(SeverityLevel.NORMAL)
  @Description("Search for a book and check if it is present in the search results")
  @Link("https://www.amazon.com")
  @Issue("BUG-12345")
  public void testAmazonSearch() {
    AmazonPage amazonPage = new AmazonPage(driver);
    amazonPage.goToHomePage();
    amazonPage.applyCategoryFilter(Config.CATEGORY);
    amazonPage.searchFor(Config.SEARCH_KEYWORD);

    List<WebElement> bookElements = amazonPage.getSearchResults();

    SearchResultPage searchResultPage = new SearchResultPage(bookElements, driver);

    ArrayList<Book> listOfBooks = searchResultPage.extractBooks();
    searchResultPage.printBooks(listOfBooks);
    String expectedBookUrl = Config.BOOK_TO_CHECK_URL;
    DetailedBookPage detailedBookPage = new DetailedBookPage(driver, expectedBookUrl);

    Book expectedBook = detailedBookPage.extractBookDetails();
    // Assert.assertTrue("Expected book not found", listOfBooks.contains(expectedBook));
    assertBookFound(listOfBooks, expectedBook);
  
  }
  @Step("Assert expected book found")
  public void assertBookFound(ArrayList<Book> listOfBooks, Book expectedBook) {
    Assert.assertTrue("Expected book not found", listOfBooks.contains(expectedBook));
  }
}
