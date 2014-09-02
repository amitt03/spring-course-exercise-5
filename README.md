
Exercise 5
==========

Have a look at the project, it is a solution for exercise 4 with additional test logic.

1. Look at the **dao** and **model** packages. It is possible to use this package outside the spring container. <br/>
  1. Look at **MemberTest** class, it's a simple **black-box** unit test of a POJO.<br/>
    - Execute the test suite (right-click > Run 'MemberTest') and get an error from an un-implemented test.
    - Implement the missing test and see that the suite passes.<br/>
  2. Look at **MemberInMemoryDaoWhiteBoxTest** class. Note that in order to **white-box**, we need access to a private member of the UUT.
    - Execute the test suite and get NullPointerException(s) (and AssertionError) due to the un-implemented code at line 24.
    - Implement using Spring-test's **ReflectionTestUtils**.
    - Execute the test suite and get AssertionError(s) due to the un-implemented tests.
    - Implement the rest of the test suite.<br/>
    **NOTICE**<br/>
    Use *uutMembers* when you need to check a method's **side-effect**.<br/>
    Test the effect of a single method invocation in each test case.

2. Look at **BookAnalyzerSetupTest** class. this is a simple stub for a Spring Mockito JUnit test. note how we use a PropertySourcesPlaceholderConfigurer that will help us test BookAnalyzer with some test environment variables.<br/>
  1. Execute **BookAnalyzerSetupTest** class and see that Spring did not load. Make the JUnit test run in Spring's TestContext.<br/>
  2. Execute **BookAnalyzerSetupTest** class and see that the application context fails to load. debug and fix the loading issue.<br/>
  3. Now the **BookAnalyzer** class autowiring fails, as the **BookAnalyzer** class was not scanned. Make the test context scan BookAnalyzer class.<br/>
     It's impossible to solve this one with *@ComponentScan*, use the alternative mentioned earlier.<br/>

  **NOTICE**<br/>
  i, ii and iii can be solved by adding a single line to **BookAnalyzerTest**. do not settle for less (more)!<br/>
  **Do not** change or add anything outside **BookAnalyzerSetupTest**.<br/>
  **Do not** change the *testSpringSetup()* test case.

3. Look at **LibraryTest** class. This is a partial **white-box** Spring Mockito JUnit test.<br/>
  1. Execute **LibraryTest** class and see that Spring did not load. Repeat steps 2.i, 2.ii, 2.iii to set up Spring environment correctly but with the following changes:<br/>
    - In 2.ii use *SpringockitoAnnotatedContextLoader.class* to load the spring context
    - In 2.iii instead of *BookAnalyzer.class* scan only Library.class .
  2. Execute **LibraryTest** class and see that autowiring of Library's dependencies fails. add mocking annotations to the un-scanned beans.<br/>
<br/>

**BONUS ASSIGNMENT**<br/>
4. Look at **MemberInMemoryDaoBlackBoxTest** class and its parent **IMemberDaoBlackBoxTest** class. The test refers to the **IMemberDao** interface and so it is reusable on any **IMemberDao** implementation. This is an example of **black-boxing** and **test re-use**.
  - Execute **MemberInMemoryDaoBlackBoxTest** class and get NullPointerException(s) and AssertionError(s).<br/>
    The exceptions in *testMemberCreationOnce* and *testMemberCreationTwice* are due to careless method overriding.<br/>
    Change the name of the *setUp()* method in either **MemberInMemoryDaoBlackBoxTest** or **IMemberDaoBlackBoxTest** to fix this.
  - Implement the rest of **IMemberDaoBlackBoxTest** test suite.

###N O T I C E
- [ ] Test **only** the the **IMemberDao** interface (don't use reflection)
- [ ] Test the affect of a method invocation on the result of other methods
