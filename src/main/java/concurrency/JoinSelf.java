/*
 * Copyright (c) 2016
 * This source code is licensed under the Creative Commons Attribution-ShareAlike 4.0 International License.
 */

package Lab;

/**
 * Created by gakshintala on 6/17/16.
 */
public class JoinSelf extends Thread {
    public static void main(String[] args) {
        JoinSelf joinSelf = new JoinSelf();
        joinSelf.start();
    }

    @Override
    public void run() {
        System.out.println("Before Join");
        try {
            join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("After Join");
    }
}
