package models;

import observers.LibraryObserver;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Library {
    private final String name;
    private final Map<String, Book> books;
    private final Map<String, Patron> patrons;
    private final Map<String, LendingRecord> lendingRecords;
    private final List<LibraryObserver> observers;

    public Library(String name) {
        this.name = name;
        this.books = new HashMap<>();
        this.patrons = new HashMap<>();
        this.lendingRecords = new HashMap<>();
        this.observers = new ArrayList<>();
    }

    public String getName() { return name; }
    public Map<String, Book> getBooks() { return books; }
    public Map<String, Patron> getPatrons() { return patrons; }
    public Map<String, LendingRecord> getLendingRecords() { return lendingRecords; }

    // Observer pattern methods
    public void addObserver(LibraryObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(LibraryObserver observer) {
        observers.remove(observer);
    }

    public void notifyBookCheckedOut(String patronId, String bookId) {
        for (LibraryObserver observer : observers) {
            observer.onBookCheckedOut(patronId, bookId);
        }
    }

    public void notifyBookReturned(String patronId, String bookId) {
        for (LibraryObserver observer : observers) {
            observer.onBookReturned(patronId, bookId);
        }
    }
}