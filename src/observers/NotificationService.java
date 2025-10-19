package observers;

import java.util.logging.Logger;

public class NotificationService implements LibraryObserver {
    private static final Logger logger = Logger.getLogger(NotificationService.class.getName());

    @Override
    public void onBookCheckedOut(String patronId, String bookId) {
        logger.info("NOTIFICATION: Book " + bookId + " checked out by patron " + patronId);
        // In a real system, send email/SMS notification
    }

    @Override
    public void onBookReturned(String patronId, String bookId) {
        logger.info("NOTIFICATION: Book " + bookId + " returned by patron " + patronId);
        // In a real system, send email/SMS notification
    }
}