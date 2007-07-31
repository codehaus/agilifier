package com.stuffedgiraffe.agilifier.publisher;

import freemarker.log.Logger;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Map;

public class FreemarkerFileGenerator implements FileGenerator {
    private Configuration freemarkerConfig;

    public FreemarkerFileGenerator() {
        try {
            Logger.selectLoggerLibrary(Logger.LIBRARY_NONE);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        freemarkerConfig = new Configuration();
        // Specify the data source where the template files come from.
        freemarkerConfig.setClassForTemplateLoading(this.getClass(), "/templates/");
        // Specify how templates will see the data model.
        freemarkerConfig.setObjectWrapper(new DefaultObjectWrapper());
    }

    public void generateFile(String templateFilename, Map<String, Object> context, File outputFile) {
        templateFilename = templateFilename.replaceAll(".vm", ".ftl");
        PrintWriter printWriter = null;
        outputFile.getParentFile().mkdirs();
        try {
            printWriter = new PrintWriter(new FileOutputStream(outputFile));
            Template temp = freemarkerConfig.getTemplate(templateFilename);
            temp.process(context, printWriter);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (printWriter != null) {
                printWriter.close();
            }
        }
    }
}
