import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;


public class AmazonParser {

    @Test
    public void testAmazonSearch() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        WebDriver driver = new ChromeDriver(options);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.get("https://www.amazon.com/");

        // Установлюємо фільтр Book
        WebElement bookFilter = driver.findElement(By.cssSelector("#nav-search-dropdown-card"));
        bookFilter.click();
        WebElement bookOption = driver.findElement(By.cssSelector("#searchDropdownBox option[value='search-alias=stripbooks-intl-ship']"));
        bookOption.click();

        // Вводимо ключове слово Java
        WebElement searchInput = driver.findElement(By.cssSelector("#twotabsearchtextbox"));
        searchInput.sendKeys("Java");
        searchInput.submit();

        // Створюємо List Book, та беремо усі видані на сторінці результати книг
        ArrayList<Book> books = new ArrayList<>();
        List<WebElement> bookElements = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("[data-component-type='s-search-result']")));

        // Проходимо по кожній книжці
        for (WebElement element : bookElements) {
            // Вилучаємо назву
            WebElement titleElement = wait.until(ExpectedConditions.visibilityOf(element.findElement(By.cssSelector("h2.a-size-mini"))));
            String title = titleElement.getText();

            // Вилучаємо посилання на книгу
            String url = element.findElement(By.cssSelector("a.a-link-normal")).getAttribute("href");

            // Вилучаємо автора та вирізаємо дату після пайпу
            WebElement authorElement = wait.until(ExpectedConditions.visibilityOf(element.findElement(By.cssSelector(".a-row.a-size-base.a-color-secondary"))));
            String author = authorElement.getText().replace("by ", "");
            int pipeIndex = author.lastIndexOf("|");
            if (pipeIndex != -1) {
                author = author.substring(0, pipeIndex).trim();
            }

            // Перевіряємо чи являється книга бестселлером
            boolean isBestseller = false;
            try {
                WebElement bestsellerElement = element.findElement(By.xpath(".//span[@class='a-badge-text']"));
                isBestseller = bestsellerElement != null;
            } catch (Exception e) {

            }

            // Беремо повну та часткову ціну та об'єднуємо в повну ціну
            WebElement priceWhole = element.findElement(By.cssSelector("span.a-price-whole"));
            WebElement priceFraction = element.findElement(By.cssSelector("span.a-price-fraction"));
            String wholePart = priceWhole.getText();
            String fractionPart = priceFraction.getText();
            String price = wholePart + "." + fractionPart;

            books.add(new Book(title, author, price, isBestseller, url));

        }

        // перевіряємо щоб ліст не був пустим
        Assert.assertFalse(books.isEmpty());

        // Виводимо у консоль данні кожної книги
        for (Book book : books) {
            System.out.println(book);
        }

        // Перевіряємо наявність певної книги у списку і виводимо у консоль результат перевірки
        String targetBookUrl = "https://www.amazon.com/Head-First-Java-Kathy-Sierra/dp/0596009208/ref=sr_1_2?dchild=1&keywords=Java&qid=1610356790&s=books&sr=1-2";
        boolean isTargetBookFound = false;
        String baseUrl = targetBookUrl.split("\\?")[0];
        for (Book book : books) {
            if (book.getUrl().startsWith(baseUrl)) {
                isTargetBookFound = true;
                break;
            }
        }

        if (isTargetBookFound) {
            System.out.println("Книга знайдена у списку.");
        } else {
            System.out.println("Книга НЕ знайдена у списку.");
        }


        Assert.assertTrue("Книга НЕ знайдена у списку.", isTargetBookFound);

        driver.quit();
    }

}