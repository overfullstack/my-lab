package labs;/*
 * Copyright (c) 2019 - Present, Gopal S Akshintala 
 * This source code is licensed under the Creative Commons Attribution-ShareAlike 4.0 International License.
 * 	http://creativecommons.org/licenses/by-sa/4.0/
 */

import common.Bean;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.Optional;

public class OptionalLab {
    
    @Test
    void optionalWithNull() {
        Assertions.assertThrows(NullPointerException.class, () -> Optional.of(null));
    }
    
    @Test
    void immutability() {
        var bean = new Bean("str1", "str1", Collections.emptyList());
        var beanFromOptional = Optional.of(bean)
                .map(b -> {
                    b.setProp1("str2");
                    return b;
                })
                .get();
        System.out.println("Optional Mutates the value inside it" + bean.getProp1());
        Assertions.assertSame(bean, beanFromOptional);
    }
}
