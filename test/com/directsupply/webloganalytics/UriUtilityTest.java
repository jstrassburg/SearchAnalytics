package com.directsupply.webloganalytics;

import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

public class UriUtilityTest {
    @Test
    public void testParseQueryStringFromUriQueryString() throws Exception {
        String queryString = "ASDF=FDSA&FOO=BAR";
        Map<String, String> actual = UriUtility.parseQueryStringFromUri(queryString);
        Assert.assertEquals(2, actual.size());
        Assert.assertEquals("FDSA", actual.get("ASDF"));
        Assert.assertEquals("BAR", actual.get("FOO"));
        Assert.assertNull(actual.get("BARF"));
    }
}
