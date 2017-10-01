/*
 * Copyright (c) 2017 - Present, Gopal S Akshintala This source code is licensed under the Creative Commons Attribution-ShareAlike 4.0 International License.
 * 	http://creativecommons.org/licenses/by-sa/4.0/
 */

package inheritence;

/**
 * Created by Gopala Akshintala on 18/09/17.
 */
public class MethodCalling {
    public static void main(String[] args) {
        test1(null);
    }

    public static void test1(String str) {
        System.out.println(str);
    }

    public static void test1(Object obj) {
        System.out.println(obj);
    }
}
