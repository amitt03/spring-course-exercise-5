package springcourse.exercises.exercise5;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.mock.env.MockPropertySource;
import org.springframework.util.Assert;
import springcourse.exercises.exercise5.model.Book;
import springcourse.exercises.exercise5.model.Genre;

// TODO spring configuration is missing
public class BookAnalyzerSetupTest {

    @Configuration
    static class MyConfig {
        // use properties from mockEnvVars as part of this test's fixture
        @Bean
        public PropertySourcesPlaceholderConfigurer configurer() {
            PropertySourcesPlaceholderConfigurer result = new PropertySourcesPlaceholderConfigurer();
            MutablePropertySources propertySources = new MutablePropertySources();
            propertySources.addLast(mockEnvVars);
            result.setPropertySources(propertySources);
            return result;
        }
    }

    @Autowired
    protected BookAnalyzer analyzer;


    @Autowired
    private ApplicationContext ac;


    @Value("${analyze.romance}")
    private String[] romanceKeyWord;

    private static MockPropertySource mockEnvVars;

    private static Book book;

    @BeforeClass
    public static void setUp() throws Exception {
        mockEnvVars = new MockPropertySource()
                .withProperty("analyze.romance", "1,2")
                .withProperty("analyze.comedy", "3,4")
                .withProperty("analyze.horror", "5,6")
                .withProperty("analyze.action", "7,8")
                .withProperty("analyze.fiction", "9,A");
        book = new Book("The screwtape letters", "Lewis Carroll");
        book.setDetails("A fictional story about morality");
    }

    @Test
    public void testSpringSetup() throws Exception {
        Assert.notNull(ac, "2a. spring did not load!");
        Assert.notNull(analyzer, "2c. auto wiring failed!");
        org.junit.Assert.assertNotEquals("2c. AppConfig loaded (and scanned books-analyzer.properties)!", "kiss", romanceKeyWord[0]);
        org.junit.Assert.assertNotEquals("2d. no properties placeholder configurator loaded!", "${analyze.romance}", romanceKeyWord[0]);
        org.junit.Assert.assertEquals("2d. mockEnvVars not loaded!", "1", romanceKeyWord[0]);

        org.junit.Assert.assertEquals("the analyzer does not work!", Genre.FICTION, analyzer.analyzeBook(book));

    }
}
