package labs;/*
 * Copyright (c) 2019 - Present, Gopal S Akshintala
 * This source code is licensed under the Creative Commons Attribution-ShareAlike 4.0 International License.
 * 	http://creativecommons.org/licenses/by-sa/4.0/
 */

import org.junit.jupiter.api.Test;

import java.util.Random;
import java.util.function.Function;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

class Concurrency {
    @Test
    void frequencyCount() {
        System.out.println(
                new Random().ints(100, 0, 10)
                        .boxed()
                        .parallel()
                        .collect(groupingBy(Function.identity(), counting()))
        );

    }
}
