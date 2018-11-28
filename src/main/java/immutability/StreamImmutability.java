/*
 * Copyright (c) 2018 - Present, Gopal S Akshintala 
 * This source code is licensed under the Creative Commons Attribution-ShareAlike 4.0 International License.
 * 	http://creativecommons.org/licenses/by-sa/4.0/
 */

package immutability;

import com.google.gson.Gson;

import java.util.Optional;
import java.util.stream.Stream;

public class StreamImmutability {
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

        @Override
        public String toString() {
            return String.valueOf(value);
        }
    }

    private static Gson gson = new Gson();

    public static void main(String[] args) {
        Holder mutableHolder = new Holder(1);
        Stream<Holder> stream = Stream.of(mutableHolder);
        Stream<Holder> mappedStream = stream.peek(holder -> holder.setValue(2));

        System.out.println(mutableHolder.value);
        mappedStream.forEach(System.out::print);
    }
}
