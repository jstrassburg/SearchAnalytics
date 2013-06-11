package com.directsupply.webloganalytics;

import junit.framework.Assert;
import org.junit.Test;

import java.io.*;

public class WebLogRecordTest {
    @Test
    public void testConstructorSuccess() {
        String goodRecord = "36681298\tAD\t102\t2011-01-24T07:36:27\tGET\t200\t" +
                "/dssi.3.30/ShoppingCart/default.asp\tAdditionType=0&LinesInserted=1&ErrorLines=" +
                "0&InsertCost=$39.89&ReturnToURL=%2Fdssi%2E3%2E30%2FSearch%2FResults%2Easpx%3FSrtBy%" +
                "3DPREVIOUSLYORDERED%26Term%3Dtoilet%2Bpaper%26PID%3D1%26RcPerPg%3D20%26Supp%3D%26Sr" +
                "tOrd%3D1%26OnOG%3DTrue%26OffOG%3DTrue%26POH%3D0%";

        WebLogRecord webLogRecord = new WebLogRecord(goodRecord);
        Assert.assertEquals("36681298", webLogRecord.getSessionId());
        Assert.assertEquals("AD", webLogRecord.getJobCode());
        Assert.assertEquals("102", webLogRecord.getProfileId());
        Assert.assertEquals("2011-01-24T07:36:27", webLogRecord.getRequestDate());
        Assert.assertEquals("GET", webLogRecord.getRequestMethod());
        Assert.assertEquals("200", webLogRecord.getHttpStatus());
        Assert.assertEquals("/dssi.3.30/ShoppingCart/default.asp", webLogRecord.getRequestUri());
        Assert.assertEquals("AdditionType=0&LinesInserted=1&ErrorLines=" +
                "0&InsertCost=$39.89&ReturnToURL=%2Fdssi%2E3%2E30%2FSearch%2FResults%2Easpx%3FSrtBy%" +
                "3DPREVIOUSLYORDERED%26Term%3Dtoilet%2Bpaper%26PID%3D1%26RcPerPg%3D20%26Supp%3D%26Sr" +
                "tOrd%3D1%26OnOG%3DTrue%26OffOG%3DTrue%26POH%3D0%", webLogRecord.getRequestQueryString());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorNullInput() {
        new WebLogRecord(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorNotEnoughFieldsInRecord() {
        new WebLogRecord("foo\tbar");
    }

    @Test
    public void testConstructorWithManyRecords() throws IOException {
        InputStream inputFileStream = new FileInputStream("testInput.txt");
        BufferedReader fileReader = new BufferedReader(new InputStreamReader(inputFileStream));
        String line;
        while ((line = fileReader.readLine()) != null) {
            new WebLogRecord(line);
        }

        fileReader.close();
    }
}
