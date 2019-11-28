/*
 * Copyright (c) 2017 - Present, Gopal S Akshintala This source code is licensed under the Creative Commons Attribution-ShareAlike 4.0 International License.
 * 	http://creativecommons.org/licenses/by-sa/4.0/
 */

package jsonmapper;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Created by Gopala Akshintala on 30/08/17.
 */
public abstract class SuperBean {
    String superProp;

    @JsonCreator
    public SuperBean(@JsonProperty("superProp") String superProp) {
        this.superProp = superProp + "from JsonCreator";
    }

    public String getSuperProp() {
        return superProp;
    }

    public void setSuperProp(String superProp) {
        this.superProp = superProp;
    }
}
