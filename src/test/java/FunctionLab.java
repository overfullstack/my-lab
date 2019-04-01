/*
 * Copyright (c) 2019 - Present, Gopal S Akshintala
 * This source code is licensed under the Creative Commons Attribution-ShareAlike 4.0 International License.
 * 	http://creativecommons.org/licenses/by-sa/4.0/
 */

import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.function.Function;

class FunctionLab {

    @Test
    void functionChain() {
        Function<String, Integer> f1 = Integer::parseInt;
        Function<Integer, String> f2 = String::valueOf;
        Function<Integer, Integer> fComposed = f2.andThen(f1);
    }

    @Test
    void curriedFunction() {
        Function<String, Function<String, String>> appendMe = x -> y -> x + y;
        System.out.println(appendMe
                .apply("x")
                .apply("y"));
    }
    
    @Test
    void multiInputFunction() {
        System.out.println(
                wrapOptional("first")
                .flatMap(first -> wrapOptional("second")
                        .flatMap(second -> concat(first, second))
                ).get()
        );
    }
    
    @Test
    void multiInputWithCurrying() {
        System.out.println(
                wrapOptional("first")
                .map(this::curryConcat)
                .flatMap(concat -> concat.apply("second"))
                .get()
        );
    }
    
    Optional<String> wrapOptional(String input) {
        return Optional.of(input);
    }
    
    Optional<String> concat(String str1, String str2) {
        return Optional.of(str1 + str2);
    }
    
    Function<String, Optional<String>> curryConcat(String str1) {
        return str2 -> Optional.of(str1 + str2);
    }
}
