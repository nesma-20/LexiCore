package lexicore;



import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;

public class TextLoader {

    private static final String END_SENTINEL = "$$END_TEXT$$";

    public String readFromKeyboard(BufferedReader reader)
            throws IOException {

        StringBuilder textBuilder = new StringBuilder();

        System.out.println();
        System.out.println("Enter or paste your text.");
        System.out.println(
                "Type " + END_SENTINEL
                        + " on a separate line to finish:"
        );

        while (true) {
            String line = reader.readLine();

            if (line == null
                    || line.trim().equals(END_SENTINEL)) {
                break;
            }

            if (textBuilder.length() > 0) {
                textBuilder.append(System.lineSeparator());
            }

            textBuilder.append(line);
        }

        return textBuilder.toString();
    }

    public String readFromFile(String filePath)
            throws IOException {

        if (filePath == null || filePath.isBlank()) {
            throw new IOException(
                    "The file path cannot be empty."
            );
        }

        Path path;

        try {
            path = Path.of(filePath.trim());
        } catch (InvalidPathException exception) {
            throw new IOException(
                    "The file path is invalid."
            );
        }

        if (!path.isAbsolute()) {
            throw new IOException(
                    "Please enter an absolute file path."
            );
        }

        if (!Files.exists(path)) {
            throw new IOException(
                    "The file does not exist."
            );
        }

        if (!Files.isRegularFile(path)) {
            throw new IOException(
                    "The path does not point to a regular file."
            );
        }

        if (!Files.isReadable(path)) {
            throw new IOException(
                    "The file cannot be read."
            );
        }

        StringBuilder textBuilder = new StringBuilder();

        try (BufferedReader fileReader =
                     Files.newBufferedReader(
                             path,
                             StandardCharsets.UTF_8
                     )) {

            String line;

            while ((line = fileReader.readLine()) != null) {

                if (textBuilder.length() > 0) {
                    textBuilder.append(
                            System.lineSeparator()
                    );
                }

                textBuilder.append(line);
            }
        }

        return textBuilder.toString();
    }
}
