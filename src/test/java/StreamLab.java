/*
 * Copyright (c) 2019 - Present, Gopal S Akshintala 
 * This source code is licensed under the Creative Commons Attribution-ShareAlike 4.0 International License.
 * 	http://creativecommons.org/licenses/by-sa/4.0/
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;
import java.util.stream.IntStream;
import org.junit.jupiter.api.Test;

class StreamLab {

    @Test
    void generate() {
        Collections.nCopies(8, 1).stream().forEach(System.out::print);
        System.out.println();
        IntStream.generate(() -> 1).limit(8).forEach(System.out::print);
    }

    @Test
    void findFirstTest() {
        Integer[] arr = {1,2,3,6};
        Arrays.stream(arr)
                .filter(i -> i > 5)
                .map(Optional::of)
        ;
    }
    
    @Test
    void findFirstForEmpty() {
        System.out.println(new ArrayList<>().stream().findFirst());
    }
    
}
