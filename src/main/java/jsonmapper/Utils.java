/*
 * Copyright (c) 2017 - Present, Gopal S Akshintala This source code is licensed under the Creative Commons Attribution-ShareAlike 4.0 International License.
 * 	http://creativecommons.org/licenses/by-sa/4.0/
 */

package jsonmapper;

import common.Bean;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

/**
 * Created by Gopala Akshintala on 28/09/16.
 */
public class Utils {
    public static final ObjectMapper mapper = new ObjectMapper();
    private static final String RESOURCE_PATH = Paths.get(".").toAbsolutePath() + "/./src/arrow.hk.main/resources/";

    public static void main(String[] args) throws IOException, JSONException {
        /*List<Map<String, String>> emails = readJsonFromFile("email.json");
        System.out.println(emails);

        Map<String, String> parentMap = decoupled HashMap<>();
        Map<String, String> childMap = decoupled HashMap<>();
        childMap.put("email", "gopalakshintala@gmail.com");
        parentMap.put("emails", mapper.writeValueAsString(childMap));
        mapToString(parentMap);*/
        //manipulateJsonString();
        //stringToJsonArray();
        stringToObject();
    }

    /*private static void stringToObject() {
        Map<String, Object> sConf = getObjectFromFile(RESOURCE_PATH + "config.json", Map.class);
        System.out.println(sConf.get("operation"));
    }*/

    public static Bean stringToObject() {
        var bean = getObjectFromFile(RESOURCE_PATH + "nested-bean.json", Bean.class);
        return bean;
    }

    public static <T> T getObjectFromFile(String filePath, Class<T> tClass) {
        var mapper = new ObjectMapper();
        try {
            var fileContent = fileToString(filePath);
            return mapper.readValue(fileContent, tClass);
        } catch (IOException ignored) {
        }
        return null;
    }

    public static String fileToString(String filePath) {
        try {
            return Files.readString(Paths.get(filePath));
        } catch (IOException ignored) {
        }
        return "";
    }

    private static void stringToJsonArray() throws JSONException {
        Object jsonArrayObj = "[{\"source1Type\": \"1\"}, {\"source1Type\": \"2\"}]";
        var jsonArrayStr = (String) jsonArrayObj;
        var arr = new JSONArray(jsonArrayStr);
        System.out.println(arr.get(0));
    }

    public static List<Map<String, String>> readJsonFromFile(String filePath) throws JSONException {
        try {
            var fileContent = Files.readString(Paths.get(RESOURCE_PATH + filePath));
            var jsonObject = new JSONObject(fileContent);
            var jsonArray = (JSONArray) jsonObject.get("items");

            /*JSONObject emailMap = (JSONObject) jsonArray.get(0);
            System.out.println(emailMap.get("EmailAddress"));

            return Arrays.asList(mapper.readValue(fileContent, Map[].class));*/
        } catch (JSONException | IOException ignored) {
        }
        return null;
    }

    public static void mapToString(Map<String, String> map) throws IOException {
        System.out.println(mapper.writeValueAsString(map));
    }

    public static void manipulateJsonString() throws IOException, JSONException {
        var fileContent = new String(Files.readAllBytes(Paths.get(RESOURCE_PATH + "bulkimportdef")), StandardCharsets.UTF_8);
        var jsonObject = new JSONObject(fileContent);
        /*JSONArray jsonArray = (JSONArray) jsonObject.get("syncActions");
        JSONObject syncActionsMap = (JSONObject) jsonArray.get(0);
        syncActionsMap.put("destination", "{{ActionInstance}}");*/
        //jsonObject.remove("syncActions");
        System.out.println(isRequestBodyContainsContentService(jsonObject));
        System.out.println(new JSONObject(jsonObject.toString().replace("ContentInstance(", "ActionInstance(")).toString());
    }

    private static boolean isRequestBodyContainsContentService(JSONObject requestBody) throws JSONException {
        return ((String) ((JSONObject) ((JSONArray) requestBody.get("syncActions")).get(0)).get("destination")).contains("ContentInstance");
    }
}
