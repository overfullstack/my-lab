/*
 * Copyright (c) 2017 - Present, Gopal S Akshintala This source code is licensed under the Creative Commons Attribution-ShareAlike 4.0 International License.
 * 	http://creativecommons.org/licenses/by-sa/4.0/
 */

package jsonMapper;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

/**
 * Created by Gopala Akshintala on 28/09/16.
 */
public class Utils {
    private static final String RESOURCE_PATH = Paths.get(".").toAbsolutePath() + "/./src/main/resources/";
    public static final ObjectMapper mapper = new ObjectMapper();
    public static void main(String[] args) throws IOException, JSONException {
        /*List<Map<String, String>> emails = readJsonFromFile("email.json");
        System.out.println(emails);

        Map<String, String> parentMap = new HashMap<>();
        Map<String, String> childMap = new HashMap<>();
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
        Bean bean = getObjectFromFile(RESOURCE_PATH + "bean.json", Bean.class);
        return bean;
    }

    public static <T> T getObjectFromFile(String filePath, Class<T> tClass) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            String fileContent = fileToString(filePath);
            return mapper.readValue(fileContent, tClass);
        } catch (IOException ignored) {
        }
        return null;
    }

    public static String fileToString(String filePath) {
        try {
            return new String(Files.readAllBytes(Paths.get(filePath)), "UTF-8");
        } catch (IOException ignored) {
        }
        return null;
    }

    private static void stringToJsonArray() throws JSONException {
        Object jsonArrayObj = "[{\"source1Type\": \"1\"}, {\"source1Type\": \"2\"}]";
        String jsonArrayStr = (String) jsonArrayObj;
        JSONArray arr = new JSONArray(jsonArrayStr);
        System.out.println(arr.get(0));
    }

    public static List<Map<String, String>> readJsonFromFile(String filePath) throws JSONException {
        try {
            String fileContent = new String(Files.readAllBytes(Paths.get(RESOURCE_PATH + filePath)), "UTF-8");
            JSONObject jsonObject = new JSONObject(fileContent);
            JSONArray jsonArray = (JSONArray) jsonObject.get("items");

            /*JSONObject emailMap = (JSONObject) jsonArray.get(0);
            System.out.println(emailMap.get("EmailAddress"));

            return Arrays.asList(mapper.readValue(fileContent, Map[].class));*/
        } catch (JSONException|IOException ignored) {
        }
        return null;
    }

    public static void mapToString(Map<String, String> map) throws IOException {
        System.out.println(mapper.writeValueAsString(map));
    }

    public static void manipulateJsonString() throws IOException, JSONException {
        String fileContent = new String(Files.readAllBytes(Paths.get(RESOURCE_PATH + "bulkimportdef")), "UTF-8");
        JSONObject jsonObject = new JSONObject(fileContent);
        /*JSONArray jsonArray = (JSONArray) jsonObject.get("syncActions");
        JSONObject syncActionsMap = (JSONObject) jsonArray.get(0);
        syncActionsMap.put("destination", "{{ActionInstance}}");*/
        //jsonObject.remove("syncActions");
        System.out.println(isRequestBodyContainsContentService(jsonObject));
        System.out.println(new JSONObject(jsonObject.toString().replace("ContentInstance(", "ActionInstance(")).toString());
    }

    private static boolean isRequestBodyContainsContentService(JSONObject requestBody) throws JSONException {
        return ((String)((JSONObject)((JSONArray)requestBody.get("syncActions")).get(0)).get("destination")).contains("ContentInstance");
    }
}

