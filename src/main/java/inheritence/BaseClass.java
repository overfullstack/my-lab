/*
 * Copyright (c) 2017 - Present, Gopal S Akshintala This source code is licensed under the Creative Commons Attribution-ShareAlike 4.0 International License.
 * 	http://creativecommons.org/licenses/by-sa/4.0/
 */

package inheritence;

/**
 * Created by Gopala Akshintala on 08/09/17.
 */
public class BaseClass {
    void printClassName() {
        System.out.println(Thread.currentThread().getStackTrace()[1].getClassName());
    }
}
