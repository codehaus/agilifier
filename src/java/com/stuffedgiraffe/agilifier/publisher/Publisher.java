package com.stuffedgiraffe.agilifier.publisher;

import com.stuffedgiraffe.agilifier.main.FileContext;
import com.stuffedgiraffe.agilifier.model.Module;
import com.stuffedgiraffe.agilifier.model.UserStory;
import com.stuffedgiraffe.agilifier.model.UserStorySuite;

public interface Publisher {
    void generateUserStorySuiteSummary(UserStorySuite userStorySuite, FileContext fileContext);

    void generateUserStorySummary(UserStory userStory, FileContext fileContext);

    void generateModuleSummary(Module module);
}
