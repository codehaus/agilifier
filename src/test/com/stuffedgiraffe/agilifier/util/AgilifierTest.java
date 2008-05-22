package com.stuffedgiraffe.agilifier.util;

import junit.framework.TestCase;

public class AgilifierTest extends TestCase {
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

    private void assertUncamel(String camel, String expectedUncamel) {
        String result = Agilifier.uncamel(camel);
        assertEquals(expectedUncamel,result);
    }

}
