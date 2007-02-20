package com.stuffedgiraffe.agilifier.publisher;

import java.io.File;
import java.util.Map;

public interface FileGenerator {
    void generateFile(String templateName, Map context, File outputFile);
}
