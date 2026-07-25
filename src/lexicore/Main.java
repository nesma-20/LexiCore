package lexicore;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) {

        try (
                BufferedReader reader =
                        new BufferedReader(
                                new InputStreamReader(System.in)
                        )
        ) {



            TextLoader textLoader =
                    new TextLoader();

            TextPreprocessor preprocessor =
                    new TextPreprocessor();

            TextAnalyzer analyzer =
                    new TextAnalyzer();

            String originalText =
                    loadInitialText(
                            reader,
                            textLoader
                    );

            if (originalText == null) {

                System.out.println(
                        "LexiCore stopped."
                );

                return;
            }

            ProcessedText processedText =
                    preprocessor.preprocess(
                            originalText
                    );

            // Temporary integration for testing Nesma's Keyword Extraction and Autocomplete classes.
            KeywordExtractor keywordExtractor =
                    new KeywordExtractor();

            AutocompleteService autocompleteService =
                    new AutocompleteService(
                            processedText
                    );

            System.out.println();
            System.out.println(
                    "Text loaded and processed successfully."
            );

            runMainMenu(
                    reader,
                    originalText,
                    processedText,
                    analyzer,
                    keywordExtractor,
                    autocompleteService
            );

        } catch (IOException exception) {

            System.out.println(
                    "Input error: "
                            + exception.getMessage()
            );
        }
    }

    private static String loadInitialText(
            BufferedReader reader,
            TextLoader textLoader
    ) throws IOException {

        while (true) {

            printInputMenu();

            String choice =
                    reader.readLine();

            if (choice == null) {
                return null;
            }

            switch (choice.trim()) {

                case "1": {

                    String keyboardText =
                            textLoader.readFromKeyboard(
                                    reader
                            );

                    if (keyboardText.isBlank()) {

                        System.out.println(
                                "No text was entered."
                        );

                    } else {

                        return keyboardText;
                    }

                    break;
                }

                case "2": {

                    System.out.print(
                            "Enter the absolute file path: "
                    );

                    String filePath =
                            reader.readLine();

                    if (
                            filePath == null
                                    || filePath.isBlank()
                    ) {

                        System.out.println(
                                "No file path was entered."
                        );

                        break;
                    }

                    try {

                        String fileText =
                                textLoader.readFromFile(
                                        filePath
                                );

                        if (fileText.isBlank()) {

                            System.out.println(
                                    "The file is empty."
                            );

                        } else {

                            return fileText;
                        }

                    } catch (IOException exception) {

                        System.out.println(
                                "File input error: "
                                        + exception.getMessage()
                        );
                    }

                    break;
                }

                case "0": {

                    return null;
                }

                default: {

                    System.out.println(
                            "Invalid choice. "
                                    + "Please enter 0, 1, or 2."
                    );
                }
            }
        }
    }

    private static void runMainMenu(
            BufferedReader reader,
            String originalText,
            ProcessedText processedText,
            TextAnalyzer analyzer,
            KeywordExtractor keywordExtractor,
            AutocompleteService autocompleteService
    ) throws IOException {

        while (true) {

            printMainMenu();

            String choice =
                    reader.readLine();

            if (choice == null) {

                System.out.println(
                        "LexiCore stopped."
                );

                return;
            }

            switch (choice.trim()) {

                case "1": {

                    printOriginalText(
                            originalText
                    );

                    break;
                }

                case "2": {

                    printCleanedText(
                            processedText
                    );

                    break;
                }

                case "3": {

                    printSentenceTokens(
                            processedText
                    );

                    break;
                }

                case "4": {

                    printAllTokens(
                            processedText
                    );

                    break;
                }

                case "5": {

                    printTextStatistics(
                            processedText,
                            analyzer
                    );

                    break;
                }

                case "6": {

                    printKeywords(
                            processedText,
                            keywordExtractor
                    );

                    break;
                }

                case "7": {

                    runAutocomplete(
                            reader,
                            autocompleteService
                    );

                    break;
                }

                case "0": {

                    System.out.println(
                            "Thank you for using LexiCore."
                    );

                    return;
                }

                default: {

                    System.out.println(
                            "Invalid choice. "
                                    + "Please enter a number "
                                    + "from 0 to 7."
                    );
                }
            }
        }
    }

    private static void printInputMenu() {

        System.out.println();

        System.out.println(
                "================================="
        );

        System.out.println(
                "        LexiCore Text Input"
        );

        System.out.println(
                "================================="
        );

        System.out.println(
                "1. Enter text from keyboard"
        );

        System.out.println(
                "2. Load text from file"
        );

        System.out.println(
                "0. Exit"
        );

        System.out.print(
                "Choose an option: "
        );
    }

    private static void printMainMenu() {

        System.out.println();

        System.out.println(
                "================================="
        );

        System.out.println(
                "        LexiCore Main Menu"
        );

        System.out.println(
                "================================="
        );

        System.out.println(
                "1. Show original text"
        );

        System.out.println(
                "2. Show cleaned text"
        );

        System.out.println(
                "3. Show sentence tokens"
        );

        System.out.println(
                "4. Show all tokens"
        );

        System.out.println(
                "5. Show text statistics"
        );

        System.out.println(
                "6. Show extracted keywords"
        );

        System.out.println(
                "7. Smart autocomplete"
        );

        System.out.println(
                "0. Exit"
        );

        System.out.print(
                "Choose an option: "
        );
    }

    private static void printOriginalText(
            String originalText
    ) {

        System.out.println();

        System.out.println(
                "========== ORIGINAL TEXT =========="
        );

        System.out.println(
                originalText
        );

        System.out.println(
                "==================================="
        );
    }

    private static void printCleanedText(
            ProcessedText processedText
    ) {

        System.out.println();

        System.out.println(
                "========== CLEANED TEXT =========="
        );

        System.out.println(
                processedText.getCleanedText()
        );

        System.out.println(
                "=================================="
        );
    }

    private static void printSentenceTokens(
            ProcessedText processedText
    ) {

        System.out.println();

        System.out.println(
                "======== SENTENCE TOKENS ========"
        );

        System.out.println(
                processedText.getSentenceTokens()
        );

        System.out.println(
                "================================="
        );
    }

    private static void printAllTokens(
            ProcessedText processedText
    ) {

        System.out.println();

        System.out.println(
                "============ ALL TOKENS ============"
        );

        System.out.println(
                processedText.getAllTokens()
        );

        System.out.println(
                "===================================="
        );
    }

    private static void printTextStatistics(
            ProcessedText processedText,
            TextAnalyzer analyzer
    ) {

        System.out.println();

        System.out.println(
                "========== TEXT STATISTICS =========="
        );

        System.out.println(
                "Number of words: "
                        + analyzer.countWords(
                        processedText
                )
        );

        System.out.println(
                "Number of sentences: "
                        + analyzer.countSentences(
                        processedText
                )
        );

        System.out.println(
                "Number of unique words: "
                        + analyzer.countUniqueWords(
                        processedText
                )
        );

        System.out.println(
                "Number of characters excluding spaces: "
                        + analyzer
                        .countCharactersExcludingSpaces(
                                processedText
                        )
        );

        System.out.println(
                "====================================="
        );
    }

    private static void printKeywords(
            ProcessedText processedText,
            KeywordExtractor keywordExtractor
    ) {

        Map<String, Integer> keywords =
                keywordExtractor.extractKeywords(
                        processedText,
                        5
                );

        System.out.println();

        System.out.println(
                "========== TOP KEYWORDS =========="
        );

        if (keywords.isEmpty()) {

            System.out.println(
                    "No keywords were found."
            );

        } else {

            for (
                    Map.Entry<String, Integer> entry
                    : keywords.entrySet()
            ) {

                System.out.println(
                        entry.getKey()
                                + " : "
                                + entry.getValue()
                );
            }
        }

        System.out.println(
                "=================================="
        );
    }

    private static void runAutocomplete(
            BufferedReader reader,
            AutocompleteService autocompleteService
    ) throws IOException {

        System.out.print(
                "Enter a word prefix: "
        );

        String prefix =
                reader.readLine();

        if (
                prefix == null
                        || prefix.isBlank()
        ) {

            System.out.println(
                    "The prefix cannot be empty."
            );

            return;
        }

        List<String> suggestions =
                autocompleteService.getSuggestions(
                        prefix,
                        5
                );

        System.out.println();

        System.out.println(
                "====== AUTOCOMPLETE RESULTS ======"
        );

        if (suggestions.isEmpty()) {

            System.out.println(
                    "No suggestions were found."
            );

        } else {

            for (String suggestion : suggestions) {

                System.out.println(
                        suggestion
                                + " (frequency: "
                                + autocompleteService
                                .getWordFrequency(
                                        suggestion
                                )
                                + ")"
                );
            }
        }

        System.out.println(
                "=================================="
        );
    }
}