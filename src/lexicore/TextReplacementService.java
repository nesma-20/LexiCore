package lexicore;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class TextReplacementService {

    public ReplacementResult replaceWord(
            TextState currentState,
            String targetWord,
            String replacementWord
    ) {

        return replaceWord(
                currentState,
                targetWord,
                replacementWord,
                null
        );
    }

    public ReplacementResult replaceWord(
            TextState currentState,
            String targetWord,
            String replacementWord,
            UndoRedoManager undoRedoManager
    ) {

        long startTime = System.nanoTime();

        if (currentState == null
                || currentState.getProcessedText() == null
                || targetWord == null
                || targetWord.isBlank()
                || replacementWord == null
                || replacementWord.isBlank()) {

            long elapsedMillis =
                    (System.nanoTime() - startTime)
                            / 1_000_000;

            return new ReplacementResult(
                    targetWord,
                    replacementWord,
                    0,
                    elapsedMillis,
                    currentState
            );
        }

        String normalizedTarget =
                targetWord
                        .toLowerCase(Locale.ROOT)
                        .trim();

        String normalizedReplacement =
                replacementWord
                        .toLowerCase(Locale.ROOT)
                        .trim();

        ProcessedText oldProcessedText =
                currentState.getProcessedText();

        List<List<String>> newSentenceTokens =
                new ArrayList<>();

        List<String> newAllTokens =
                new ArrayList<>();

        int mutationCount = 0;

        for (
                List<String> sentence
                : oldProcessedText.getSentenceTokens()
        ) {

            List<String> newSentence =
                    new ArrayList<>();

            for (String word : sentence) {

                if (
                        word.equalsIgnoreCase(
                                normalizedTarget
                        )
                ) {

                    newSentence.add(
                            normalizedReplacement
                    );

                    newAllTokens.add(
                            normalizedReplacement
                    );

                    mutationCount++;

                } else {

                    newSentence.add(word);

                    newAllTokens.add(word);
                }
            }

            newSentenceTokens.add(newSentence);
        }

        long elapsedMillis =
                (System.nanoTime() - startTime)
                        / 1_000_000;

        if (mutationCount == 0) {

            return new ReplacementResult(
                    targetWord,
                    replacementWord,
                    0,
                    elapsedMillis,
                    currentState
            );
        }

        String newCleanedText =
                String.join(" ", newAllTokens);

        ProcessedText newProcessedText =
                new ProcessedText(
                        newCleanedText,
                        newSentenceTokens,
                        newAllTokens
                );

        TextState newState =
                new TextState(
                        newCleanedText,
                        newProcessedText,
                        "Replaced \""
                                + normalizedTarget
                                + "\" -> \""
                                + normalizedReplacement
                                + "\""
                );

        if (undoRedoManager != null) {

            undoRedoManager.pushNewState(newState);
        }

        return new ReplacementResult(
                targetWord,
                replacementWord,
                mutationCount,
                elapsedMillis,
                newState
        );
    }
}