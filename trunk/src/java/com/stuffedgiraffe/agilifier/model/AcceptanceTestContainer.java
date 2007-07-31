package com.stuffedgiraffe.agilifier.model;

import java.util.Collection;

public interface AcceptanceTestContainer extends AcceptanceTestOrAcceptanceTestContainer {
    String getDescription();

    int getPassedCount();

    int getFailedCount();

    int getFailedPercent();

    int getPassedPercent();


    String getPathToRoot();

    String getPath();

    void addChild(AcceptanceTestOrAcceptanceTestContainer child);

    AcceptanceTestContainer getParent();

    Collection<AcceptanceTestOrAcceptanceTestContainer> getChildren();


    String getText();

    void setText(String text);

}