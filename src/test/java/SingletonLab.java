/*
 * Copyright (c) 2019 - Present, Gopal S Akshintala 
 * This source code is licensed under the Creative Commons Attribution-ShareAlike 4.0 International License.
 * 	http://creativecommons.org/licenses/by-sa/4.0/
 */

import common.SingletonBean;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SingletonLab {
    
    @Test
    void checkSingletonReferenceEquality() {
        var singletonBean1 = SingletonBean.INSTANCE;
        var singletonBean2 = SingletonBean.INSTANCE;
        Assertions.assertEquals(singletonBean1, singletonBean2);
    }
}
