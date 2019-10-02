/*
 * Copyright (c) 2019 - Present, Gopal S Akshintala
 * This source code is licensed under the Creative Commons Attribution-ShareAlike 4.0 International License.
 * 	http://creativecommons.org/licenses/by-sa/4.0/
 */

package common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;

import static common.BeanType.T1;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Bean {
    private String prop;
    private String prop1;
    private String prop2;
    private Bean nestedBean;
    private List<Bean> beans;
    private BeanType type;

    public Bean(String s, String a, List<Bean> emptyList) {
        this(s, a, null, null, emptyList, T1);
    }

    public Bean(String prop, String prop1) {
        this(prop, prop1, "");
    }

    public Bean(String prop, String prop1, String prop2) {
        this(prop, prop1, prop2, null, Collections.emptyList(), T1);
    }

    public Bean(String prop, String prop1, String prop2, BeanType type) {
        this(prop, prop1, prop2, null, Collections.emptyList(), type);
    }

}
