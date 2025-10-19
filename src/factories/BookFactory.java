package factories;

import models.Book;

public class BookFactory {
    public Book createBook(String title, String author, int yearPublished, String isbn) {
        return new Book(title, author, yearPublished, isbn);
    }

    public Book createClassicBook(String title, String author, int yearPublished, String isbn) {
        // Could add special handling for classic books
        return new Book(title, author, yearPublished, isbn);
    }

    public Book createModernBook(String title, String author, int yearPublished, String isbn) {
        // Could add special handling for modern books
        return new Book(title, author, yearPublished, isbn);
    }
}