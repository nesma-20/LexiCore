package lexicore;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class Trie {

    private final TrieNode root;

    public Trie() {
        root = new TrieNode();
    }

    public void insert(String word) {

        insert(word, 1);
    }

    public void insert(
            String word,
            int frequency
    ) {

        String normalizedWord =
                normalizeToken(word);

        if (normalizedWord.isBlank()
                || frequency <= 0) {

            return;
        }

        TrieNode currentNode = root;

        for (int index = 0;
             index < normalizedWord.length();
             index++) {

            char currentCharacter =
                    normalizedWord.charAt(index);

            currentNode =
                    currentNode.getOrCreateChild(
                            currentCharacter
                    );
        }

        currentNode.markAsEndOfWord();

        currentNode.addFrequency(
                frequency
        );
    }

    public boolean search(String word) {

        String normalizedWord =
                normalizeToken(word);

        if (normalizedWord.isBlank()) {
            return false;
        }

        TrieNode finalNode =
                findNode(normalizedWord);

        return finalNode != null
                && finalNode.isEndOfWord();
    }

    public boolean startsWith(
            String prefix
    ) {

        String normalizedPrefix =
                normalizeToken(prefix);

        if (normalizedPrefix.isBlank()) {
            return false;
        }

        return findNode(
                normalizedPrefix
        ) != null;
    }

    public int getFrequency(String word) {

        String normalizedWord =
                normalizeToken(word);

        if (normalizedWord.isBlank()) {
            return 0;
        }

        TrieNode finalNode =
                findNode(normalizedWord);

        if (finalNode == null
                || !finalNode.isEndOfWord()) {

            return 0;
        }

        return finalNode.getFrequency();
    }

    public List<String> getSuggestions(
            String prefix,
            int maximumSuggestions
    ) {

        List<String> suggestions =
                new ArrayList<>();

        if (maximumSuggestions <= 0) {
            return suggestions;
        }

        String normalizedPrefix =
                normalizeToken(prefix);

        if (normalizedPrefix.isBlank()) {
            return suggestions;
        }

        TrieNode prefixNode =
                findNode(normalizedPrefix);

        if (prefixNode == null) {
            return suggestions;
        }

        List<Map.Entry<String, Integer>>
                collectedWords =
                new ArrayList<>();

        StringBuilder currentWord =
                new StringBuilder(
                        normalizedPrefix
                );

        collectWords(
                prefixNode,
                currentWord,
                collectedWords
        );

        collectedWords.sort(
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

        int resultSize =
                Math.min(
                        maximumSuggestions,
                        collectedWords.size()
                );

        for (int index = 0;
             index < resultSize;
             index++) {

            suggestions.add(
                    collectedWords
                            .get(index)
                            .getKey()
            );
        }

        return suggestions;
    }

    private TrieNode findNode(
            String text
    ) {

        TrieNode currentNode = root;

        for (int index = 0;
             index < text.length();
             index++) {

            char currentCharacter =
                    text.charAt(index);

            currentNode =
                    currentNode.getChild(
                            currentCharacter
                    );

            if (currentNode == null) {
                return null;
            }
        }

        return currentNode;
    }

    private void collectWords(
            TrieNode currentNode,
            StringBuilder currentWord,
            List<Map.Entry<String, Integer>>
                    collectedWords
    ) {

        if (currentNode.isEndOfWord()) {

            collectedWords.add(
                    Map.entry(
                            currentWord.toString(),
                            currentNode.getFrequency()
                    )
            );
        }

        for (
                Map.Entry<Character, TrieNode>
                        childEntry
                : currentNode
                .getChildren()
                .entrySet()
        ) {

            currentWord.append(
                    childEntry.getKey()
            );

            collectWords(
                    childEntry.getValue(),
                    currentWord,
                    collectedWords
            );

            currentWord.deleteCharAt(
                    currentWord.length() - 1
            );
        }
    }

    private String normalizeToken(
            String token
    ) {

        if (token == null) {
            return "";
        }

        return token
                .toLowerCase(Locale.ROOT)
                .replaceAll(
                        "[^\\p{L}\\p{N}']",
                        ""
                )
                .trim();
    }
}