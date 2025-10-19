package models;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class Patron {
    private final String id;
    private String name;
    private String email;
    private String phoneNumber;
    private final List<String> borrowingHistory;
    private final List<String> currentBorrowedBooks;

    public Patron(String name, String email, String phoneNumber) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.borrowingHistory = new ArrayList<>();
        this.currentBorrowedBooks = new ArrayList<>();
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPhoneNumber() { return phoneNumber; }
    public List<String> getBorrowingHistory() { return new ArrayList<>(borrowingHistory); }
    public List<String> getCurrentBorrowedBooks() { return new ArrayList<>(currentBorrowedBooks); }

    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public void addToBorrowingHistory(String bookId) {
        borrowingHistory.add(bookId);
    }

    public void borrowBook(String bookId) {
        currentBorrowedBooks.add(bookId);
    }

    public void returnBook(String bookId) {
        currentBorrowedBooks.remove(bookId);
    }

    public boolean hasBorrowedBook(String bookId) {
        return currentBorrowedBooks.contains(bookId);
    }

    @Override
    public String toString() {
        return String.format("Patron[id=%s, name='%s', email='%s', borrowedBooks=%d]",
                id, name, email, currentBorrowedBooks.size());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Patron)) return false;
        Patron patron = (Patron) o;
        return id.equals(patron.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}