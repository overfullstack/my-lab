/*
 * Copyright (c) 2018 - Present, Gopal S Akshintala
 * This source code is licensed under the Creative Commons Attribution-ShareAlike 4.0 International License.
 * 	http://creativecommons.org/licenses/by-sa/4.0/
 */

package string;

import utils.ResourceFileReader;

public class StringReplace {
    public static void main(String[] args) {
        var abc = "\"abc\"";
        System.out.println(abc.replace("\"", "\\\""));
        //System.out.println(StringEscapeUtils.escapeJava(abc));
        var fileLine = new ResourceFileReader("string.txt").readLine();
        System.out.println(fileLine);
        System.out.println(fileLine.replace("\"", "\\\""));
    }
}
