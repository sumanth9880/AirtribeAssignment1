package observers;

public interface LibraryObserver {
    void onBookCheckedOut(String patronId, String bookId);
    void onBookReturned(String patronId, String bookId);
}