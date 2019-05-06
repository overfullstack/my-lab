/*
 * Copyright (c) 2017 - Present, Gopal S Akshintala This source code is licensed under the Creative Commons Attribution-ShareAlike 4.0 International License.
 * 	http://creativecommons.org/licenses/by-sa/4.0/
 */

package http.jersey1x;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;

import javax.ws.rs.core.MediaType;

/**
 * Created by Gopala Akshintala on 12/09/17.
 */
public class Client {
    public static void main(String[] args) {
        final var URL = "https://slc11cfp.us.oracle.com/eloqua/bluekai/client/activity/trigger?oauth_consumer_key=dyp0jJRSkoBLkGI7c0p4T3og0dJgOfRwV5i3Ogp%2FjR0%3D&siteId=10790440";
        try {
            final ClientConfig clientConfig = new DefaultClientConfig();
            clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);

            var client = com.sun.jersey.api.client.Client.create(clientConfig);

            client.setConnectTimeout(60 * 1000);
            client.setReadTimeout(60 * 1000);

            final var response = client
                    .resource(URL)
                    .type(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .method("POST",ClientResponse.class,"{}");

            var code = response.getStatus();

        } catch (Exception e) {

            e.printStackTrace();

        }
    }
}
