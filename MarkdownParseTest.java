import static org.junit.Assert.*;
import org.junit.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class MarkdownParseTest {

    @Test
    public void testFile1() throws IOException {
        String contents= Files.readString(Path.of("./test-file.md"));
        List<String> expect = List.of("https://something.com", "some-thing.html");
        assertEquals(MarkdownParse.getLinks(contents), expect);
    }

    @Test
    public void testFile2() throws IOException {
        String contents= Files.readString(Path.of("./test-file2.md"));
        List<String> expect = List.of("https://something.com", "some-page.html");
        assertEquals(MarkdownParse.getLinks(contents), expect);
    }

    @Test
    public void testLinkAtBeginning() {
        String contents= "[link title](a.com)";
        List<String> expect = List.of("a.com");
        assertEquals(MarkdownParse.getLinks(contents), expect);
    }

    @Test
    public void testSpaceInURL() {
        String contents = "[title](space in-url.com)";
        List<String> expect = List.of();
        assertEquals(MarkdownParse.getLinks(contents), expect);
    }

    @Test
    public void testSpaceAfterParen() {
        String contents = "[title]( space-in-url.com)";
        List<String> expect = List.of("space-in-url.com");
        assertEquals(expect, MarkdownParse.getLinks(contents));
    }

    @Test
    public void testSpaceBeforeParen() {
        String contents = "[title]   (should-not-count.com)";
        List<String> expect = List.of();
        assertEquals(MarkdownParse.getLinks(contents), expect);
    }

    @Test
    public void testNestedParens() throws IOException {
        String contents = Files.readString(Path.of("test-parens-inside-link.md"));
        List<String> expect = List.of("something.com()", "something.com((()))", "something.com", "boring.com");
        assertEquals(expect, MarkdownParse.getLinks(contents));
    }

    @Test
    public void testMissingCloseParen() {
        String contents= "[link title](a.com";

    public void testMissingCloseParen() throws IOException {
        String contents = Files.readString(Path.of("test-missing-paren-plus-test-file2.md"));
        List<String> expect = List.of();
        assertEquals(MarkdownParse.getLinks(contents), expect);
        }
    
    }
    @Test
    public void myTestSnip1() throws IOException {
        String contents = Files.readString(Path.of("test-snip-1.md"));
        List<String> expect = List.of('`[a link](url.com)','another link`','cod[e', 'code]');
        assertEquals(expect, MarkdownParse.getLinks(contents));
    }
    @Test
    public void myTestSnip2() throws IOException {
        String contents = Files.readString(Path.of("test-snip-2.md"));
        List<String> expect = List.of('[a nested link](b.com)','a nested parenthesized url`','some escaped [ brackets ]s');
        assertEquals(expect, MarkdownParse.getLinks(contents));
    }\
    @Test
    public void myTestSnip3() throws IOException {
        String contents = Files.readString(Path.of("test-snip-3.md"));
        List<String> expect = List.of('[this title text is really long and takes up more than one line and has some line breaks]( https://www.twitter.com )','this title text is really long and takes up more than one line',"[this link doesn't have a closing parenthesis](github.com And there's still some more text after that. [this link doesn't have a closing parenthesis for a while](https://cse.ucsd.edu/) And then there's more text");
        assertEquals(expect, MarkdownParse.getLinks(contents));
    }
}
