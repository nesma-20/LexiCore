package lexicore;


public class TextState {

    private final String rawText;
    private final ProcessedText processedText;
    private final String description;

    public TextState(
            String rawText,
            ProcessedText processedText
    ) {

        this(
                rawText,
                processedText,
                "Text state"
        );
    }

    public TextState(
            String rawText,
            ProcessedText processedText,
            String description
    ) {

        this.rawText = rawText;

        this.processedText = processedText;

        this.description =
                (description == null
                        || description.isBlank())
                        ? "Text state"
                        : description;
    }

    public String getRawText() {
        return rawText;
    }

    public ProcessedText getProcessedText() {
        return processedText;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {

        return description
                + " ("
                + processedText.getAllTokens().size()
                + " tokens)";
    }
}
