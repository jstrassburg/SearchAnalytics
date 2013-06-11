package com.directsupply.webloganalytics;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class SearchFitnessMap extends Mapper<LongWritable, Text, Text, Text> {
    @Override
    public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String line = value.toString();

        WebLogRecord record = new WebLogRecord(line);
        String url = String.format("%s?%s", record.getRequestUri(), record.getRequestQueryString());

        boolean isAddToCartPage = AnalyticsMatcher.isAddToCartPage(url);
        boolean isProductDetailPage = AnalyticsMatcher.isProductDetailPage(url);

        if (isAddToCartPage || isProductDetailPage) {
            SearchTermCatId searchTermCatId = AnalyticsMatcher.extractSearchTermAndCatIdFromUri(url);

            if (searchTermCatId.getCatId() != null && searchTermCatId.getSearchTerm() != null) {
                String recordType = isAddToCartPage ? "A" : "D";

                emitMapOutputRecords(context, record, searchTermCatId, recordType);
            }
        }
    }

    /**
     * Writes the different records that this mapper outputs.
     * 1.) SEARCH_TERM|CATID|JOB_CODE|PROFILE_ID
     * 2.) SEARCH_TERM|CATID|*|PROFILE_ID
     * 3.) SEARCH_TERM|CATID|JOB_CODE|*
     * 4.) SEARCH_TERM|CATID|*|*
     */
    private void emitMapOutputRecords(
            Context context, WebLogRecord record, SearchTermCatId searchTermCatId, String recordType)
            throws IOException, InterruptedException {

        String mapOutputValue = String.format("%s\t1", recordType);

        String mapOutputKey = computeKey(
                searchTermCatId.getSearchTerm(), searchTermCatId.getCatId(),
                record.getJobCode(), record.getProfileId());

        emitKeyValue(mapOutputKey, mapOutputValue, context);

        mapOutputKey = computeKey(
                searchTermCatId.getSearchTerm(), searchTermCatId.getCatId(),
                "*", record.getProfileId());

        emitKeyValue(mapOutputKey, mapOutputValue, context);

        mapOutputKey = computeKey(
                searchTermCatId.getSearchTerm(), searchTermCatId.getCatId(),
                record.getJobCode(), "*");

        emitKeyValue(mapOutputKey, mapOutputValue, context);

        mapOutputKey = computeKey(
                searchTermCatId.getSearchTerm(), searchTermCatId.getCatId(),
                "*", "*");

        emitKeyValue(mapOutputKey, mapOutputValue, context);
    }

    private void emitKeyValue(String key, String value, Context context) throws IOException, InterruptedException {
        Text textKey = new Text();
        textKey.set(key);
        Text textValue = new Text();
        textValue.set(value);

        context.write(textKey, textValue);
    }

    private String computeKey(String term, String catId, String jobCode, String profileId) {
        return String.format("%s|%s|%s|%s",
                sanitizeKeyString(term), sanitizeKeyString(catId),
                sanitizeKeyString(jobCode), sanitizeKeyString(profileId));
    }

    private String sanitizeKeyString(String keyPart) {
        return keyPart.replaceAll("\\|", "~");
    }
}
