package springcourse.exercises.exercise5.dao.impl;

import org.junit.Before;
import springcourse.exercises.exercise5.dao.api.IMemberDaoBlackBoxTest;

/**
 * @author amira
 * @since 4/17/2014
 */
public class MemberInMemoryDaoBlackBoxTest extends IMemberDaoBlackBoxTest {

    // TODO : bonus assignment - fix this test

    @Before
    public void setUp() throws Exception {
        uut = new MemberInMemoryDao();
    }
}
