/*
 * Copyright (c) 2019 - Present, Gopal S Akshintala 
 * This source code is licensed under the Creative Commons Attribution-ShareAlike 4.0 International License.
 * 	http://creativecommons.org/licenses/by-sa/4.0/
 */

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import common.Bean;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class GsonMapper {
    private final Gson gson = new Gson();
    
    @Test
    void testDeepCloneListOfBeans() {
        List<Bean> beans = new ArrayList<>();
        var bean1 = new Bean("1", Collections.emptyList());
        var bean2 = new Bean("2", Collections.emptyList());
        var bean3 = new Bean("3", Collections.emptyList());
        beans.add(bean1);
        beans.add(bean2);
        beans.add(bean3);
        System.out.println(beans);

        var beanListType = new TypeToken<ArrayList<Bean>>(){}.getType();

        List<Bean> deepCopyBeans = gson.fromJson(gson.toJson(beans), beanListType);
        System.out.println(deepCopyBeans);
        
        Assertions.assertEquals(beans, deepCopyBeans);
    }
    
    @Test
    void testDeepCloneBean() {
        var bean1 = new Bean("1", Collections.emptyList());
        System.out.println(bean1);

        var deepCopyBean1 = gson.fromJson(gson.toJson(bean1), Bean.class);
        System.out.println(deepCopyBean1);
        
        Assertions.assertEquals(bean1, deepCopyBean1);
    }
}
