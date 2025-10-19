package strategies;

import models.Book;
import java.util.Collection;
import java.util.List;

public class SearchByAuthor implements SearchStrategy {
    @Override
    public List<Book> search(Collection<Book> books, String query) {
        return books.stream()
                .filter(book -> book.getAuthor().toLowerCase().contains(query.toLowerCase()))
                .toList();
    }
}