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

    @Test
    public void testParseQueryStringFromUriWithDecode() throws Exception {
        String uri = "http://foo.com/asdf?foo=bar&bar=this+phrase";
        Map<String, String> actual = UriUtility.parseQueryStringFromUri(uri);
        Assert.assertEquals(2, actual.size());
        Assert.assertEquals("bar", actual.get("foo"));
        Assert.assertEquals("this phrase", actual.get("bar"));
    }
}
