package springcourse.solutions.exercise5;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springcourse.solutions.exercise5.dao.api.IBookDao;
import springcourse.solutions.exercise5.dao.api.IMemberDao;
import springcourse.solutions.exercise5.model.Book;
import springcourse.solutions.exercise5.model.Genre;
import springcourse.solutions.exercise5.model.Member;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Amit Tal
 * @since 3/24/14
 */
@Service
public class Library {

    private Logger logger = LoggerFactory.getLogger(Library.class);

    private IBookDao bookDao;
    private IMemberDao memberDao;
    private BookAnalyzer bookAnalyzer;

    @Autowired
    public Library(IBookDao bookDao, IMemberDao memberDao, BookAnalyzer bookAnalyzer) {
        this.bookDao = bookDao;
        this.memberDao = memberDao;
        this.bookAnalyzer = bookAnalyzer;
    }

    @PostConstruct
    public void open() {
        if (bookDao.checkConnection()) {
            logger.info("The library is now open");
        } else {
            logger.info("The library is temporarily unavailable");
        }
    }

    public Member createMembership(Member member) {
        Member dbMember = memberDao.createMember(member);
        return dbMember;
    }

    public Collection<Member> getAllMembers() {
        Collection<Member> allMembers = memberDao.getAllMembers();
        if (allMembers != null) {
            for (Member member : allMembers) {
                Collection<Book> loanedBooks = bookDao.getLoanedBooks(member.getMemberId());
                member.setLoanedBooks(loanedBooks);
            }
        }
        return allMembers;
    }

    public Book addNewBook(Book newBook) {
        Genre genre = bookAnalyzer.analyzeBook(newBook);
        newBook.setGenre(genre);
        Book book = bookDao.addBook(newBook);
        return book;
    }

    public void removeBook(String catalogId) {
        bookDao.removeBook(catalogId);
    }

    public Collection<Book> getAllBooks() {
        return bookDao.getAllBooks();
    }

    public Collection<Book> searchBooksByAuthor(String author) {
        Collection<Book> allBooks = getAllBooks();
        Collection<Book> booksByAuthor = new ArrayList<Book>();
        for (Book book : allBooks) {
            if (author.equals(book.getAuthor())) {
                booksByAuthor.add(book);
            }
        }
        return booksByAuthor;
    }

    public Book loanBook(String catalogId, String memberId) {
        if (memberDao.memberExist(memberId)) {
            Book book = bookDao.loanBook(catalogId, memberId);
            return book;
        } else {
            throw new IllegalArgumentException("Member " + memberId + " is not a member of this library");
        }
    }

    public Collection<Book> getLoanedBooks(String memberId) {
        if (memberDao.memberExist(memberId)) {
            Collection<Book> loanedBooks = bookDao.getLoanedBooks(memberId);
            return loanedBooks;
        } else {
            throw new IllegalArgumentException("Member " + memberId + " is not a member of this library");
        }
    }

    public void returnBook(String catalogId, String memberId) {
        String returnedMemberId = bookDao.returnBook(catalogId);
        if (!memberId.equals(returnedMemberId)) {
            logger.warn("Returned book {} was loaned by {} BUT returned by {}", catalogId, memberId, returnedMemberId);
        }
    }

    @PreDestroy
    public void close() {
        logger.info("The library is now closed");
    }

}