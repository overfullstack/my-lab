/*
 * Copyright (c) 2019 - Present, Gopal S Akshintala 
 * This source code is licensed under the Creative Commons Attribution-ShareAlike 4.0 International License.
 * 	http://creativecommons.org/licenses/by-sa/4.0/
 */

package enumLab;

public class ImmutableEnumTest {
    public static void main(String[] args) {
        MyEnum myEnum = MyEnum.ENUM1;
        MyEnum myEnum1 = myEnum.withValue("newValue");
        System.out.println(myEnum.getValue());
        System.out.println(myEnum1.getValue());
    }
}
