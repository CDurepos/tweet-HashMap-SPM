package twitterpack;

import java.io.IOException;

public class program8 {
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        MyHashMap<Tweet, Boolean> trainData = MyDataReader.readDataToHashMap(args[0]);
        long end = System.currentTimeMillis();

        System.out.println( (end-start) + " milliseconds to build the train hash map");
        System.out.println(trainData.resizeCount + " resizes to build the train hash map");

        MySentimentAnalysisModel model = new MySentimentAnalysisModel(trainData);

        start = System.currentTimeMillis();
        MyHashMap<Tweet, Boolean> testData = MyDataReader.readDataToHashMap(args[1]);
        end = System.currentTimeMillis();

        System.out.println( (end-start) + " milliseconds to build the test hash map");
        System.out.println(testData.resizeCount + " resizes to build the test hash map");

        System.out.println("Ratio of correct predictions: " + model.predict(testData));




    }
}
