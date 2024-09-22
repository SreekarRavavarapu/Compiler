import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum Token {

    TK_PRINT("base"),
    TK_STRING("text"),
    TK_INTEGER("num"),
    TK_DOUBLE("dec"),
    TK_BOOL("bool"),
    TK_INPUT("ask"),
    TK_COMMENTS("\\@"),
    TK_PLUS("\\+"),
    TK_MINUS("\\-"),
    TK_MUL("\\*"),
    TK_DIV("/"),
    TK_NOT("!"),
    TK_AND("&"),
    TK_OR("\\|"),
    TK_LESS("<"),
    TK_LEG("<="),
    TK_GT(">"),
    TK_GEQ(">="),
    TK_ASSIGN("=="),
    TK_EQUALS("="),
    TK_PARENOPEN("\\("),
    TK_PARENCLOSE("\\)"),
    TK_SEMI(";"),
    TK_COMMA(","),
    TK_IF("if"),
    TK_ELSE("else"),
    TK_WHILE("while"),
    TK_OPENBRACKET("\\{"),
    TK_CLOSEBRACKET("\\}"),
    INTEGER("\\d"),
    STRING("\"[^\"]+\""),
    VARIABLE("\\w+");

    private final Pattern pattern;

    Token(String regex) {
        pattern = Pattern.compile("^" + regex);
    }

    int endOfMatch(String text) {
        Matcher matchTokens = pattern.matcher(text);

        if (matchTokens.find()) {
            return matchTokens.end();
        }
        return -1;
    }


}
