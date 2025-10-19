package services;

import models.Book;
import models.Library;
import strategies.SearchStrategy;

import java.util.List;

public class SearchService {
    private final Library library;
    private SearchStrategy searchStrategy;

    public SearchService(Library library) {
        this.library = library;
    }

    public void setSearchStrategy(SearchStrategy searchStrategy) {
        this.searchStrategy = searchStrategy;
    }

    public List<Book> search(String query) {
        if (searchStrategy == null) {
            throw new IllegalStateException("Search strategy not set");
        }
        return searchStrategy.search(library.getBooks().values(), query);
    }
}