package lexicore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PositionalSearchEngine {
    private final Map<String, List<int[]>> positionalIndex = new HashMap<>();
    private List<List<String>> sentenceCorpus = new ArrayList<>();


    public void buildIndex(ProcessedText processedText) {
        positionalIndex.clear();
        if (processedText == null) return;

        this.sentenceCorpus = processedText.getSentenceTokens();

        for (int sIndex = 0; sIndex < sentenceCorpus.size(); sIndex++) {
            List<String> sentence = sentenceCorpus.get(sIndex);
            for (int wIndex = 0; wIndex < sentence.size(); wIndex++) {
                String word = sentence.get(wIndex).toLowerCase();

                positionalIndex.putIfAbsent(word, new ArrayList<>());
                positionalIndex.get(word).add(new int[]{sIndex, wIndex});
            }
        }
    }


    public List<SearchMatch> searchWord(String query) {
        List<SearchMatch> matches = new ArrayList<>();
        if (query == null || query.trim().isEmpty()) return matches;

        String target = query.trim().toLowerCase();
        List<int[]> positions = positionalIndex.get(target);

        if (positions != null) {
            for (int[] pos : positions) {
                int sIndex = pos[0];
                int wIndex = pos[1];
                String snippet = buildSnippet(sIndex, wIndex);
                matches.add(new SearchMatch(sIndex, wIndex, snippet));
            }
        }
        return matches;
    }


    private String buildSnippet(int sentenceIndex, int wordIndex) {
        List<String> sentence = sentenceCorpus.get(sentenceIndex);
        int start = Math.max(0, wordIndex - 2);
        int end = Math.min(sentence.size(), wordIndex + 3);

        StringBuilder snippet = new StringBuilder();
        for (int i = start; i < end; i++) {
            if (i == wordIndex) {
                snippet.append("[").append(sentence.get(i)).append("] ");
            } else {
                snippet.append(sentence.get(i)).append(" ");
            }
        }
        return snippet.toString().trim();
    }

}