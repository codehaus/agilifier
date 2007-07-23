package com.stuffedgiraffe.agilifier.util;

import junit.framework.TestCase;

public class CamelUtilsTest extends TestCase {
    public void testUncamelWorksForSimpleCase() throws Exception {
        assertUncamel("ThisIsATest", "This Is A Test");
    }

    public void testSeparatesNumbersOutAsSeparateWords() throws Exception {
        assertUncamel("ThisIsATest123", "This Is A Test 123");
        assertUncamel("ThisIsTest123OutOfATotalOf1024", "This Is Test 123 Out Of A Total Of 1024");
    }

    public void testAcronymsAreSeparatedOut() throws Exception {
        assertUncamel("ThisIsAnIMSTest", "This Is An IMS Test");
    }

    public void testStringThatEndsWithAcronymIsSeparatedOut() throws Exception {
        assertUncamel("ILoveMyPMM", "I Love My PMM");
    }

    public void testStringsStartingWithNumbersAreUnCamelledCorrectly() throws Exception {
        assertUncamel("666IsTheNumberOfTheBeast", "666 Is The Number Of The Beast");
    }

    private void assertUncamel(String camel, String expectedUncamel) {
        String result = CamelUtils.uncamel(camel);
        assertEquals(expectedUncamel, result);
    }
}
