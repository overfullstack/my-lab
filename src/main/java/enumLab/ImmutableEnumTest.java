/*
 * Copyright (c) 2019 - Present, Gopal S Akshintala 
 * This source code is licensed under the Creative Commons Attribution-ShareAlike 4.0 International License.
 * 	http://creativecommons.org/licenses/by-sa/4.0/
 */

package enumLab;

public class ImmutableEnumTest {
    public static void main(String[] args) {
        //testWithValue();
        //testWithEnum();
    }
    
    private static void testWithValue() {
        MyEnum myEnum = MyEnum.ENUM1;
        MyEnum myEnum1 = myEnum.withValue("newValue");

        System.out.println(myEnum.getValue());
        System.out.println(myEnum1.getValue()); // This changes other enum as well
    }

    private static void testWithEnum() {
        MyEnum myEnum = MyEnum.ENUM1;
        MyEnum myEnum1 = MyEnum.ENUM1.withValue("newValue");

        System.out.println(myEnum.getValue());
        System.out.println(myEnum1.getValue()); // This changes other enum as well
        
        myEnum.setValue("otherValue");
        System.out.println(myEnum.getValue());
        System.out.println(myEnum1.getValue());
    }
}
