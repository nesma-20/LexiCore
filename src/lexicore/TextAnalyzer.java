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

        if (processedText == null) {
            return 0;
        }

        List<String> allTokens =
                processedText.getAllTokens();

        return allTokens.size();
    }

    public int countSentences(
            ProcessedText processedText
    ) {

        if (processedText == null) {
            return 0;
        }

        List<List<String>> sentenceTokens =
                processedText.getSentenceTokens();

        return sentenceTokens.size();
    }

    public int countUniqueWords(
            ProcessedText processedText
    ) {

        if (processedText == null) {
            return 0;
        }

        List<String> allTokens =
                processedText.getAllTokens();

        Set<String> uniqueWords =
                new HashSet<>(allTokens);

        return uniqueWords.size();
    }

    public int countCharactersExcludingSpaces(
            ProcessedText processedText
    ) {

        if (processedText == null) {
            return 0;
        }

        String cleanedText =
                processedText.getCleanedText();

        int characterCount = 0;

        for (int index = 0;
             index < cleanedText.length();
             index++) {

            char currentCharacter =
                    cleanedText.charAt(index);

            if (!Character.isWhitespace(
                    currentCharacter
            )) {
                characterCount++;
            }
        }

        return characterCount;
    }

    public Map<Character, Integer>
    countCharacterFrequencies(
            ProcessedText processedText
    ) {

        Map<Character, Integer>
                characterFrequencies =
                new HashMap<>();

        if (processedText == null) {
            return characterFrequencies;
        }

        String cleanedText =
                processedText.getCleanedText();

        for (int index = 0;
             index < cleanedText.length();
             index++) {

            char currentCharacter =
                    cleanedText.charAt(index);

            if (Character.isWhitespace(
                    currentCharacter
            )) {
                continue;
            }

            int currentFrequency =
                    characterFrequencies
                            .getOrDefault(
                                    currentCharacter,
                                    0
                            );

            characterFrequencies.put(
                    currentCharacter,
                    currentFrequency + 1
            );
        }

        return characterFrequencies;
    }

    public Map<String, Integer>
    countWordFrequencies(
            ProcessedText processedText
    ) {

        Map<String, Integer> wordFrequencies =
                new HashMap<>();

        if (processedText == null) {
            return wordFrequencies;
        }

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