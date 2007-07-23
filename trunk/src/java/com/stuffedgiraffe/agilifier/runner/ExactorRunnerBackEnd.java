package com.stuffedgiraffe.agilifier.runner;

import com.exoftware.exactor.*;
import com.exoftware.exactor.parser.ScriptParser;
import com.stuffedgiraffe.agilifier.model.AcceptanceTest;
import com.stuffedgiraffe.agilifier.publisher.FileGenerator;
import com.stuffedgiraffe.agilifier.publisher.FreemarkerFileGenerator;

import java.io.File;
import java.io.FilenameFilter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.*;

public class ExactorRunnerBackEnd {
    private static final FilenameFilter FILE_FILTER = new FilenameFilter() {
        public boolean accept(File dir, String name) {
            return name.endsWith(".act") && !name.startsWith("~");
        }
    };
    private FileGenerator fileGenerator = new FreemarkerFileGenerator();
    private int maxParameters;
    private AcceptanceTest test;
    private int executed;
    private int failed;
    private long startTime;

    public FilenameFilter getFileFilter() {
        return FILE_FILTER;
    }

    // TODO - Test me!
    public String runTest(AcceptanceTest test) {
        this.test = test;
        File resultFile = test.getResultFile();
        resultFile.getParentFile().mkdirs();

        String failure = null;
        try {
            executeTestCase(test.getTestFile(), resultFile);
        } catch (Throwable throwable) {
            failure = throwable.toString();
        }
        return failure;
    }


    private List<CommandResult> results;
    private Throwable throwable;

    private void executeTestCase(File testFile, File resultsFile) throws Throwable {
        throwable = null;
        ScriptSet scriptSet = new ScriptSet();
        scriptSet.addListener(getListener(resultsFile));
        scriptSet.addScript(new ScriptParser().parse(testFile));
        scriptSet.execute();
        if (throwable != null) {
            throw throwable;
        }
    }

    private ScriptSetListener getListener(final File resultsFile) {
        return new ScriptSetListener() {
            public void scriptSetStarted() {
            }

            public void scriptSetEnded() {
            }

            public void scriptStarted(Script s) {
                results = new LinkedList<CommandResult>();
                executed = 0;
                failed = 0;
            }

            public void scriptEnded(Script s) {
                for (Object result : results) {
                    CommandResult commandResult = (CommandResult) result;
                    int length = commandResult.getCommand().getParameters().length;
                    if (length > maxParameters) {
                        maxParameters = length;
                    }

                }

                Map<String, Object> data = new HashMap<String, Object>();
                data.put("results", results);
                data.put("acceptanceTestName", test.getDescription());
                data.put("executed", String.valueOf(executed));
                data.put("failed", String.valueOf(failed));
                data.put("inputFile", test.getTestFile().getAbsolutePath());
                data.put("inputUpdate", new Date(test.getTestFile().lastModified()).toString());
                data.put("outputFile", test.getResultFile().getAbsolutePath());
                data.put("runDate", new Date(startTime).toString());
                data.put("elapsedTime", String.valueOf((System.currentTimeMillis() - startTime) / 1000.0));

                resultsFile.getParentFile().mkdirs();
                fileGenerator.generateFile("ExactorTestResult.vm", data, resultsFile);

            }

            public void commandStarted(Command c) {
            }

            public void commandEnded(Command c, Throwable t) {
                results.add(new CommandResult(c, t));
                if (t != null) {
                    throwable = t;
                }
            }
        };
    }


    public class CommandResult {
        private Command command;
        private String stackTrace;

        public CommandResult(Command command, Throwable exception) {
            this.command = command;
            if (exception != null) {
                StringWriter stringWriter = new StringWriter();
                exception.printStackTrace(new PrintWriter(stringWriter));
                this.stackTrace = stringWriter.toString();
            }
            executed++;
            if (exception != null) {
                failed++;
            }
            startTime = System.currentTimeMillis();
        }

        public Command getCommand() {
            return command;
        }

        public String getException() {
            return stackTrace;
        }

        public boolean getHasError() {
            return stackTrace != null;
        }

        public String getExceptionForHtml() {
            if (stackTrace == null) {
                return "";
            }
            return stackTrace
                    .replaceAll("<", "&lt;")
                    .replaceAll(">", "&gt;")
                    .replaceAll("\r\n", "<br>");
        }

        public String[] getParameters() {
            String[] parameters = new String[maxParameters];
            // Nasty!!!
            Arrays.fill(parameters, "&nbsp;");
            Parameter[] commandParameters = command.getParameters();
            for (int i = 0; i < commandParameters.length; i++) {
                Parameter parameter = commandParameters[i];
                parameters[i] = parameter.stringValue();
            }
            return parameters;
        }

        public boolean getComment() {
            return command.getClass().getName().indexOf("Comment") >= 0;
        }
    }


}
