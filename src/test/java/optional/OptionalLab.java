/*
 * Copyright (c) 2019 - Present, Gopal S Akshintala 
 * This source code is licensed under the Creative Commons Attribution-ShareAlike 4.0 International License.
 * 	http://creativecommons.org/licenses/by-sa/4.0/
 */

package optional;

import common.Bean;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Optional;

public class OptionalLab {
    
    @Test
    void optionalWithNull() {
        Assertions.assertThrows(NullPointerException.class, () -> Optional.of(null));
    }
    
    @Test
    void immutability() {
        var bean = new Bean("str1");
        var beanFromOptional = Optional.of(bean)
                .map(b -> {
                    b.setProp("str2");
                    return b;
                })
                .get();
        System.out.println("Optional Mutates the value inside it" + bean.getProp());
        Assertions.assertSame(bean, beanFromOptional);
    }
}
