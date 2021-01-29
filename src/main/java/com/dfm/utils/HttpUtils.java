package com.dfm.utils;

import com.dfm.beans.ResponseInfo;
import okhttp3.*;
import okhttp3.Request.Builder;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * @program: m3u8_project
 * @description:
 * @author: Mr.D
 * @create: 2020-12-23 15:02
 */
public class HttpUtils {
    public static void main(String[] args) throws IOException {
       byte[] bytes = getBytes("https://vd1381747414.grazy.cn/ts_d8c0d85fe2adff1f3cae7453c1dba0ee000006?e=1611659685&token=jKas2qY3GBfgRW5tXpDq026ezFmT3DMkjpMLBYzF:4WYUzXu0ynXLUIvp9e6vupQJQ48");
        System.out.println(bytes.length);
    }

    private static HttpURLConnection httpURLConnection;



    public static ResponseInfo httpGetStr(String url){
        URL u = null;
        try {
            u = new URL(url);
            httpURLConnection = (HttpURLConnection) u.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.addRequestProperty("User-Agent","Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/88.0.4324.96 Safari/537.36");
            httpURLConnection.setConnectTimeout(15000);
            httpURLConnection.setReadTimeout(15000);
            httpURLConnection.connect();
            return new ResponseInfo(httpURLConnection.getResponseCode(),httpURLConnection.getResponseMessage(),httpURLConnection.getInputStream());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            httpURLConnection.disconnect();
        }
        return null;
    }








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
