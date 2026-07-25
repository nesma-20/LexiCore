package lexicore;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class KeywordExtractor {

    private final Set<String> stopWords;

    public KeywordExtractor() {

        stopWords = new HashSet<>();

        stopWords.add("a");
        stopWords.add("an");
        stopWords.add("the");
        stopWords.add("and");
        stopWords.add("or");
        stopWords.add("but");
        stopWords.add("is");
        stopWords.add("are");
        stopWords.add("was");
        stopWords.add("were");
        stopWords.add("be");
        stopWords.add("been");
        stopWords.add("being");
        stopWords.add("to");
        stopWords.add("of");
        stopWords.add("in");
        stopWords.add("on");
        stopWords.add("at");
        stopWords.add("for");
        stopWords.add("from");
        stopWords.add("with");
        stopWords.add("by");
        stopWords.add("as");
        stopWords.add("it");
        stopWords.add("this");
        stopWords.add("that");
        stopWords.add("these");
        stopWords.add("those");
        stopWords.add("i");
        stopWords.add("you");
        stopWords.add("he");
        stopWords.add("she");
        stopWords.add("we");
        stopWords.add("they");
        stopWords.add("my");
        stopWords.add("your");
        stopWords.add("his");
        stopWords.add("her");
        stopWords.add("our");
        stopWords.add("their");
        stopWords.add("do");
        stopWords.add("does");
        stopWords.add("did");
        stopWords.add("have");
        stopWords.add("has");
        stopWords.add("had");
        stopWords.add("not");
        stopWords.add("so");
        stopWords.add("very");
    }

    public KeywordExtractor(
            Set<String> customStopWords
    ) {

        stopWords = new HashSet<>();

        if (customStopWords != null) {
            stopWords.addAll(customStopWords);
        }
    }

    public Map<String, Integer> extractKeywords(
            ProcessedText processedText,
            int maximumKeywords
    ) {

        Map<String, Integer> keywordFrequencies =
                new HashMap<>();

        if (processedText == null
                || maximumKeywords <= 0) {

            return new LinkedHashMap<>();
        }

        List<String> allTokens =
                processedText.getAllTokens();

        for (String word : allTokens) {

            if (word == null || word.isBlank()) {
                continue;
            }

            if (stopWords.contains(word)) {
                continue;
            }

            int currentFrequency =
                    keywordFrequencies.getOrDefault(
                            word,
                            0
                    );

            keywordFrequencies.put(
                    word,
                    currentFrequency + 1
            );
        }

        List<Map.Entry<String, Integer>>
                sortedKeywords =
                new ArrayList<>(
                        keywordFrequencies.entrySet()
                );

        sortedKeywords.sort(
                (firstEntry, secondEntry) -> {

                    int frequencyComparison =
                            Integer.compare(
                                    secondEntry.getValue(),
                                    firstEntry.getValue()
                            );

                    if (frequencyComparison != 0) {
                        return frequencyComparison;
                    }

                    return firstEntry
                            .getKey()
                            .compareTo(
                                    secondEntry.getKey()
                            );
                }
        );

        Map<String, Integer> topKeywords =
                new LinkedHashMap<>();

        int resultSize =
                Math.min(
                        maximumKeywords,
                        sortedKeywords.size()
                );

        for (int index = 0;
             index < resultSize;
             index++) {

            Map.Entry<String, Integer> entry =
                    sortedKeywords.get(index);

            topKeywords.put(
                    entry.getKey(),
                    entry.getValue()
            );
        }

        return topKeywords;
    }

    public boolean isStopWord(String word) {

        if (word == null) {
            return false;
        }

        return stopWords.contains(
                word.toLowerCase()
        );
    }

    public void addStopWord(String word) {

        if (word == null || word.isBlank()) {
            return;
        }

        stopWords.add(
                word.toLowerCase().trim()
        );
    }

    public Set<String> getStopWords() {
        return new HashSet<>(stopWords);
    }
}
