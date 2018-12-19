package org.fiware.client.util;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

/**
 *
 * @author Pedro
 */
public class NgsiRequest {
    
    public static final String SERVER_IP = "localhost";
    public static final String SERVER_PORT = "1026";
    public static final String SERVER_PROTOCOL = "http://";
    
    private String service;
    private String path;
    
    public NgsiRequest(String service,String path) {
    	this.service= service;
    	this.path = path;
    }

    public String sendPost(String urlString, String body) {
        try {
            HttpClient httpClient = HttpClientBuilder.create().build();
            HttpPost post = new HttpPost(SERVER_PROTOCOL+SERVER_IP+":"+SERVER_PORT+urlString);
            StringEntity postingString = new StringEntity(body);
            post.setEntity(postingString);
            post.setHeader("Content-type", "application/json");
            post.setHeader("fiware-service", service);
            post.setHeader("fiware-servicepath", path);
            HttpResponse response = httpClient.execute(post);
            if (response != null) {
                if(response.getEntity() != null) {
                	InputStream in = response.getEntity().getContent();
                	return IOUtils.toString(in, "UTF-8");
                }
                return "";
            }
            return null;
        } catch (IOException ex) {
            Logger.getLogger(NgsiRequest.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
