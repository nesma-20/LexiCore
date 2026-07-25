package lexicore;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class TrieNode {

    private final Map<Character, TrieNode> children;

    private boolean endOfWord;

    private int frequency;

    public TrieNode() {

        children = new HashMap<>();

        endOfWord = false;

        frequency = 0;
    }

    public TrieNode getChild(char character) {

        return children.get(character);
    }

    public TrieNode getOrCreateChild(
            char character
    ) {

        return children.computeIfAbsent(
                character,
                key -> new TrieNode()
        );
    }

    public boolean containsChild(
            char character
    ) {

        return children.containsKey(
                character
        );
    }

    public Map<Character, TrieNode>
    getChildren() {

        return Collections.unmodifiableMap(
                children
        );
    }

    public boolean isEndOfWord() {
        return endOfWord;
    }

    public void markAsEndOfWord() {
        endOfWord = true;
    }

    public int getFrequency() {
        return frequency;
    }

    public void incrementFrequency() {
        frequency++;
    }

    public void addFrequency(int amount) {

        if (amount > 0) {
            frequency += amount;
        }
    }
}