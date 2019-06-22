/*
 * Copyright (c) 2019 - Present, Gopal S Akshintala
 * This source code is licensed under the Creative Commons Attribution-ShareAlike 4.0 International License.
 * 	http://creativecommons.org/licenses/by-sa/4.0/
 */

package parallel;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ForkJoinLastName {

    private static final List<String> TEAM = Arrays.asList(
            "Venky Viswanathan",
            "Satya Jain",
            "Sivaram Yadala",
            "Manasa Ranjan Tripathi", // More than 2 word name
            "Ravi Shankar",
            "Anshul Rawat",
            "Akshintala", // Single word name
            "Manikanta Yakkala",
            "Muneer Ahmed",
            "Prateek Sharma",
            "Sowmya Tammana",
            "Srinivas Vemula",
            "Himanshu Kapoor",
            "", // Empty word
            null
    );
    
    private static final int AVAILABLE_CORES = Runtime.getRuntime().availableProcessors();

    @Test
    void testLastNameFinderSimpleLoop() {
        System.out.println(concatLastNames(TEAM));
    }

    private static String concatLastNames(List<String> team) {
        var output = new StringBuilder();
        var isFirst = true;
        for (var teamMemberName : team) { // External Iteration
            if (!teamMemberName.isEmpty()) {
                if (!isFirst) {
                    output.append(" 🤝 ");
                }
                var lastName = extractLastName(teamMemberName);
                output.append(lastName);
                isFirst = false;
            }
        }
        return output.toString();
    }

    private static String extractLastName(final String fullName) {
        final var lastSpaceIndex = fullName.lastIndexOf(" ");
        final String lastName;
        if (lastSpaceIndex < 0) {
            lastName = fullName;
        } else {
            lastName = fullName.substring(lastSpaceIndex + 1);
        }
        return lastName;
    }

    @Test
    void parallelWithForkJoinPool() {
        var forkJoinPool = new ForkJoinPool(AVAILABLE_CORES);
        var results = forkJoinPool.invoke(new MyRecursiveTask(TEAM));
        System.out.println(concatResults(results));
    }

    private static String concatResults(List<String> results) {
        var output = new StringBuilder();
        var isFirst = true;
        for (var result : results) {
            if (!result.isEmpty()) {
                if (!isFirst) {
                    output.append(" 🤝 ");
                }
                output.append(result);
                isFirst = false;
            }
        }
        return output.toString();
    }

    static class MyRecursiveTask extends RecursiveTask<List<String>> {
        private static final int MIN_TEAM_SIZE = 2; // In real-world, DO NOT have it below 10,000 
        private final List<String> team;

        MyRecursiveTask(List<String> team) {
            this.team = team;
        }

        @Override
        protected List<String> compute() {
            if (team.size() > MIN_TEAM_SIZE) {
                var mid = team.size() / 2;
                final var myRecursiveTask1 = new MyRecursiveTask(team.subList(0, mid));
                final var myRecursiveTask2 = new MyRecursiveTask(team.subList(mid, team.size()));

                myRecursiveTask1.fork();
                myRecursiveTask2.fork();

                var results = new ArrayList<String>();
                results.addAll(myRecursiveTask1.join());
                results.addAll(myRecursiveTask2.join());
                return results;
            } else {
                return Collections.singletonList(concatLastNames(team));
            }
        }
    }

}