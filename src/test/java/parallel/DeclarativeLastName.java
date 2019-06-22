/*
 * Copyright (c) 2019 - Present, Gopal S Akshintala
 * This source code is licensed under the Creative Commons Attribution-ShareAlike 4.0 International License.
 * 	http://creativecommons.org/licenses/by-sa/4.0/
 */

package parallel;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public class DeclarativeLastName {

    private static final List<String> TEAM = Arrays.asList(
            "Venky Viswanathan",
            "Satya Jain",
            "Anshul Rawat",
            "Manasa Ranjan Tripathi", // More than 2 word name
            "Sivaram Yadala",
            "Akshintala", // Single word name
            "Ravi Shankar",
            "Manoj Kumar Pendhyala",
            "", // Empty word
            "Manikanta Yakkala",
            "Muneer Ahmed",
            "Prateek Sharma",
            "Sowmya Tammana",
            "Srinivas Vemula",
            "Himanshu Kapoor",
            null
    );

    // First-Class Function
    private static final Function<String, String> lastName =
            fullName -> Arrays.stream(fullName.split(" "))
                    .reduce((other, last) -> last)
                    .orElse("");

    @Test
    void testLastNameFinderWithStream() {
        System.out.println(
                TEAM.stream()
                        .map(Optional::ofNullable)
                        .flatMap(Optional::stream) // I know this is an overkill for just doing a null-check, but this is just to demo. 
                        .filter(fullName -> !fullName.isEmpty())
                        .map(lastName)
                        .collect(Collectors.joining(" 🤝 "))
        );
    }

    @Test
    void testLastNameFinderWithParallelStream() {
        final var result = TEAM.parallelStream()
                .filter(fullName -> !fullName.isEmpty())
                .map(lastName)
                .collect(Collectors.joining(" & "));
        System.out.println(result);
    }
}