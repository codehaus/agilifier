package com.stuffedgiraffe.agilifier.publisher;

import com.stuffedgiraffe.agilifier.main.FileContext;
import com.stuffedgiraffe.agilifier.model.AcceptanceTestContainer;
import com.stuffedgiraffe.agilifier.model.Module;
import com.stuffedgiraffe.agilifier.model.UserStory;
import com.stuffedgiraffe.agilifier.model.UserStorySuite;
import com.stuffedgiraffe.agilifier.util.MapBuilder;

import java.io.File;
import java.util.Map;

public class HtmlPublisher implements Publisher {
    private FileGenerator fileGenerator;

    public HtmlPublisher(FileGenerator fileGenerator) {
        this.fileGenerator = fileGenerator;
    }

    public void generateUserStorySuiteSummary(UserStorySuite userStorySuite, FileContext fileContext) {
        File file = fileContext.getResultFile("summary" + userStorySuite.getName() + ".html");
        Map<String, Object> context = MapBuilder.make("userStory", userStorySuite);
        fileGenerator.generateFile("UserStorySuiteSummary.vm", context, file);
    }

    public void generateSummary(AcceptanceTestContainer userStorySuite, FileContext fileContext) {
        File file = fileContext.getResultFile("summary.html");
        Map<String, Object> context = MapBuilder.make("userStory", userStorySuite);
        fileGenerator.generateFile("UserStorySummary.vm", context, file);
    }

    public void generateUserStorySummary(UserStory userStory, FileContext fileContext) {
        File file = fileContext.getResultFile(userStory.getName() + File.separatorChar + "summary.html");
        Map<String, Object> context = MapBuilder.make("userStory", userStory);
        fileGenerator.generateFile("UserStorySummary.vm", context, file);
    }

    public void generateModuleSummary(Module module) {
        Map<String, Object> context = MapBuilder.make("module", module);
        File outputFile = module.getFileContext().getResultFile("index.html");
        fileGenerator.generateFile("ModuleSummary.vm", context, outputFile);
    }
}
