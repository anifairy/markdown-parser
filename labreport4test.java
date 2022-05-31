//https://howtodoinjava.com/java/io/java-read-file-to-string-examples/

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class MarkdownParse {

    public static ArrayList<String> getLinks(String markdown) {
        ArrayList<String> toReturn = new ArrayList<>();
        // find the next [, then find the ], then find the (, then read link upto next )
        int currentIndex = 0;
        while(true) {
            int openBracket = markdown.indexOf("[", currentIndex);
            if(openBracket == -1) {
                // no new open bracket
                break;
            }
            if(openBracket > 0) {
                if (markdown.charAt(openBracket - 1) == '!') {
                    currentIndex = openBracket + 1;
                    continue;
                }
            }
            int closeBracket = markdown.indexOf("]", openBracket);
            int newLine = markdown.indexOf("\n", openBracket);
            // if no new close bracket
            if (closeBracket == -1) {
                // if no new line, we are done parsing
                if(newLine == -1) {
                    break;
                }
                // otherwise, continue parsing after the newline
                currentIndex = newLine + 1;
                continue;
            }
            // if the newline comes before close bracket, continue parsing after newline
            if(newLine != -1 && newLine < closeBracket) {
                currentIndex = newLine + 1;
                continue;
            }
            int openParen = markdown.indexOf("(", closeBracket);
            if (openParen != closeBracket + 1) {
                // [test](hello)
                // the ] must be one character before (
                currentIndex = closeBracket + 1;
                continue;
            }
            int closeParen = markdown.indexOf(")", openParen);
            int newLine2 = markdown.indexOf("\n", openParen);
            // if no new close paren
            if (closeParen == -1) {
                // if no new line, we are done parsing
                if(newLine2 == -1) {
                    break;
                }
                // otherwise, continue parsing after the newline
                currentIndex = newLine2 + 1;
                continue;
            }
            // if the newline comes before close bracket, continue parsing after newline
            if(newLine2 != -1 && newLine2 < closeParen) {
                currentIndex = newLine2 + 1;
                continue;
            }
            toReturn.add(markdown.substring(openParen + 1, closeParen));
            currentIndex = closeParen + 1;
        }

        return toReturn;
    }


    public static void main(String[] args) throws IOException {
        Path fileName = Path.of(args[0]);
        String content = Files.readString(fileName);
        ArrayList<String> links = getLinks(content);
	    System.out.println(links);
    }
}
