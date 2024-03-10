package org.example.tests;
import org.example.*;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebElement;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

public class AmazonParserTest extends TestBase {
  @Test
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

    assertTrue(listOfBooks.contains(expectedBook), "Expected book not found");
  
  }
  // public void assertBookFound(ArrayList<Book> listOfBooks, Book expectedBook) {
  //   assertTrue(listOfBooks.contains(expectedBook), "Expected book not found");
    
  // }
}
