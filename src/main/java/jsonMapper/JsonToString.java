/*
 * Copyright (c) 2018 - Present, Gopal S Akshintala 
 * This source code is licensed under the Creative Commons Attribution-ShareAlike 4.0 International License.
 * 	http://creativecommons.org/licenses/by-sa/4.0/
 */

package jsonMapper;

import org.apache.http.HttpEntity;
import org.apache.http.entity.StringEntity;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;

import static jsonMapper.Utils.stringToObject;

public class JsonToString {
    public static void main(String[] args) throws IOException {
        Bean bean = stringToObject();
        System.out.println(bean.getProp());
        bean.setProp(null);
        ObjectMapper mapper = new ObjectMapper();
        System.out.println(mapper.writeValueAsString(bean));
        HttpEntity httpEntity = new StringEntity(mapper.writeValueAsString(bean));
        System.out.println(httpEntity.toString());
    }
}
