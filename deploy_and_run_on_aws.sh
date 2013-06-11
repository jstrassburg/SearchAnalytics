#!/bin/bash

S3_BASE=http://s3.amazonaws.com/search-tuning-analytics
JAR_PATH=./out/artifacts/WebLogAnalytics_jar/WebLogAnalytics.jar

s3curl.pl --id=direct_supply --debug --delete -- $S3_BASE/WebLogAnalytics.jar -v
s3curl.pl --id=direct_supply --debug --put $JAR_PATH -- $S3_Base/WebLogAnalytics.jar -v
