package com.directsupply.webloganalytics;

public class SearchTermCatId {
    private String _searchTerm = null;
    private String _catId = null;

    public SearchTermCatId(String searchTerm, String catId) {
        this._searchTerm = searchTerm;
        this._catId = catId;
    }

    public String getSearchTerm() {
        return _searchTerm;
    }

    public String getCatId() {
        return _catId;
    }
}
