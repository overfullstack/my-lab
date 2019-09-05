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

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Bean {
    private String prop;
    private String prop1;
    private String prop2;
    private Bean nestedBean;
    private List<Bean> beans;

    public Bean(String s, String a, List<Bean> emptyList) {
        this(s, a, null, null, emptyList);
    }

    public Bean(String prop, String prop1) {
        this(prop, prop1, "");
    }

    public Bean(String prop, String prop1, String prop2) {
        this(prop, prop1, prop2, null, Collections.emptyList());
    }
    
}
