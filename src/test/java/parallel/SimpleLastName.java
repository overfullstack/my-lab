/*
 * Copyright (c) 2019 - Present, Gopal S Akshintala
 * This source code is licensed under the Creative Commons Attribution-ShareAlike 4.0 International License.
 * 	http://creativecommons.org/licenses/by-sa/4.0/
 */

package parallel;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

public class SimpleLastName {

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

    @Test
    void testLastNameFinderSimpleLoop() {
        System.out.println(concatLastNames(TEAM));
    }

    private static String concatLastNames(List<String> team) {
        var output = new StringBuilder();
        var isFirst = true;
        for (var teamMemberName : team) { // External Iteration
            if (teamMemberName != null && !teamMemberName.isEmpty()) {
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

}