/*
 * Copyright (c) 2018 - Present, Gopal S Akshintala 
 * This source code is licensed under the Creative Commons Attribution-ShareAlike 4.0 International License.
 * 	http://creativecommons.org/licenses/by-sa/4.0/
 */

package lambda;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;
import java.util.stream.IntStream;

public class StreamLab {
    public static void main(String[] args) {
        //generate();
        findFirstTest();
    }

    private static void generate() {
        Collections.nCopies(8, 1).stream().forEach(System.out::print);
        System.out.println();
        IntStream.generate(() -> 1).limit(8).forEach(System.out::print);
    }

    private static void findFirstTest() {
        Integer[] arr = {1,2,3,6};
        Arrays.stream(arr)
                .filter(i -> i > 5)
                .map(Optional::of)
        ;
    }
    
}
