package lexicore;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class TextPreprocessor {

    public ProcessedText preprocess(String originalText) {

        if (originalText == null || originalText.isBlank()) {
            return new ProcessedText(
                    "",
                    new ArrayList<>(),
                    new ArrayList<>()
            );
        }

        String normalizedText =
                originalText
                        .toLowerCase(Locale.ROOT)
                        .replaceAll("\\s+", " ")
                        .trim();

        List<List<String>> sentenceTokens =
                new ArrayList<>();

        List<String> allTokens =
                new ArrayList<>();



        String[] sentences =
                normalizedText.split("[.!?]+");

        for (String sentence : sentences) {

            String cleanedSentence =
                    sentence
                            .replaceAll(
                                    "[^\\p{L}\\p{N}\\s']",
                                    " "
                            )
                            .replaceAll("\\s+", " ")
                            .trim();

            if (cleanedSentence.isEmpty()) {
                continue;
            }

            String[] words =
                    cleanedSentence.split("\\s+");

//كل دورة من حلقة for يتم إنشاء قائمة جديدة وفارغة للجملة الحالية
            List<String> currentSentenceTokens =
                    new ArrayList<>();



            for (String word : words) {

                if (!word.isBlank()) {
                    currentSentenceTokens.add(word);
                    allTokens.add(word);
                }
            }

            if (!currentSentenceTokens.isEmpty()) {
                sentenceTokens.add(
                        currentSentenceTokens
                );
            }
        }

        String cleanedText =
                String.join(" ", allTokens);

        return new ProcessedText(
                cleanedText,
                sentenceTokens,
                allTokens
        );
    }
}