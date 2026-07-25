package lexicore;


import java.util.ArrayList;
import java.util.List;

public class PhraseSearchEngine {

    private List<List<String>> sentenceCorpus =
            new ArrayList<>();

    private final TextPreprocessor preprocessor =
            new TextPreprocessor();

    public void buildIndex(
            ProcessedText processedText
    ) {

        sentenceCorpus.clear();

        if (processedText == null) {
            return;
        }

        for (
                List<String> sentence
                : processedText.getSentenceTokens()
        ) {

            sentenceCorpus.add(
                    new ArrayList<>(sentence)
            );
        }
    }

    public List<SearchMatch> searchPhrase(
            String query
    ) {

        List<SearchMatch> matches =
                new ArrayList<>();

        if (
                query == null
                        || query.isBlank()
        ) {

            return matches;
        }

        ProcessedText processedQuery =
                preprocessor.preprocess(
                        query
                );

        List<String> phraseWords =
                processedQuery.getAllTokens();

        if (phraseWords.isEmpty()) {

            return matches;
        }

        for (
                int sentenceIndex = 0;
                sentenceIndex < sentenceCorpus.size();
                sentenceIndex++
        ) {

            List<String> sentence =
                    sentenceCorpus.get(
                            sentenceIndex
                    );

            if (
                    phraseWords.size()
                            > sentence.size()
            ) {

                continue;
            }

            int lastPossibleStart =
                    sentence.size()
                            - phraseWords.size();

            for (
                    int startIndex = 0;
                    startIndex <= lastPossibleStart;
                    startIndex++
            ) {

                if (
                        phraseMatchesAt(
                                sentence,
                                phraseWords,
                                startIndex
                        )
                ) {

                    String snippet =
                            buildSnippet(
                                    sentenceIndex,
                                    startIndex,
                                    phraseWords.size()
                            );

                    matches.add(
                            new SearchMatch(
                                    sentenceIndex,
                                    startIndex,
                                    snippet
                            )
                    );
                }
            }
        }

        return matches;
    }

    private boolean phraseMatchesAt(
            List<String> sentence,
            List<String> phraseWords,
            int startIndex
    ) {

        for (
                int offset = 0;
                offset < phraseWords.size();
                offset++
        ) {

            String sentenceWord =
                    sentence.get(
                            startIndex + offset
                    );

            String phraseWord =
                    phraseWords.get(offset);

            if (
                    !sentenceWord.equalsIgnoreCase(
                            phraseWord
                    )
            ) {

                return false;
            }
        }

        return true;
    }

    private String buildSnippet(
            int sentenceIndex,
            int phraseStartIndex,
            int phraseLength
    ) {

        List<String> sentence =
                sentenceCorpus.get(
                        sentenceIndex
                );

        int snippetStart =
                Math.max(
                        0,
                        phraseStartIndex - 2
                );

        int phraseEndIndex =
                phraseStartIndex
                        + phraseLength
                        - 1;

        int snippetEnd =
                Math.min(
                        sentence.size(),
                        phraseEndIndex + 3
                );

        StringBuilder snippet =
                new StringBuilder();

        for (
                int index = snippetStart;
                index < snippetEnd;
                index++
        ) {

            if (index == phraseStartIndex) {

                snippet.append("[");
            }

            snippet.append(
                    sentence.get(index)
            );

            if (index == phraseEndIndex) {

                snippet.append("]");
            }

            if (index < snippetEnd - 1) {

                snippet.append(" ");
            }
        }

        return snippet.toString();
    }
}