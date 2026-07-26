package lexicore;


public class ReplacementResult {

    private final String targetWord;
    private final String replacementWord;
    private final int mutationCount;
    private final long processingTimeMillis;
    private final TextState resultingState;

    public ReplacementResult(
            String targetWord,
            String replacementWord,
            int mutationCount,
            long processingTimeMillis,
            TextState resultingState
    ) {

        this.targetWord = targetWord;
        this.replacementWord = replacementWord;
        this.mutationCount = mutationCount;
        this.processingTimeMillis = processingTimeMillis;
        this.resultingState = resultingState;
    }

    public String getTargetWord() {
        return targetWord;
    }

    public String getReplacementWord() {
        return replacementWord;
    }

    public int getMutationCount() {
        return mutationCount;
    }

    public long getProcessingTimeMillis() {
        return processingTimeMillis;
    }

    public TextState getResultingState() {
        return resultingState;
    }

    public boolean wasSuccessful() {
        return mutationCount > 0;
    }

    @Override
    public String toString() {

        if (!wasSuccessful()) {

            return "No occurrences of \""
                    + targetWord
                    + "\" were found. ("
                    + processingTimeMillis
                    + " ms)";
        }

        return "Replaced \""
                + targetWord
                + "\" with \""
                + replacementWord
                + "\" | Mutations: "
                + mutationCount
                + " | Time: "
                + processingTimeMillis
                + " ms";
    } }
