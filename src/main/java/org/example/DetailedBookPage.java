import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class DetailedBookPage {
  private WebDriver driver;
  private String bookUrl;
  private WebDriverWait wait;

  public DetailedBookPage(WebDriver driver, String bookUrl) {
    this.driver = driver;
    this.bookUrl = bookUrl;
    this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
  }

  public Book extractBookDetails() {
    driver.get(bookUrl);

    WebElement titleElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("productTitle")));
    String title = titleElement.getText();

    WebElement authorElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("bylineInfo")));
    String author = authorElement.getText();
    author = formatAuthorText(author);
    boolean isBestseller = !driver.findElements(By.cssSelector(".zg-badge-wrapper")).isEmpty();

    String price = getPrice();
    Book expectedBook = new Book(title, author, price, isBestseller);

    // Выводим данные о книге в консоль для отладки
    System.out.println("Очікувана книга:");
    System.out.println("Назва: " + expectedBook.getTitle());
    System.out.println("Автор: " + expectedBook.getAuthor());
    System.out.println("Ціна: " + expectedBook.getPrice());
    System.out.println("Бестселлер: " + expectedBook.isBestseller());

    return expectedBook;
  }

  private String getPrice() {
    WebElement priceElement;
    try {
      WebElement kindleBlock = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("tmm-grid-swatch-KINDLE")));
      kindleBlock.click();
      WebElement kindlePriceElement = driver.findElement(By.id("kindle-price"));
      if (kindlePriceElement.isDisplayed()) {
        priceElement = kindlePriceElement;
      } else {
        priceElement = driver.findElement(By.id("rental-header-price"));
      }

    } catch (Exception e) {
      System.out.println("Елемент 'tmm-grid-swatch-KINDLE' НЕ знайдено, шукаємо id 'tmm-grid-swatch-PAPERBACK'");
      WebElement paperbackBlock = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("tmm-grid-swatch-PAPERBACK")));
      paperbackBlock.click();
      WebElement paperbackPriceElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("span.a-price")));
      if(paperbackPriceElement.isDisplayed()) {
        priceElement = paperbackPriceElement;
      }
      else {
        priceElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("span.slot-price")));
      }

    }
    return priceElement.getText().replace("$", "");
  }

  private String formatAuthorText(String author) {
    author = author.replace("by ", "").trim();
    author = author.replaceAll("\\(Author\\)", "").trim();
    int commaIndex = author.indexOf(",");
    if (commaIndex != -1) {
      author = author.substring(0, commaIndex).trim();
    }
    int pipeIndex = author.lastIndexOf("|");
    if (pipeIndex != -1) {
      author = author.substring(0, pipeIndex).trim();
    }
    return author;
  }


}
