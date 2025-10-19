package models;

import enums.BookStatus;
import java.util.Objects;
import java.util.UUID;

public class Book {
    private final String id;
    private final String isbn;
    private String title;
    private String author;
    private int yearPublished;
    private BookStatus status;

    public Book(String title, String author, int yearPublished, String isbn) {
        this.id = UUID.randomUUID().toString();
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.yearPublished = yearPublished;
        this.status = BookStatus.AVAILABLE;
    }

    public String getId() { return id; }
    public String getIsbn() { return isbn; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public int getYearPublished() { return yearPublished; }
    public BookStatus getStatus() { return status; }

    public void setTitle(String title) { this.title = title; }
    public void setAuthor(String author) { this.author = author; }
    public void setYearPublished(int yearPublished) { this.yearPublished = yearPublished; }
    public void setStatus(BookStatus status) { this.status = status; }

    public boolean isAvailable() {
        return status == BookStatus.AVAILABLE;
    }

    @Override
    public String toString() {
        return String.format("Book[id=%s, title='%s', author='%s', year=%d, isbn=%s, status=%s]",
                id, title, author, yearPublished, isbn, status);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book)) return false;
        Book book = (Book) o;
        return id.equals(book.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}