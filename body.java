import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

public class body {
    private StringBuilder userInput = new StringBuilder();
    private Token token;
    private String body;
    private boolean empty = false;
    private String errors = "";
    private Set<Character> blankCharacters = new HashSet<Character>();

    public body (String readFile) {
        try (Stream<String> st = Files.lines(Paths.get(readFile))) {
            st.forEach(userInput::append);
        } catch (IOException exception) {
            empty = true;
            errors = "Could not read file / File not found: " + readFile;
            return;
        }

        blankCharacters.add('\r'); // Carrage Return
        blankCharacters.add('\n'); // New Line
        blankCharacters.add((char) 8);
        blankCharacters.add((char) 9);
        blankCharacters.add((char) 11);
        blankCharacters.add((char) 12);
        blankCharacters.add((char) 32);

        nextToken();
    }

    public void nextToken() {
        if (empty) {
            return;
        }

        if (userInput.length() == 0) {
            empty = true;
            return;
        }

        blankWhiteSpaces();

        if (findNextToken()) {
            return;
        }

        empty = true;

        if (userInput.length() > 0) {
            errors = "Unexpected symbol/Unidentifiable symbol: '" + userInput.charAt(0) + "'";
        }
    }

    private void blankWhiteSpaces() {
        int deleteChars = 0;

        while (blankCharacters.contains(userInput.charAt(deleteChars))) {
            deleteChars++;
        }

        if (deleteChars > 0) {
            userInput.delete(0, deleteChars);
        }
    }

    private boolean findNextToken() {
        for (Token x : Token.values()) {
            int end = x.endOfMatch(userInput.toString());

            if (end != -1) {
                token = x;
                body = userInput.substring(0, end);
                userInput.delete(0, end);
                return true;
            }
        }

        return false;
    }

    public void print() {
        if (token == Token.TK_PRINT) {
            System.out.println(body.substring(5, body.length() - 1));
        }
    }


    public void integer() {
        if (token == Token.INTEGER) {
            if (body.charAt(0) == '-') {
                System.out.println(Integer.parseInt(body.substring(1, body.length())) * -1);
            } else {
                System.out.println(Integer.parseInt(body));
            }
        }
    }


    public void doubleNum() {
        if (token == Token.TK_DOUBLE) {
            if (body.charAt(0) == '-') {
                System.out.println(Double.parseDouble(body.substring(1, body.length())) * -1);
            } else {
                System.out.println(Double.parseDouble(body));
            }
        }
    }

    public void loop() {
        if (token == Token.TK_WHILE || token == Token.TK_IF) {
            System.out.println(body);
        }
    }

    public void comment() {
        if (token == Token.TK_COMMENTS) {
            userInput.delete(0, userInput.length());
        }
    }

    public Token currentToken() {
        return token;
    }

    public String currentLexer() {
        return body;
    }

    public boolean isSuccessful() {
        return errors.isEmpty();
    }

    public String errorMessage() {
        return errors;
    }

    public boolean isEmpty() {
        return empty;
    }
}