package com.directsupply.webloganalytics;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Map;

public class AnalyticsMatcher {

    public static boolean isProductDetailPage(String uri) {
        if (uri == null)
            return false;

        String uriLowerCase = uri.toLowerCase();
        return uriLowerCase.matches(".*prod[1-2]?.asp.*");
    }

    public static boolean isAddToCartPage(String uri) {
        if (uri == null)
            return false;

        String uriLowerCase = uri.toLowerCase();
        return uriLowerCase.matches(".*shoppingcart/default.asp.*");
    }

    public static SearchTermCatId extractSearchTermAndCatIdFromUri(String uri) throws UnsupportedEncodingException {
        String uriLowerCase = uri.toLowerCase();
        Map<String, String> queryStringVariables = UriUtility.parseQueryStringFromUri(uriLowerCase);

        String returnToUrl = extractSanitizedReturnToUrl(queryStringVariables);

        if (returnToUrl == null)
            return new SearchTermCatId(queryStringVariables.get("term"), queryStringVariables.get("catid"));

        try
        {
            String decodedReturnUrl = URLDecoder.decode(returnToUrl.toLowerCase(), "UTF-8");
            Map<String, String> parsedReturnUrl = UriUtility.parseQueryStringFromUri(decodedReturnUrl);

            return new SearchTermCatId(parsedReturnUrl.get("term"), queryStringVariables.get("catid"));
        }
        catch (UnsupportedEncodingException ex) {
            System.err.println(ex.toString());
            throw ex;
        }
    }

    private static String extractSanitizedReturnToUrl(Map<String, String> queryStringVariables) {
        String returnToUrl = queryStringVariables.get("returntourl");

        if (returnToUrl != null && returnToUrl.endsWith("%")) {
            returnToUrl = returnToUrl.substring(0, returnToUrl.length() - 1);
        }

        return returnToUrl;
    }
}
