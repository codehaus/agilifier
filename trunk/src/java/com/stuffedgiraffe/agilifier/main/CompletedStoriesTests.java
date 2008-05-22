package com.stuffedgiraffe.agilifier.main;

import com.stuffedgiraffe.agilifier.builder.UserStoriesSuiteBuilder;
import com.stuffedgiraffe.agilifier.junit.JUnitSuiteBuilder;
import com.stuffedgiraffe.agilifier.model.Module;
import com.stuffedgiraffe.agilifier.model.UserStorySuite;
import com.stuffedgiraffe.agilifier.publisher.FreemarkerFileGenerator;
import com.stuffedgiraffe.agilifier.publisher.HtmlPublisher;
import com.stuffedgiraffe.agilifier.publisher.Publisher;
import com.stuffedgiraffe.agilifier.util.DeafTestListener;
import junit.framework.TestSuite;

public class CompletedStoriesTests {
    public static TestSuite suite() throws Exception {
        FileContext fileContext = FileContext.createFromPropertiesFile();
        GenerateUserStorySite.createResultsArea(fileContext);

        UserStoriesSuiteBuilder factory = new UserStoriesSuiteBuilder();
        Module module = new Module("Project", fileContext);
        UserStorySuite completed = factory.buildUserStorySuite("CompletedStories", fileContext, module);
        module.addUserStorySuite(completed);
        Publisher publisher = new HtmlPublisher(new FreemarkerFileGenerator());
        DeafTestListener listener = new DeafTestListener();
        JUnitSuiteBuilder testSuiteBuilder = new JUnitSuiteBuilder(publisher, listener);
        return testSuiteBuilder.buildJUnitSuite(module);
    }
}
