package strategies;

import models.Book;
import java.util.Collection;
import java.util.List;

public class SearchByISBN implements SearchStrategy {
    @Override
    public List<Book> search(Collection<Book> books, String query) {
        return books.stream()
                .filter(book -> book.getIsbn().equals(query))
                .toList();
    }
}