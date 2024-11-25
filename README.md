README

    Tweetpack is a package built and designed for use in Assignment 8 of the University of Southern
    Maine's COS285 Data Structures course, taught by Dr. Behrooz Mansouri.

    This program is intended to operate using provided files named 'tweets_test.tsv' and 'tweets_train.tsv'

Configuration

    In order to use this program, one must have a Java compiler and Runtime environment installed.

Operation

    In order to use the program as intended, one must first compile the java files within the
    package. Following compilation, one may run the program by executing 'program8.java' in the 'tweetpack'
    package and passing the file paths of 'tweets_train.tsv' and 'tweets_test.tsv' to the console, as input.

    These are the only two lines that should be necessary to use the associated program...

    'javac ~/tweetpack/*.java'
    'java ~/tweetpack/program8 ~/tweets_train.tsv ~/tweets_test.tsv'

    Upon execution, this program will output the following:
    The time to build the hash map of data from 'tweets_train.tsv'
    The number of table resizes for the building of the hash map of data from 'tweets_train.tsv'
    The time to build the hash map of data from 'tweets_test.tsv'
    The number of table resizes for the building of the hash map of data from 'tweets_test.tsv'
    The sentiment prediction accuracy for the provided model and given data

INCLUDED FILES

    Within the flightpack package, the following files are present...

    MyDataReader.java                   (By Dr. Behrooz Mansouri)
    MyHashMap.java
    MySentimentAnalysisModel.java
    program8.java
    Tweet.java                          (By Dr. Behrooz Mansouri)

    Other files included in this program...

    README.md                           (THIS FILE)
    tweets_train.tsv                    (Data)
    tweets_test.tsv                     (Data)



