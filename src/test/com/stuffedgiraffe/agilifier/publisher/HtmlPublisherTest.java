package com.stuffedgiraffe.agilifier.publisher;

import com.stuffedgiraffe.agilifier.main.FileContext;
import com.stuffedgiraffe.agilifier.model.Module;
import com.stuffedgiraffe.agilifier.model.UserStory;
import com.stuffedgiraffe.agilifier.model.UserStorySuite;
import com.stuffedgiraffe.agilifier.util.MapBuilder;
import org.jmock.MockObjectTestCase;
import org.jmock.cglib.Mock;

import java.io.File;
import java.util.Map;

public class HtmlPublisherTest extends MockObjectTestCase {
    protected UserStorySuite userStorySuite;
    protected FileContext fileContext;
    protected Mock fileGeneratorMock;
    protected HtmlPublisher htmlPublisher;
    private Module module;
    private UserStory userStory;

    public void setUp() throws Exception {
        super.setUp();
        fileContext = new FileContext("testDir", "resultsDir");
        userStory = new UserStory("MyUserStory");
        module = new Module("MyModule", fileContext);
        userStorySuite = new UserStorySuite("MyTestSuite", module);
        userStorySuite.addChild(userStory);
        module.addUserStorySuite(userStorySuite);
        fileGeneratorMock = new Mock(FileGenerator.class);
        htmlPublisher = new HtmlPublisher((FileGenerator) fileGeneratorMock.proxy());
    }

    public void testCallsFileGeneratorToGenerateUserStorySuiteSummary() throws Exception {
        File outputFile = new File("resultsDir/summaryMyTestSuite.html");
        Map<String, Object> data = MapBuilder.make("suite", userStorySuite);
        fileGeneratorMock.expects(once()).method("generateFile").with(eq("UserStorySuiteSummary.vm"), eq(data), eq(outputFile));

        htmlPublisher.generateUserStorySuiteSummary(userStorySuite, fileContext);
    }

    public void testCallsFileGeneratorToGenerateModuleSummary() throws Exception {
        File outputFile = new File("resultsDir/index.html");
        Map<String, Object> data = MapBuilder.make("module", module);
        fileGeneratorMock.expects(once()).method("generateFile").with(eq("ModuleSummary.vm"), eq(data), eq(outputFile));

        htmlPublisher.generateModuleSummary(module);
    }

    public void testCallsFileGeneratorToGenerateUserStorySummary() throws Exception {
        File outputFile = new File("resultsDir/MyUserStory/summary.html");
        Map<String, Object> data = MapBuilder.make("userStory", userStory);
        fileGeneratorMock.expects(once()).method("generateFile").with(eq("UserStorySummary.vm"), eq(data), eq(outputFile));

        htmlPublisher.generateUserStorySummary(userStory, fileContext);
    }


}
