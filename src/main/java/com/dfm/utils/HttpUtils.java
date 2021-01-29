package com.dfm.utils;

import okhttp3.*;

import java.io.IOException;
import java.net.HttpURLConnection;

/**
 * @program: m3u8_project
 * @description:
 * @author: Mr.D
 * @create: 2020-12-23 15:02
 */
public class HttpUtils {

    public static String get(String url) throws IOException {
        Response response = request(url);
        try {
            if (response.isSuccessful()) {
                return response.body().string();
            }
        } finally {
            response.close();
        }
        return null;
    }

    public static byte[] getBytes(String url) throws IOException {
        Response response = request(url);
        try {
            if (response.isSuccessful()) {
                return response.body().bytes();
            }
        } finally {
            response.close();
        }
        return null;
    }

    public static boolean isSuccess(String url) {
        Response response = null;
        try {
            response = request(url);
        } catch (IOException e) {
            return false;
        } finally {
            response.close();
        }
        return response.isSuccessful();
    }

    private static OkHttpClient okHttpClient = new OkHttpClient();

    public static Response request(String url) throws IOException {
        Request request = new Request.Builder().url(url).build();
        Call call = okHttpClient.newCall(request);
        return call.execute();
    }
}
