/*
 * Copyright (c) 2018 - Present, Gopal S Akshintala
 * This source code is licensed under the Creative Commons Attribution-ShareAlike 4.0 International License.
 * 	http://creativecommons.org/licenses/by-sa/4.0/
 */

package immutability;

import com.google.gson.Gson;

import java.util.Optional;

public class OptionalImmutability {
    private static final Gson gson = new Gson();

    public static void main(String[] args) {
        var mutableHolder = new Holder(1);
        var container = Optional.of(mutableHolder);
        System.out.println("Before:" + container.get().getValue());
        mutableHolder.setValue(2);
        System.out.println("After:" + container.get().getValue());

        var mappedContainer = container.map(holder -> {
            var holderCopy = gson.fromJson(gson.toJson(holder), Holder.class);
            holderCopy.setValue(3);
            return holderCopy;
        });

        mutableHolder.setValue(4);
        System.out.println("Trying to mutate inside Optional: " + mappedContainer.get().getValue());
    }

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
}
