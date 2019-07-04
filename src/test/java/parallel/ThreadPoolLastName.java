/*
 * Copyright (c) 2019 - Present, Gopal S Akshintala
 * This source code is licensed under the Creative Commons Attribution-ShareAlike 4.0 International License.
 * 	http://creativecommons.org/licenses/by-sa/4.0/
 */

package parallel;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class ThreadPoolLastName {

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

    private String parallelWithThreadPool(List<String> team) {
        var exec = Executors.newFixedThreadPool(AVAILABLE_CORES - 1);
        var futureList = new ArrayList<Future<String>>();
        var segmentLen = team.size() / AVAILABLE_CORES;
        if (segmentLen == 0) {
            segmentLen = team.size();
        }
        var offset = 0;
        while (offset < team.size()) {
            final var from = offset;
            final var to = offset + segmentLen;
            futureList.add(exec.submit(new Callable<>() {
                @Override
                public String call() {
                    return concatLastNames(team.subList(from, to));
                }
            }));
            offset += segmentLen;
        }
        var results = new ArrayList<String>();
        for (var future : futureList) {
            try {
                results.add(future.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
        if (offset < team.size()) {
            results.add(concatLastNames(team.subList(team.size() - segmentLen, team.size())));
        }

        exec.shutdown();
        try {
            exec.awaitTermination(10, TimeUnit.SECONDS);
        } catch (InterruptedException ignore) {
        }
        return concatResults(results);
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

    @Test
    void testLastNameFinderThreadPool() {
        System.out.println(parallelWithThreadPool(TEAM));
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

}