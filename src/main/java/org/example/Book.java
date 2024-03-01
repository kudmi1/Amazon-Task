package org.example;

import java.util.Objects;

public class Book {
    private String title;
    private String author;
    private String price;
    private boolean isBestseller;

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Book other = (Book) obj;
        return Objects.equals(title, other.title) &&
                Objects.equals(author, other.author) &&
                Objects.equals(price, other.price) &&
                isBestseller == other.isBestseller;
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, author, price, isBestseller);
    }

    public Book(String title, String author, String price, boolean isBestseller) {
        this.title = title;
        this.author = author;
        this.price = price;
        this.isBestseller = isBestseller;
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


    @Override
    public String toString() {
        return "Book {" + "\n" +
                "Title: " + title + "\n" +
                "Author: " + author + "\n" +
                "Price = " + price + "\n" +
                "Bestseller = " + isBestseller + "\n" +
                '}';
    }
}