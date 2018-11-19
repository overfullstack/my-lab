/*
 * Copyright (c) 2018 - Present, Gopal S Akshintala 
 * This source code is licensed under the Creative Commons Attribution-ShareAlike 4.0 International License.
 * 	http://creativecommons.org/licenses/by-sa/4.0/
 */

package enumLab;

public enum MyEnum {
    ENUM1("enum1"),
    ENUM2("enum2");
    
    MyEnum(String value) {
        this.value = value;
    }
    private final String value;

    public String getValue() {
        return value;
    }
}
