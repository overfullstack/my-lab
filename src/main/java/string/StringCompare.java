/*
 * Copyright (c) 2018 - Present, Gopal S Akshintala 
 * This source code is licensed under the Creative Commons Attribution-ShareAlike 4.0 International License.
 * 	http://creativecommons.org/licenses/by-sa/4.0/
 */

package string;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class StringCompare {
    public static void main(String[] args) {
        MyObject myObject1 = new MyObject();
        MyObject myObject2 = new MyObject();
        MyObject myObject3 = new MyObject();
        
        // This comparision results true only if both the references point to same object.
        System.out.println("Reference Comparision" + (myObject1 == myObject2));
        
        // Arraylist doesn't use hashcode 
        List<MyObject> myObjectList = new ArrayList<>();
        myObjectList.add(myObject1);
        myObjectList.add(myObject2);
        System.out.println(myObjectList.contains(myObject3));

        // Hashset calls hashcode while adding objects to the collection. 
        Set<MyObject> myObjectSet = new HashSet<>();
        myObjectSet.add(myObject1);
        myObjectSet.add(myObject2); // This calls hashcode, and if found, calls equal to check for duplicates
        System.out.println(myObjectSet.contains(myObject3));
    }
}

class MyObject {
    @Override
    public int hashCode() {
        System.out.println("hashcode called");
        return 1;
    }

    @Override
    public boolean equals(Object obj) {
        System.out.println("equals called");
        return super.equals(obj);
    }
}