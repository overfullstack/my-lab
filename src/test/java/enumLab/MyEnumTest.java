/*
 * Copyright (c) 2019 - Present, Gopal S Akshintala 
 * This source code is licensed under the Creative Commons Attribution-ShareAlike 4.0 International License.
 * 	http://creativecommons.org/licenses/by-sa/4.0/
 */

package enumLab;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

class MyEnumTest {
    
    @Test
    void loopThroughValues() {
        Arrays.stream(MyEnum.values()).forEach(System.out::println);
    }

}