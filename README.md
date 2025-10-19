# AirtribeAssignment1
This repository contains the solution for Assignment 1 of the Airtribe internship program. The assignment involves a comprehensive library management system built in Java that demonstrates object-oriented programming principles and design patterns.
## Features
- **Book Management**: Add, update, remove, and search books
- **Patron Management**: Manage library members and their borrowing history
- **Lending System**: Check out and return books with tracking
- **Search Functionality**: Search books by title, author, or ISBN
- **Notification System**: Real-time notifications for library events
- **Status Tracking**: Monitor book availability and lending status

## Design Patterns Implemented
- **Observer Pattern**: Real-time notifications for library events
- **Strategy Pattern**: Flexible search strategies (by title, author, ISBN)
- **Factory Pattern**: Centralized book creation
- **Service Layer Pattern**: Separation of business logic

## Project Structure

## Class Diagram
classDiagram
    %% Main Classes
    class Main {
        -Logger logger
        +main(String[] args)
        -demonstrateBookManagement(BookService)
        -demonstratePatronManagement(PatronService)
        -demonstrateLending(BookService, PatronService, LendingService)
        -demonstrateSearch(SearchService)
    }

    %% Models
    class Library {
        -String name
        -List~Book~ books
        -List~Patron~ patrons
        -List~LendingRecord~ lendingRecords
        -List~LibraryObserver~ observers
        +addBook(Book)
        +removeBook(Book)
        +addPatron(Patron)
        +addLendingRecord(LendingRecord)
        +addObserver(LibraryObserver)
        +notifyObservers(String)
    }

    class Book {
        -String id
        -String title
        -String author
        -int publicationYear
        -String isbn
        -BookStatus status
        +getId() String
        +getTitle() String
        +getStatus() BookStatus
        +setStatus(BookStatus)
    }

    class Patron {
        -String id
        -String name
        -String email
        -String phoneNumber
        -List~String~ borrowingHistory
        +getId() String
        +getName() String
        +getBorrowingHistory() List~String~
        +addToBorrowingHistory(String)
    }

    class LendingRecord {
        -String id
        -String patronId
        -String bookId
        -LocalDate checkoutDate
        -LocalDate dueDate
        -LocalDate returnDate
        -LendingStatus status
        +getPatronId() String
        +getBookId() String
        +getStatus() LendingStatus
        +setReturnDate(LocalDate)
    }

    %% Enums
    class BookStatus {
        <<enumeration>>
        AVAILABLE
        CHECKED_OUT
        RESERVED
        MAINTENANCE
    }

    class LendingStatus {
        <<enumeration>>
        ACTIVE
        RETURNED
        OVERDUE
    }

    %% Services
    class BookService {
        -Library library
        +addBook(String, String, int, String) Book
        +updateBook(String, String, String, Integer)
        +removeBook(String)
        +getBookById(String) Book
        +getAllBooks() List~Book~
    }

    class PatronService {
        -Library library
        +addPatron(String, String, String) Patron
        +updatePatron(String, String, String)
        +removePatron(String)
        +getPatronById(String) Patron
        +getAllPatrons() List~Patron~
    }

    class LendingService {
        -Library library
        +checkoutBook(String, String)
        +returnBook(String, String)
        +getActiveLendingRecords() List~LendingRecord~
    }

    class SearchService {
        -Library library
        -SearchStrategy strategy
        +setSearchStrategy(SearchStrategy)
        +search(String) List~Book~
    }

    %% Observers
    class LibraryObserver {
        <<interface>>
        +update(String message)
    }

    class NotificationService {
        +update(String message)
    }

    %% Strategies
    class SearchStrategy {
        <<interface>>
        +search(List~Book~, String) List~Book~
    }

    class SearchByTitle {
        +search(List~Book~, String) List~Book~
    }

    class SearchByAuthor {
        +search(List~Book~, String) List~Book~
    }

    class SearchByISBN {
        +search(List~Book~, String) List~Book~
    }

    %% Factory
    class BookFactory {
        +createBook(String, String, int, String) Book
    }

    %% Exceptions
    class BookNotFoundException {
        <<exception>>
        +BookNotFoundException(String)
    }

    class PatronNotFoundException {
        <<exception>>
        +PatronNotFoundException(String)
    }

    class BookNotAvailableException {
        <<exception>>
        +BookNotAvailableException(String)
    }

    %% Relationships
    Main --> Library
    Main --> BookService
    Main --> PatronService
    Main --> LendingService
    Main --> SearchService
    Main --> NotificationService

    Library "1" *-- "*" Book
    Library "1" *-- "*" Patron
    Library "1" *-- "*" LendingRecord
    Library "1" o-- "*" LibraryObserver

    Book --> BookStatus
    LendingRecord --> LendingStatus

    BookService --> Library
    BookService ..> Book
    BookService ..> BookFactory
    BookService ..> BookNotFoundException

    PatronService --> Library
    PatronService ..> Patron
    PatronService ..> PatronNotFoundException

    LendingService --> Library
    LendingService ..> LendingRecord
    LendingService ..> BookNotFoundException
    LendingService ..> PatronNotFoundException
    LendingService ..> BookNotAvailableException

    SearchService --> Library
    SearchService --> SearchStrategy
    SearchService ..> Book

    NotificationService ..|> LibraryObserver
    SearchByTitle ..|> SearchStrategy
    SearchByAuthor ..|> SearchStrategy
    SearchByISBN ..|> SearchStrategy

    BookFactory ..> Book


## Key Components
### Models
- **Book**: Represents a book with title, author, ISBN, and status 
- **Patron**: Represents a library member with contact details and borrowing history 
- **LendingRecord**: Tracks book checkout and return transactions 
- **Library**: Central repository for books, patrons, and lending records

### Services
- **BookService**: Handles all book-related operations
- **PatronService**: Manages patron information
- **LendingService**: Handles checkout and return processes 
- **SearchService**: Provides flexible book search capabilities

### Design Pattern Details
#### Observer Pattern
The notification system uses the Observer pattern to notify subscribers about library events:
- **LibraryObserver**: Interface for observers
- **NotificationService**: Concrete observer implementation
- **Library**: Subject that maintains and notifies observers

#### Strategy Pattern
Search functionality uses the Strategy pattern for flexible search criteria:
- **SearchStrategy**: Interface defining search contract
- **SearchByTitle**, SearchByAuthor, SearchByISBN: Concrete strategies
- **SearchService**: Context that uses strategies

#### Factory Pattern
Book creation is centralized using the Factory pattern:
- **BookFactory**: Responsible for creating Book instances

### Exception Handling
The system uses custom exceptions for better error handling:
- **BookNotFoundException**: Thrown when a book is not found
- **PatronNotFoundException**: Thrown when a patron is not found
- **BookNotAvailableException**: Thrown when attempting to checkout an unavailable book

