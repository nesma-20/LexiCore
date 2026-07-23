package lexicore;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TextAnalyzer {

    public int countWords(
            ProcessedText processedText
    ) {

        List<String> allTokens =
                processedText.getAllTokens();

        return allTokens.size();
    }

    public int countSentences(
            ProcessedText processedText
    ) {

        List<List<String>> sentenceTokens =
                processedText.getSentenceTokens();

        return sentenceTokens.size();
    }

    public int countUniqueWords(
            ProcessedText processedText
    ) {

        List<String> allTokens =
                processedText.getAllTokens();

        Set<String> uniqueWords =
                new HashSet<>(allTokens);

        return uniqueWords.size();
    }

    public int countCharacters(
            String originalText
    ) {

        if (originalText == null) {
            return 0;
        }

        return originalText.length();
    }

    public Map<String, Integer> countWordFrequencies(
            ProcessedText processedText
    ) {

        Map<String, Integer> wordFrequencies =
                new HashMap<>();

        List<String> allTokens =
                processedText.getAllTokens();

        for (String word : allTokens) {

            int currentFrequency =
                    wordFrequencies.getOrDefault(
                            word,
                            0
                    );

            wordFrequencies.put(
                    word,
                    currentFrequency + 1
            );
        }

        return wordFrequencies;
    }
}