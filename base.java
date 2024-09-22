import java.io.FilterInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class base {

    public static void main(String[] args) {
        body body = new body("read.txt");
        while (!body.isEmpty()) {
            System.out.printf("%-5s:%s\n", body.currentLexer(), body.currentToken());
            body.nextToken();
        }

        if (body.isSuccessful()) {
            System.out.println("Done!");
        } else {
            System.out.println(body.errorMessage());
        }

        try (Stream<String> st = Files.lines(Paths.get("read.txt"))) {
            st.forEach(System.out::println);
        } catch (IOException exception) {
            System.out.println("Could not read file / File not found:");
        }

    }
}