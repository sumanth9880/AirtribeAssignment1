package services;

import enums.BookStatus;
import exceptions.BookNotFoundException;
import factories.BookFactory;
import models.Book;
import models.Library;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class BookService {
    private static final Logger logger = Logger.getLogger(BookService.class.getName());
    private final Library library;
    private final BookFactory bookFactory;

    public BookService(Library library) {
        this.library = library;
        this.bookFactory = new BookFactory();
    }

    public Book addBook(String title, String author, int yearPublished, String isbn) {
        Book book = bookFactory.createBook(title, author, yearPublished, isbn);
        library.getBooks().put(book.getId(), book);
        logger.info("Book added: " + book.getTitle());
        return book;
    }

    public void removeBook(String bookId) throws BookNotFoundException {
        Book book = library.getBooks().remove(bookId);
        if (book == null) {
            throw new BookNotFoundException("Book with ID " + bookId + " not found");
        }
        logger.info("Book removed: " + book.getTitle());
    }

    public void updateBook(String bookId, String title, String author, Integer yearPublished)
            throws BookNotFoundException {
        Book book = getBookById(bookId);
        if (title != null) book.setTitle(title);
        if (author != null) book.setAuthor(author);
        if (yearPublished != null) book.setYearPublished(yearPublished);
        logger.info("Book updated: " + book.getTitle());
    }

    public Book getBookById(String bookId) throws BookNotFoundException {
        Book book = library.getBooks().get(bookId);
        if (book == null) {
            throw new BookNotFoundException("Book with ID " + bookId + " not found");
        }
        return book;
    }

    public List<Book> getAllBooks() {
        return new ArrayList<>(library.getBooks().values());
    }

    public void updateBookStatus(String bookId, BookStatus status) throws BookNotFoundException {
        Book book = getBookById(bookId);
        book.setStatus(status);
        logger.info("Book status updated: " + book.getTitle() + " -> " + status);
    }

    public List<Book> getAvailableBooks() {
        return library.getBooks().values().stream()
                .filter(Book::isAvailable)
                .toList();
    }
}