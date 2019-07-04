package labs;/*
 * Copyright (c) 2019 - Present, Gopal S Akshintala 
 * This source code is licensed under the Creative Commons Attribution-ShareAlike 4.0 International License.
 * 	http://creativecommons.org/licenses/by-sa/4.0/
 */

import enumLab.MyEnum;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

class EnumLab {
    
    @Test
    void loopThroughValues() {
        Arrays.stream(MyEnum.values()).forEach(System.out::println);
    }

    @Test
    void enumToString() {
        System.out.println(MyEnum.ENUM1.toString());
        Assertions.assertTrue(MyEnum.ENUM1.toString().equalsIgnoreCase("Enum1"));
    }
}