package com.directsupply.webloganalytics;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

public class SearchFitness {
    public static void main(String[] args) throws Exception {
        Configuration configuration = new Configuration();

        Job job = new Job(configuration,  "WebLogAnalytics");
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);

        // this wasn't in the example but was needed for Amazon EMR
        // to find the classes
        job.setJarByClass(SearchFitnessMap.class);

        job.setMapperClass(SearchFitnessMap.class);
        job.setReducerClass(SearchFitnessReduce.class);

        job.setInputFormatClass(TextInputFormat.class);
        job.setOutputFormatClass(TextOutputFormat.class);

        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        job.waitForCompletion(true);
    }


}
