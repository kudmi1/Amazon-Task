public class Book {
    private String title;
    private String author;
    private String price;
    private boolean isBestseller;
    private String url;

    public Book(String title, String author, String price, boolean isBestseller, String url) {
        this.title = title;
        this.author = author;
        this.price = price;
        this.isBestseller = isBestseller;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }
    public String getAuthor() {
        return author;
    }
    public String getPrice() {
        return price;
    }
    public boolean isBestseller() {
        return isBestseller;
    }
    public String getUrl() {
        return url;
    }

    @Override
    public String toString() {
        return "Книга {" + "\n" +
                "Назва: " + title + ", " + "\n" +
                "Автор: " + author + ", " + "\n" +
                "Ціна = " + price + ", " + "\n" +
                "Бестселлер = " + isBestseller + "\n" +
                "Посилання = " + url + "\n" +
                '}';
    }
}