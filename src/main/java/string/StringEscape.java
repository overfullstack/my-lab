/*
 * Copyright (c) 2018 - Present, Gopal S Akshintala
 * This source code is licensed under the Creative Commons Attribution-ShareAlike 4.0 International License.
 * 	http://creativecommons.org/licenses/by-sa/4.0/
 */

package string;

import org.apache.commons.text.StringEscapeUtils;

public class StringEscape {
    public static void main(String args[]) {

        var testStr = "< > \" &";

        System.out.println("Original : " + testStr + "\n");

        //System.out.println("escapeHtml4 : " + StringEscapeUtils.escapeHtml4(testStr));
        System.out.println("escapeCsv : " + StringEscapeUtils.escapeCsv(testStr));

    }
}
