/*
 * Copyright (c) 2018 - Present, Gopal S Akshintala
 * This source code is licensed under the Creative Commons Attribution-ShareAlike 4.0 International License.
 * 	http://creativecommons.org/licenses/by-sa/4.0/
 */

package map;

import java.util.HashMap;
import java.util.Map;

public class MapLab {
    public static void main(String[] args) {
        Map<Integer, MyClass> map = new HashMap<>();
        map.put(1, new MyClass(1));
        MyClass myClass = map.get(1);
        myClass.val++;
        System.out.println(map.get(1).val);
    }
}

class MyClass {
    int val;

    MyClass(int val) {
        this.val = val;
    }
}