package lexicore;

public class SearchMatch {
    private final int sentenceIndex;
    private final int wordIndex;
    private final String snippet;

    public SearchMatch(int sentenceIndex, int wordIndex, String snippet) {
        this.sentenceIndex = sentenceIndex;
        this.wordIndex = wordIndex;
        this.snippet = snippet;
    }

    public int getSentenceIndex() {
        return sentenceIndex;
    }

    public int getWordIndex() {
        return wordIndex;
    }

    public String getSnippet() {
        return snippet;
    }

    @Override
    public String toString() {
        return "Sentence #" + (sentenceIndex + 1) + " (Word pos: " + (wordIndex + 1) + ") -> \"" + snippet + "\"";
    }
}
