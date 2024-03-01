import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class SearchResultPage {
  private List<WebElement> searchResults;

  public SearchResultPage(List<WebElement> searchResults) {
    this.searchResults = searchResults;
  }

  public ArrayList<Book> extractBooks() {
    ArrayList<Book> listOfBooks = new ArrayList<>();
    for (WebElement element : searchResults) {
      String title = element.findElement(By.cssSelector("h2.a-size-mini")).getText();
      String url = element.findElement(By.cssSelector("a.a-link-normal")).getAttribute("href");
      String author = element.findElement(By.cssSelector(".a-row.a-size-base.a-color-secondary")).getText().replace("by ", "");

      int pipeIndex = author.lastIndexOf("|");
      if (pipeIndex != -1) {
        author = author.substring(0, pipeIndex).trim();
      }

      boolean isBestseller = !element.findElements(By.xpath(".//span[@class='a-badge-text']")).isEmpty();

      WebElement priceWhole = element.findElement(By.cssSelector("span.a-price-whole"));
      WebElement priceFraction = element.findElement(By.cssSelector("span.a-price-fraction"));
      String wholePart = priceWhole.getText();
      String fractionPart = priceFraction.getText();
      String price = wholePart + "." + fractionPart;

      listOfBooks.add(new Book(title, author, price, isBestseller, url));
    }
    return listOfBooks;
  }

  public boolean checkForBestsellers(ArrayList<Book> books) {
    boolean isBestsellerFound = false;

    for (Book book : books) {
      if(book.isBestseller()) {
        isBestsellerFound = true;
        break;
      }
    }
    if (!isBestsellerFound) {
      System.out.println("Бестселлера на странице не найдено");
    }

    return isBestsellerFound;
  }

  public void printBooks(ArrayList<Book> books) {
    for (Book book : books) {
      System.out.println(book);
    }
  }

  public boolean assertTargetBookInList(ArrayList<Book> books, String targetBookUrl) {
    boolean isTargetBookFound = false;
    String baseUrl = targetBookUrl.split("\\?")[0];
    for (Book book : books) {
      if (book.getUrl().startsWith(baseUrl)) {
        isTargetBookFound = true;
        break;
      }
    }
    if (isTargetBookFound) {
      System.out.println("Книга найдена в списке.");
    } else {
      System.out.println("Книга не найдена в списке.");
    }
    return isTargetBookFound;
  }
}
