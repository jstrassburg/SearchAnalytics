package com.directsupply.webloganalytics;

import java.util.HashMap;
import java.util.Map;

public class UriUtility {
    public static Map<String, String> parseQueryStringFromUri(String uri) {
        int indexOfQuestionMark = uri.indexOf("?");

        if (isOnlyQueryString(indexOfQuestionMark)) {
            return parseQueryString(uri);
        }
        return parseQueryString(uri.substring(indexOfQuestionMark + 1));
    }

    private static boolean isOnlyQueryString(int indexOfQuestionMark) {
        return indexOfQuestionMark == -1;
    }

    private static Map<String, String> parseQueryString(String queryString) {
        Map<String, String> variables = new HashMap<String, String>();
        for (String pair : queryString.split("&")) {
            String[] keyValue = pair.split("=");
            if (keyValue.length >= 2)
                variables.put(keyValue[0], keyValue[1]);
        }
        return variables;
    }
}
