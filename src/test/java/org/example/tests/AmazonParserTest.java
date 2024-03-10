package org.example.tests;
import org.example.*;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebElement;

import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

public class AmazonParserTest extends TestBase {
  @Test
  @Severity(SeverityLevel.NORMAL)
  @Description("ТЕСТ АМАЗОНА")
  @Owner("ДМИТРИЙ КУЩИК")
  public void testAmazonSearch() {
    AmazonPage amazonPage = new AmazonPage(driver);
    Allure.step("Go to Amazon Home Page");
    amazonPage.goToHomePage();

    Allure.step("Apply Category Filter");
    amazonPage.applyCategoryFilter(Config.CATEGORY);

    Allure.step("Search for Java");
    amazonPage.searchFor(Config.SEARCH_KEYWORD);

    Allure.step("Get Search Results");
    List<WebElement> bookElements = amazonPage.getSearchResults();

    SearchResultPage searchResultPage = new SearchResultPage(bookElements, driver);

    Allure.step("Extract Books");
    ArrayList<Book> listOfBooks = searchResultPage.extractBooks();

    Allure.step("Print Books");
    searchResultPage.printBooks(listOfBooks);

    String expectedBookUrl = Config.BOOK_TO_CHECK_URL;
    DetailedBookPage detailedBookPage = new DetailedBookPage(driver, expectedBookUrl);
    Allure.step("Extract Book Details");
    Book expectedBook = detailedBookPage.extractBookDetails();
    
    Allure.step("Check if expected book is in list of books");
    assertTrue(listOfBooks.contains(expectedBook), "Expected book not found");
  
  } 
}
