package labs;

import org.junit.jupiter.api.Test;

import java.util.TreeSet;

/**
 * gakshintala created on 11/24/19.
 */
public class SetLab {


    @Test
    void enumInTreeSet() {
        final var names = new TreeSet<Name>();
        names.add(Name.GOPAL);
        names.add(Name.AKSHINTALA);
        names.add(Name.SARMA);
        names.add(Name.GOPAL);
        names.forEach(System.out::println);
    }

    enum Name {
        AKSHINTALA, GOPAL, SARMA
    }
}
