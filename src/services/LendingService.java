package services;

import enums.BookStatus;
import enums.LendingStatus;
import exceptions.BookNotAvailableException;
import exceptions.BookNotFoundException;
import exceptions.PatronNotFoundException;
import models.Book;
import models.LendingRecord;
import models.Library;
import models.Patron;

import java.util.List;
import java.util.logging.Logger;

public class LendingService {
    private static final Logger logger = Logger.getLogger(LendingService.class.getName());
    private final Library library;
    private final BookService bookService;
    private final PatronService patronService;

    public LendingService(Library library) {
        this.library = library;
        this.bookService = new BookService(library);
        this.patronService = new PatronService(library);
    }

    public LendingRecord checkoutBook(String patronId, String bookId)
            throws PatronNotFoundException, BookNotFoundException, BookNotAvailableException {
        Patron patron = patronService.getPatronById(patronId);
        Book book = bookService.getBookById(bookId);

        if (!book.isAvailable()) {
            throw new BookNotAvailableException("Book is not available for checkout");
        }

        LendingRecord record = new LendingRecord(bookId, patronId);
        library.getLendingRecords().put(record.getId(), record);

        book.setStatus(BookStatus.CHECKED_OUT);
        patron.borrowBook(bookId);
        patron.addToBorrowingHistory(bookId);

        library.notifyBookCheckedOut(patronId, bookId);
        logger.info("Book checked out: " + book.getTitle() + " by " + patron.getName());

        return record;
    }

    public void returnBook(String patronId, String bookId)
            throws PatronNotFoundException, BookNotFoundException {
        Patron patron = patronService.getPatronById(patronId);
        Book book = bookService.getBookById(bookId);

        if (!patron.hasBorrowedBook(bookId)) {
            throw new IllegalStateException("Patron has not borrowed this book");
        }

        LendingRecord record = findActiveLendingRecord(patronId, bookId);
        if (record != null) {
            record.markAsReturned();
        }

        book.setStatus(BookStatus.AVAILABLE);
        patron.returnBook(bookId);

        library.notifyBookReturned(patronId, bookId);
        logger.info("Book returned: " + book.getTitle() + " by " + patron.getName());
    }

    private LendingRecord findActiveLendingRecord(String patronId, String bookId) {
        return library.getLendingRecords().values().stream()
                .filter(r -> r.getPatronId().equals(patronId) &&
                            r.getBookId().equals(bookId) &&
                            r.getStatus() == LendingStatus.ACTIVE)
                .findFirst()
                .orElse(null);
    }

    public List<LendingRecord> getOverdueRecords() {
        return library.getLendingRecords().values().stream()
                .filter(LendingRecord::isOverdue)
                .toList();
    }
}