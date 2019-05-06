/*
 * Copyright (c) 2018 - Present, Gopal S Akshintala 
 * This source code is licensed under the Creative Commons Attribution-ShareAlike 4.0 International License.
 * 	http://creativecommons.org/licenses/by-sa/4.0/
 */

package immutability;

import com.google.gson.Gson;

public class CloningWithGson {
    private static class Holder {
        private int value;

        Holder(int value) {
            this.value = value;
        }

        int getValue() {
            return value;
        }

        void setValue(int value) {
            this.value = value;
        }
    }

    private static final Gson gson = new Gson();

    public static void main(String[] args) {
        var holder = new Holder(1);
        var holderCopy1 = gson.fromJson(gson.toJson(holder), Holder.class);
        holderCopy1.setValue(2);
        var holderCopy2 = gson.fromJson(gson.toJson(holder), Holder.class);
        holderCopy2.setValue(3);
        System.out.println(holder.getValue());
        System.out.println(holderCopy1.getValue());
        System.out.println(holderCopy2.getValue());
    }
}
