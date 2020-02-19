/*
 * Copyright (c) 2018 - Present, Gopal S Akshintala
 * This source code is licensed under the Creative Commons Attribution-ShareAlike 4.0 International License.
 * 	http://creativecommons.org/licenses/by-sa/4.0/
 */

package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;

public class ResourceFileReader {
    private static final String RESOURCE_PATH = Paths.get(".").toAbsolutePath() + "/./src/arrow.higherkinds.main/resources/";
    private BufferedReader reader;

    public ResourceFileReader(String filePath) {
        try {
            this.reader = new BufferedReader(new FileReader(new File(RESOURCE_PATH + filePath)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public String readLine() {
        try {
            return this.reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
