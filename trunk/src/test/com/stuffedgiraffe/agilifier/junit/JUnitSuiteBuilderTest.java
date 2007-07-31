package com.stuffedgiraffe.agilifier.junit;

import com.stuffedgiraffe.agilifier.main.FileContext;
import com.stuffedgiraffe.agilifier.model.AcceptanceTest;
import com.stuffedgiraffe.agilifier.model.Module;
import com.stuffedgiraffe.agilifier.model.UserStory;
import com.stuffedgiraffe.agilifier.model.UserStorySuite;
import com.stuffedgiraffe.agilifier.publisher.Publisher;
import com.stuffedgiraffe.agilifier.runner.Runner;
import junit.framework.*;
import org.jmock.cglib.Mock;
import org.jmock.cglib.MockObjectTestCase;

import java.io.File;

public class JUnitSuiteBuilderTest extends MockObjectTestCase {
    protected UserStory story = new UserStory("StoryA");
    protected File testFile = new File("SomeFilename.html");
    protected UserStorySuite suite;
    protected Mock publisherMock;
    protected Mock runnerMock;
    protected Mock testListenerMock;
    protected JUnitSuiteBuilder jUnitSuiteBuilder;
    protected Module module;
    protected AcceptanceTest acceptanceTest;
    protected File resultFile;
    private Runner runner;

    protected void setUp() throws Exception {
        super.setUp();

        publisherMock = new Mock(Publisher.class);
        runnerMock = new Mock(Runner.class);
        testListenerMock = new Mock(TestListener.class);

        runner = (Runner) runnerMock.proxy();

        FileContext fileContext = new FileContext("acceptance-tests", "build/acceptance-test-results");
        module = new Module("MyModule", fileContext);
        suite = new UserStorySuite("SuiteOne", module);
        acceptanceTest = new AcceptanceTest(testFile, fileContext.getResultsFile(testFile), runner);
        resultFile = fileContext.getResultsFile(testFile);


        jUnitSuiteBuilder = new JUnitSuiteBuilder((Publisher) publisherMock.proxy(),
                (TestListener) testListenerMock.proxy());
        module.addUserStorySuite(suite);
        suite.addChild(story);
        story.addTest(acceptanceTest);
    }


    public void testTestSuiteStructureMimicsModuleStructure() throws Exception {
        TestSuite moduleSuite = jUnitSuiteBuilder.buildJUnitSuite(module, module.getFileContext());

        assertEquals("MyModule", moduleSuite.getName());
        TestSuite storiesSuite = (TestSuite) moduleSuite.tests().nextElement();
        assertEquals("Suite One (1 story)", storiesSuite.getName());
        TestSuite storySuite = (TestSuite) storiesSuite.tests().nextElement();
        assertEquals("Story A (1 test)", storySuite.getName());
        TestCase test = (TestCase) storySuite.tests().nextElement();
        assertEquals(JUnitAcceptanceTest.class, test.getClass());
        assertEquals("Some Filename", test.getName());
    }

    public void testRunningGeneratedSuiteCallsRunnerAndPublisherAndListenerCorrectly() throws Throwable {
        publisherMock.expects(once()).method("generateModuleSummary").with(same(module));
        publisherMock.expects(once()).method("generateUserStorySuiteSummary").with(same(suite), same(module.getFileContext()));
        publisherMock.expects(once()).method("generateUserStorySummary").with(same(story), same(module.getFileContext()));

        testListenerMock.expects(once()).method("startTest").with(isA(JUnitAcceptanceTest.class));
        testListenerMock.expects(once()).method("endTest").with(isA(JUnitAcceptanceTest.class));

        runnerMock.expects(once()).method("runTest").with(eq(new AcceptanceTest(testFile, resultFile, runner)));

        TestSuite moduleSuite = jUnitSuiteBuilder.buildJUnitSuite(module, module.getFileContext());
        TestResult testResult = new TestResult();
        moduleSuite.run(testResult);

        // The TestSuite would have swallowed any exceptions that occurred, so let's
        // throw them now so that we don't fail silendly!
        throwExceptionThatOccurred(testResult);
        verify();
    }

    private void throwExceptionThatOccurred(TestResult testResult) throws Throwable {
        if (testResult.failures().hasMoreElements()) {
            TestFailure testFailure = (TestFailure) testResult.failures().nextElement();
            throw testFailure.thrownException();
        }
        if (testResult.errors().hasMoreElements()) {
            TestFailure testFailure = (TestFailure) testResult.failures().nextElement();
            throw testFailure.thrownException();
        }
    }

}
