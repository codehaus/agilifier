package com.stuffedgiraffe.agilifier.main;

import junit.framework.TestCase;

public class FileContextTest extends TestCase {
    public void testGetResultsFilenameWorksWithWindowsFileSeparator() {
        String resultsFilename = FileContext.getResultsFilename("c:\\projects\\sunflower\\Story\\Test.html", "c:\\projects\\sunflower", "c:\\projects\\sunflower\\build\\reports\\agilifier");
        assertEquals("c:\\projects\\sunflower\\build\\reports\\agilifier\\Story\\Test.html", resultsFilename);
    }
}
