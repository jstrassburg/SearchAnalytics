package com.directsupply.webloganalytics;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class SearchFitnessReduce extends Reducer<Text, Text, Text, Text> {
    @Override
    public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
        int addToCartCount = 0;
        int viewProductDetailCount = 0;

        for (Text value : values) {
            String stringValue = value.toString();
            String[] valueParts = stringValue.split("\t");
            if (valueParts.length != 2){
                throw new IOException("Expected 2 tab-separated parts in the value, received: " + valueParts);
            }

            if (valueParts[0].equals("A")) {
                addToCartCount += Integer.parseInt(valueParts[1]);
            }
            else if (valueParts[0].equals("D")) {
                viewProductDetailCount += Integer.parseInt(valueParts[1]);
            }
        }

        Text reduceOutput = new Text();
        reduceOutput.set(String.format("%s\t%s", addToCartCount, viewProductDetailCount));

        context.write(key, reduceOutput);
    }
}
