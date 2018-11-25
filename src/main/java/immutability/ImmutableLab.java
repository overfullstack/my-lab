/*
 * Copyright (c) 2018 - Present, Gopal S Akshintala 
 * This source code is licensed under the Creative Commons Attribution-ShareAlike 4.0 International License.
 * 	http://creativecommons.org/licenses/by-sa/4.0/
 */

package immutability;

import java.util.Objects;

public class ImmutableLab {
    private static final class MyImmutable {
        private final String mem1;
        private final String mem2;

        private MyImmutable(String mem1, String mem2) {
            this.mem1 = mem1;
            this.mem2 = mem2;
        }

        @Override
        public int hashCode() {
            return Objects.hash(mem1, mem2);
        }

        @Override
        public boolean equals(Object obj) {
            return (obj instanceof MyImmutable)
                    && ((MyImmutable) obj).mem1.equals(mem1)
                    && ((MyImmutable) obj).mem2.equals(mem2);
        }
    }

    public static void main(String[] args) {
        MyImmutable myImmutable1 = new MyImmutable("abc", "xyz");
        MyImmutable myImmutable2 = new MyImmutable("abc", "xyz");
        System.out.println(myImmutable1 == myImmutable2);
        System.out.println(myImmutable1.equals(myImmutable2));
    }
}
