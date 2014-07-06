package springcourse.exercises.exercise5;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import springcourse.exercises.exercise5.dao.api.IBookDao;
import springcourse.exercises.exercise5.dao.api.IMemberDao;
import springcourse.exercises.exercise5.model.Book;
import springcourse.exercises.exercise5.model.Genre;

import static org.mockito.Matchers.same;
import static org.mockito.Mockito.*;


// TODO spring configuration is missing
// TODO use SpringockitoAnnotatedContextLoader.class to load the spring context
public class LibraryTest {

    public static final Genre EXPECTED_GENRE = Genre.NA;
    public static final String MEMBER_ID = "123";

    @Configuration
    static class MyConfig {}

    @Autowired
    // TODO use mock instead of the original spring bean
    private IBookDao bookDao;

    @Autowired
    // TODO use mock instead of the original spring bean
    private IMemberDao memberDao;

    @Autowired
    // TODO use mock instead of the original spring bean
    private BookAnalyzer bookAnalyzer;


    @Autowired
    private Library uut;


    @Test
    public void testAddNewBook() throws Exception {
        // set up mock fixture
        Book argument = mock(Book.class);
        Book daoResult = mock(Book.class);
        when(bookAnalyzer.analyzeBook(same(argument))).thenReturn(EXPECTED_GENRE);
        when(bookDao.addBook(same(argument))).thenReturn(daoResult);
        // run test case
        Book methodResult = uut.addNewBook(argument);
        // verify result
        Assert.assertSame("not the same book returned", daoResult, methodResult);
        // verify method side-effects
        verify(argument).setGenre(Genre.NA);
    }

    @Test
    public void testGetLoanedBooksMemberExists() throws Exception {
        // set up mock fixture

        // run test case
        uut.getLoanedBooks(MEMBER_ID);
        // verify result

    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetLoanedBooksMemberDoesNotExist() throws Exception {
        // set up mock fixture

        // run test case
        uut.getLoanedBooks(MEMBER_ID);
    }
}