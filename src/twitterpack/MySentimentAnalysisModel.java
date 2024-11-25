package twitterpack;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class MySentimentAnalysisModel {
    private MyHashMap<String, Integer> negative, positive;
    private MyHashMap<String, Integer> metadataPositive, metadataNegative;

    public MySentimentAnalysisModel(MyHashMap<Tweet, Boolean> trainData) {
        this.positive = new MyHashMap<>();
        this.negative = new MyHashMap<>();

        for (Tweet tweet : trainData) {
            List<String> terms = filter(tweet.getText());
            for (String term : terms) {
                if (term.isEmpty())
                    continue;
                MyHashMap<String, Integer> targetMap = trainData.get(tweet) ? positive : negative;

                targetMap.put(term, targetMap.get(term) == null ? 1 : targetMap.get(term) + 1);
            }
        }
    }

    private List<String> filter(String text) {
        List<String> terms = new ArrayList<>();
        Pattern pattern = Pattern.compile("[^a-zA-Z\\s]");
        String alphChars = pattern.matcher(text).replaceAll(""); // Remove non-alphabetic characters
        String[] tokens = alphChars.toLowerCase().split("\\s+"); // Split by whitespace
        for (String token : tokens) {
            if (!isStopword(token)) { // Check if it's not a stopword
                terms.add(token);
            }
        }
        return terms;
    }

    private boolean isStopword(String word) {
        String[] stopwords = {
                "i", "me", "my", "myself", "we", "our", "ours", "ourselves", "you", "your", "yours", "yourself", "yourselves", "he", "him", "his", "himself", "she",
                "her", "hers", "herself", "it", "its", "itself", "they", "them", "their", "theirs", "themselves", "what", "which", "who", "whom", "this", "that", "these",
                "those", "am", "is", "are", "was", "were", "be", "been", "being", "has", "had", "having", "do", "does", "did", "doing", "a", "an", "the", "and",
                "but", "if", "or", "because", "as", "until", "while", "of", "at", "by", "for", "with", "about", "between", "into", "through", "during",
                "before", "after", "to", "from", "up", "down", "in", "out", "on", "off", "further", "then", "once",
                "here", "there", "when", "where", "why", "how", "all", "any", "both", "each", "other", "some", "such", "only", "own", "same", "so", "than", "will","just"
        };
        for (String stopword : stopwords) {
            if (stopword.equals(word)) {
                return true;
            }
        }
        return false;
    }

    public MyHashMap<String, Integer> getPositiveWords() {
        return positive;
    }

    public MyHashMap<String, Integer> getNegativeWords() {
        return negative;
    }

    public double predict(MyHashMap<Tweet, Boolean> data) {
        int total = 0;
        int correct = 0;
        for (Tweet tweet : data) {
            boolean prediction = prediction(tweet);
            if (prediction == tweet.isSentimentPositive()) {
                correct++;
            }

            total++;
        }

        return (double) correct / total;
    }

    private boolean prediction(Tweet tweet) {
        int score = 0;
        List<String> terms = filter(tweet.getText());
        for (String term : terms) {
            if ( positive.get(term) != null )
                score += positive.get(term);

            if ( negative.get(term) != null )
                score -= negative.get(term);
        }

        return score >= 0;
    }

    private double calculateIDF(String term, int totalDocuments) {
        int docCount = 0;
        if (positive.get(term) != null) docCount++;
        if (negative.get(term) != null) docCount++;

        if (docCount == 0) return 0;
        return Math.log((double) totalDocuments / docCount);
    }
}
