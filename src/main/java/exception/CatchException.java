/*
 * Copyright (c) 2018 - Present, Gopal S Akshintala 
 * This source code is licensed under the Creative Commons Attribution-ShareAlike 4.0 International License.
 * 	http://creativecommons.org/licenses/by-sa/4.0/
 */

package exception;

public class CatchException {
    public static void main(String[] args) {
        try {
            throw new RuntimeException("run time");
            //String abc = null;
            //System.out.println(abc.length());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
