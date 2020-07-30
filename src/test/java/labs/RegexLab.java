package labs;

import org.junit.jupiter.api.Test;

/**
 * gakshintala created on 5/14/20.
 */
public class RegexLab {
    @Test
    void multiLineRegex() {
        var original = """
                /* blah */
                    // /* foo
                    foo();
                    // foo */
                    foo2();
                    /* // foo2 */
                    """;
        var result = original.replaceAll("//.*|(\"(?:\\\\[^\"]|\\\\\"|.)*?\")|(?s)/\\*.*?\\*/", "$1");
        System.out.println(result);
    }
}
