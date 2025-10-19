import models.Book;
import models.Library;
import models.Patron;
import services.BookService;
import services.LendingService;
import services.PatronService;
import services.SearchService;
import observers.NotificationService;
import strategies.SearchByAuthor;
import strategies.SearchByISBN;
import strategies.SearchByTitle;

import java.util.List;
import java.util.logging.Logger;

public class Main {
    private static final Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        logger.info("Starting Library Management System");

        // Initialize library and services
        Library library = new Library("Central Library");
        BookService bookService = new BookService(library);
        PatronService patronService = new PatronService(library);
        LendingService lendingService = new LendingService(library);
        SearchService searchService = new SearchService(library);

        // Register observers
        NotificationService notificationService = new NotificationService();
        library.addObserver(notificationService);

        // Demonstrate functionality
        demonstrateBookManagement(bookService);
        demonstratePatronManagement(patronService);
        demonstrateLending(bookService, patronService, lendingService);
        demonstrateSearch(searchService);

        logger.info("Library Management System demonstration completed");
    }

    private static void demonstrateBookManagement(BookService bookService) {
        System.out.println("\n=== Book Management Demo ===");

        // Add books
        Book book1 = bookService.addBook("The Great Gatsby", "F. Scott Fitzgerald", 1925, "978-0743273565");
        Book book2 = bookService.addBook("To Kill a Mockingbird", "Harper Lee", 1960, "978-0061120084");
        Book book3 = bookService.addBook("1984", "George Orwell", 1949, "978-0451524935");

        System.out.println("Added books:");
        bookService.getAllBooks().forEach(System.out::println);

        // Update book
        try {
            bookService.updateBook(book1.getId(), "The Great Gatsby - Revised", null, null);
            System.out.println("\nUpdated book: " + bookService.getBookById(book1.getId()));
        } catch (exceptions.BookNotFoundException e) {
            System.err.println("Book update failed: " + e.getMessage());
        }
    }

    private static void demonstratePatronManagement(PatronService patronService) {
        System.out.println("\n=== Patron Management Demo ===");

        // Add patrons
        Patron patron1 = patronService.addPatron("John Doe", "john.doe@email.com", "123-456-7890");
        Patron patron2 = patronService.addPatron("Jane Smith", "jane.smith@email.com", "098-765-4321");

        System.out.println("Registered patrons:");
        patronService.getAllPatrons().forEach(System.out::println);
    }

    private static void demonstrateLending(BookService bookService, PatronService patronService,
                                          LendingService lendingService) {
        System.out.println("\n=== Lending Process Demo ===");

        Book book = bookService.getAllBooks().get(0);
        Patron patron = patronService.getAllPatrons().get(0);

        // Checkout book
        try {
            lendingService.checkoutBook(patron.getId(), book.getId());
            System.out.println("Book checked out successfully");
            System.out.println("Patron's borrowing history: " + patron.getBorrowingHistory().size() + " books");
        } catch (Exception e) {
            System.err.println("Checkout failed: " + e.getMessage());
        }

        // Return book
        try {
            lendingService.returnBook(patron.getId(), book.getId());
            System.out.println("Book returned successfully");
        } catch (Exception e) {
            System.err.println("Return failed: " + e.getMessage());
        }
    }

    private static void demonstrateSearch(SearchService searchService) {
        System.out.println("\n=== Search Functionality Demo ===");

        // Search by title
        searchService.setSearchStrategy(new SearchByTitle());
        List<Book> titleResults = searchService.search("1984");
        System.out.println("Books with title '1984': " + titleResults.size());

        // Search by author
        searchService.setSearchStrategy(new SearchByAuthor());
        List<Book> authorResults = searchService.search("Harper Lee");
        System.out.println("Books by Harper Lee: " + authorResults.size());

        // Search by ISBN
        searchService.setSearchStrategy(new SearchByISBN());
        List<Book> isbnResults = searchService.search("978-0743273565");
        System.out.println("Books with ISBN '978-0743273565': " + isbnResults.size());
    }
}