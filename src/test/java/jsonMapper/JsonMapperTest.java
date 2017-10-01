/*
 * Copyright (c) 2017 - Present, Gopal S Akshintala This source code is licensed under the Creative Commons Attribution-ShareAlike 4.0 International License.
 * 	http://creativecommons.org/licenses/by-sa/4.0/
 */

package jsonMapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.nio.file.Paths;

/**
 * Created by Gopala Akshintala on 30/08/17.
 */
public class JsonMapperTest {
    private static final String RESOURCE_PATH = Paths.get(".").toAbsolutePath() + "/./src/main/resources/";
    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Test
    public void inheritanceSerialization() {

    }
}