package com.directsupply.webloganalytics;

import org.junit.Assert;
import org.junit.Test;

import java.io.UnsupportedEncodingException;

public class AnalyticsMatcherTest {
    @Test
    public void testIsProductDetailPageNullString() throws Exception {
        Assert.assertFalse(AnalyticsMatcher.isProductDetailPage(null));
    }

    @Test
    public void testIsProductDetailPageEmptyString() throws Exception {
        Assert.assertFalse(AnalyticsMatcher.isProductDetailPage(""));
    }

    @Test
    public void testIsProductDetailPageSuccess() throws Exception {
        Assert.assertTrue(AnalyticsMatcher.isProductDetailPage("prod.asp?CatID=SOMECATEGORYID&Term=clown"));
    }

    @Test
    public void testIsProductDetailPageProd1() throws Exception {
        Assert.assertTrue(AnalyticsMatcher.isProductDetailPage("Prod1.asp?CatID=SOMECATEGORYID&Term=Foo"));
    }

    @Test
    public void testIsProductDetailPageProd2() throws Exception {
        Assert.assertTrue(AnalyticsMatcher.isProductDetailPage("Prod2.asp?CatID=SOMECATEGORYID&Term=Foo"));
    }

    @Test
    public void testIsAddToCartPageNullString() throws Exception {
        Assert.assertFalse(AnalyticsMatcher.isAddToCartPage(null));
    }

    @Test
    public void testIsAddToCartPageEmptyString() throws Exception {
        Assert.assertFalse(AnalyticsMatcher.isAddToCartPage(""));
    }

    @Test
    public void testIsAddToCartPageSuccess() throws Exception {
        Assert.assertTrue(AnalyticsMatcher.isAddToCartPage(
                "shoppingcart/default.asp?CatID=SOMECATEGORYID&ReturnUrl=foobar%3Dbarfoo%26Term%3Dfoo"));
    }

    @Test
    public void testExtractSearchTermCatId() throws Exception {
        String expectedCatId = "asdffdsa";
        String expectedTerm = "dsfsf";

        String uri = String.format(
                "Prod1.asp?CatID=%s&Term=%s", expectedCatId, expectedTerm);
        SearchTermCatId actual = AnalyticsMatcher.extractSearchTermAndCatIdFromUri(uri);

        Assert.assertEquals(expectedCatId, actual.getCatId());
        Assert.assertEquals(expectedTerm, actual.getSearchTerm());
    }

    @Test
    public void testExtractSearchTermAndCatIdFromUriFromReturnToUrl() throws Exception {
        String expectedCatId = "asdffdsa";
        String expectedTerm = "dsfsf";

        String uri = String.format(
                "shoppingcart/default.asp?CatID=%s&ReturnToUrl=foobar%%3Dbarfoo%%26Term%%3D%s",
                expectedCatId, expectedTerm);
        SearchTermCatId actual = AnalyticsMatcher.extractSearchTermAndCatIdFromUri(uri);

        Assert.assertEquals(expectedCatId, actual.getCatId());
        Assert.assertEquals(expectedTerm, actual.getSearchTerm());
    }

    @Test
    public void testExtractSearchTermAndCatIdFromUriWithRealProductDetailRecord() throws UnsupportedEncodingException {
        String line = "36950256\tCS\t592\t2011-02-07T09:59:46\tGET\t200\t/dssi.3.30/prod1.asp\tcatid=" +
                "0000000009615&ReturnToURL=%2fdssi.3.30%2fSearch%2fResults.aspx%3fSrtBy%3dPREVIOUSLYORDERED%26" +
                "Term%3dbandage%2broll%26PID%3d1%26RcPerPg%3d20%26Supp%3d%26SrtOrd%3d1%26OnOG%3d" +
                "True%26OffOG%3dFalse%26POH%3d0%26Cat%3d%26Spec%3d2";
        WebLogRecord record = new WebLogRecord(line);

        boolean isDetail = AnalyticsMatcher.isProductDetailPage(
                record.getRequestUri() + "?" + record.getRequestQueryString());
        Assert.assertTrue(isDetail);

        SearchTermCatId searchTermCatId = AnalyticsMatcher.extractSearchTermAndCatIdFromUri(record.getRequestQueryString());
        Assert.assertNotNull("CatId was null.", searchTermCatId.getCatId());
        Assert.assertNotNull("SearchTerm was null.", searchTermCatId.getSearchTerm());
    }
}
