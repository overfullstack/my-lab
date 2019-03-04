/*
 * Copyright (c) 2019 - Present, Gopal S Akshintala
 * This source code is licensed under the Creative Commons Attribution-ShareAlike 4.0 International License.
 * 	http://creativecommons.org/licenses/by-sa/4.0/
 */

import org.junit.jupiter.api.Test;

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
}
