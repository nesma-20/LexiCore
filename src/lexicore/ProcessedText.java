package lexicore;

import java.util.List;

public class ProcessedText {

    private final String cleanedText;
    private final List<List<String>> sentenceTokens;
    private final List<String> allTokens;

    public ProcessedText(
            String cleanedText,
            List<List<String>> sentenceTokens,
            List<String> allTokens
    ) {
        this.cleanedText = cleanedText;
        this.sentenceTokens = sentenceTokens;
        this.allTokens = allTokens;
    }

    public String getCleanedText() {
        return cleanedText;
    }

    public List<List<String>> getSentenceTokens() {
        return sentenceTokens;
    }

    public List<String> getAllTokens() {
        return allTokens;
    }
}