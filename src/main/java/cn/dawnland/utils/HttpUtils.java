package cn.dawnland.utils;

import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * @author Created by cap_sub@dawnland.cn
 */
public class HttpUtils {

    private HttpUtils() { }

    public final static HttpUtils INSTANCE = new HttpUtils();

    private final HttpClient httpClient = HttpClientBuilder.create().build();

    public String get(String url) {
        HttpUriRequest httpUriRequest = new HttpGet(url);
        try {
            HttpResponse response = httpClient.execute(httpUriRequest);
            return EntityUtils.toString(response.getEntity());
        } catch (IOException e) {
            return null;
        }
    }

    public byte[] getFile(String url){
        HttpUriRequest httpUriRequest = new HttpGet(url);
        try {
            HttpResponse response = httpClient.execute(httpUriRequest);
            return EntityUtils.toByteArray(response.getEntity());
        } catch (IOException e) {
            return null;
        }
    }

}
