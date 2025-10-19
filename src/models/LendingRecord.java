package models;

import enums.LendingStatus;
import java.time.LocalDateTime;
import java.util.UUID;

public class LendingRecord {
    private final String id;
    private final String bookId;
    private final String patronId;
    private final LocalDateTime checkoutDate;
    private LocalDateTime dueDate;
    private LocalDateTime returnDate;
    private LendingStatus status;

    public LendingRecord(String bookId, String patronId) {
        this.id = UUID.randomUUID().toString();
        this.bookId = bookId;
        this.patronId = patronId;
        this.checkoutDate = LocalDateTime.now();
        this.dueDate = checkoutDate.plusWeeks(2); // 2-week lending period
        this.status = LendingStatus.ACTIVE;
    }

    public String getId() { return id; }
    public String getBookId() { return bookId; }
    public String getPatronId() { return patronId; }
    public LocalDateTime getCheckoutDate() { return checkoutDate; }
    public LocalDateTime getDueDate() { return dueDate; }
    public LocalDateTime getReturnDate() { return returnDate; }
    public LendingStatus getStatus() { return status; }

    public void setDueDate(LocalDateTime dueDate) { this.dueDate = dueDate; }
    public void setReturnDate(LocalDateTime returnDate) { this.returnDate = returnDate; }
    public void setStatus(LendingStatus status) { this.status = status; }

    public boolean isOverdue() {
        return status == LendingStatus.ACTIVE && LocalDateTime.now().isAfter(dueDate);
    }

    public void markAsReturned() {
        this.returnDate = LocalDateTime.now();
        this.status = LendingStatus.RETURNED;
    }

    @Override
    public String toString() {
        return String.format("LendingRecord[id=%s, bookId=%s, patronId=%s, status=%s, dueDate=%s]",
                id, bookId, patronId, status, dueDate);
    }
}