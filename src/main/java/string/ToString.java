/*
 * Copyright (c) 2018 - Present, Gopal S Akshintala This source code is licensed under the Creative Commons Attribution-ShareAlike 4.0 International License.
 * 	http://creativecommons.org/licenses/by-sa/4.0/
 */

package string;

import java.io.PrintWriter;
import java.io.StringWriter;

public class ToString {
    public static void main(String[] args) {
        StringWriter out = new StringWriter();
        new Throwable().printStackTrace(new PrintWriter(out));
        System.out.println(out.toString());
    }
}
