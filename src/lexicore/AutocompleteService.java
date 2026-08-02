package lexicore;

import java.util.ArrayList;
import java.util.List;

public class AutocompleteService {

    private Trie trie;

    public AutocompleteService() {
        trie = new Trie();
    }

    public AutocompleteService(
            ProcessedText processedText
    ) {

        trie = new Trie();

        buildIndex(processedText);
    }

    public void buildIndex(
            ProcessedText processedText
    ) {

        trie = new Trie();

        if (processedText == null) {
            return;
        }

        List<String> allTokens =
                processedText.getAllTokens();

        for (String word : allTokens) {

            if (word == null
                    || word.isBlank()) {

                continue;
            }


            trie.insert(word);
        }
    }

    public List<String> getSuggestions(
            String prefix,
            int maximumSuggestions
    ) {

        if (prefix == null
                || prefix.isBlank()
                || maximumSuggestions <= 0) {

            return new ArrayList<>();
        }

        return trie.getSuggestions(
                prefix,
                maximumSuggestions
        );
    }

    public boolean containsWord(
            String word
    ) {

        return trie.search(word);
    }

    public boolean containsPrefix(
            String prefix
    ) {

        return trie.startsWith(prefix);
    }

    public int getWordFrequency(
            String word
    ) {

        return trie.getFrequency(word);
    }

    public void recordWordUsage(
            String word
    ) {

        if (word == null || word.isBlank()) {
            return;
        }

        trie.insert(word);
    }

    public void rebuildIndex(
            ProcessedText processedText
    ) {

        buildIndex(processedText);
    }
}