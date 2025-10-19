package services;

import exceptions.PatronNotFoundException;
import models.Library;
import models.Patron;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class PatronService {
    private static final Logger logger = Logger.getLogger(PatronService.class.getName());
    private final Library library;

    public PatronService(Library library) {
        this.library = library;
    }

    public Patron addPatron(String name, String email, String phoneNumber) {
        Patron patron = new Patron(name, email, phoneNumber);
        library.getPatrons().put(patron.getId(), patron);
        logger.info("Patron registered: " + patron.getName());
        return patron;
    }

    public void updatePatron(String patronId, String name, String email, String phoneNumber)
            throws PatronNotFoundException {
        Patron patron = getPatronById(patronId);
        if (name != null) patron.setName(name);
        if (email != null) patron.setEmail(email);
        if (phoneNumber != null) patron.setPhoneNumber(phoneNumber);
        logger.info("Patron updated: " + patron.getName());
    }

    public Patron getPatronById(String patronId) throws PatronNotFoundException {
        Patron patron = library.getPatrons().get(patronId);
        if (patron == null) {
            throw new PatronNotFoundException("Patron with ID " + patronId + " not found");
        }
        return patron;
    }

    public List<Patron> getAllPatrons() {
        return new ArrayList<>(library.getPatrons().values());
    }

    public List<String> getPatronBorrowingHistory(String patronId) throws PatronNotFoundException {
        Patron patron = getPatronById(patronId);
        return patron.getBorrowingHistory();
    }
}