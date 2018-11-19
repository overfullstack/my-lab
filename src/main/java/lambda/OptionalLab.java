/*
 * Copyright (c) 2018 - Present, Gopal S Akshintala 
 * This source code is licensed under the Creative Commons Attribution-ShareAlike 4.0 International License.
 * 	http://creativecommons.org/licenses/by-sa/4.0/
 */

package lambda;

import java.util.Optional;

public class OptionalLab {
    public static void main(String[] args) {
        nullOptional();
    }
    
    public static void nullOptional() {
        Optional<Object> o = Optional.of(null);
        if(o.isPresent()) {
            System.out.println("present");
        } else {
            System.out.println("absent");
        }
    }
}
