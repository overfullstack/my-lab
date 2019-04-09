/*
 * Copyright (c) 2019 - Present, Gopal S Akshintala
 * This source code is licensed under the Creative Commons Attribution-ShareAlike 4.0 International License.
 * 	http://creativecommons.org/licenses/by-sa/4.0/
 */

import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;

public class CompletableFutureLab {

    @Test
    void isNotLazy() {
        CompletableFuture
                .supplyAsync(() -> {
                    System.out.println("Hello - " + Thread.currentThread().getName()); // Runs on thread other than main.
                    return "Hello";
                }).thenCompose(s ->
                CompletableFuture
                        .supplyAsync(() -> {
                            System.out.println("World - " + Thread.currentThread().getName());
                            return s + " World";
                        }));

        //Assertions.assertEquals("Hello World", completableFuture.get());
    }
}
