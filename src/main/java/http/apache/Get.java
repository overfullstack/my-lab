/*
 * Copyright (c) 2017 - Present, Gopal S Akshintala This source code is licensed under the Creative Commons Attribution-ShareAlike 4.0 International License.
 * 	http://creativecommons.org/licenses/by-sa/4.0/
 */

package http.apache;


import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;

/**
 * Created by Gopala Akshintala on 12/09/17.
 */
public class Get {

    public static void main(String[] args) throws IOException {
        HttpClient client = HttpClientBuilder.create().build();
        var URL = "https://slc11cfp.us.oracle.com/eloqua/bluekai/client/scheduleActivityJob/1234/2433?oauth_consumer_key=dyp0jJRSkoBLkGI7c0p4T3og0dJgOfRwV5i3Ogp%2FjR0%3D&siteId=10790440";
        var request = new HttpGet(URL);

        var USER_AGENT = "Mozilla/5.0";
        request.addHeader("User-Agent", USER_AGENT);
        var response = client.execute(request);
    }

}
