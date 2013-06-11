package com.directsupply.webloganalytics;

public class WebLogRecord {
    private String _sessionId = null;
    private String _jobCode = null;
    private String _profileId = null;
    private String _requestDate = null;
    private String _requestMethod = null;
    private String _httpStatus = null;
    private String _requestUri = null;
    private String _requestQueryString = null;

    public WebLogRecord(String line) throws IllegalArgumentException {
        if (line == null) {
            throw new IllegalArgumentException("Parameter line cannot be null");
        }

        String[] fields = line.split("\\t");
        if (fields.length != 8){
            throw new IllegalArgumentException(
                    String.format("Expected 8 fields in line, received: %d.", fields.length));
        }

        this._sessionId = fields[0];
        this._jobCode = fields[1];
        this._profileId = fields[2];
        this._requestDate = fields[3];
        this._requestMethod = fields[4];
        this._httpStatus = fields[5];
        this._requestUri = fields[6];
        this._requestQueryString = fields[7];
    }

    public String getSessionId() {
        return _sessionId;
    }

    public String getJobCode() {
        return _jobCode;
    }

    public String getProfileId() {
        return _profileId;
    }

    public String getRequestDate() {
        return _requestDate;
    }

    public String getRequestMethod() {
        return _requestMethod;
    }

    public String getHttpStatus() {
        return _httpStatus;
    }

    public String getRequestUri() {
        return _requestUri;
    }

    public String getRequestQueryString() {
        return _requestQueryString;
    }
}
