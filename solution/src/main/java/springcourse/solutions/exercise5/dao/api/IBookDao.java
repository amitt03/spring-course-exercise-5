package springcourse.solutions.exercise5.dao.api;

import springcourse.solutions.exercise5.model.Book;

import java.util.Collection;

/**
 * @author Amit Tal
 * @since 3/24/14
 */
public interface IBookDao {

    boolean checkConnection();

    Book addBook(Book book);

    void removeBook(String catalogId);

    Collection<Book> getAllBooks();

    Book loanBook(String catalogId, String memberId);

    String returnBook(String catalogId);

    Collection<Book> getLoanedBooks(String memberId);
}