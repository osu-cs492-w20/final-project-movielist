package com.example.movielist.utility;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class NetworkUtils {
    private static final OkHttpClient mHTTPclient = new OkHttpClient();

    public static String doHttpGet(String url) throws IOException {
        Request request = new Request.Builder().url(url).build();
        Response response = mHTTPclient.newCall(request).execute();
        try{
            return response.body().string();
        } finally {
            response.close();
        }
    }
}
