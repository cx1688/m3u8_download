package com.dfm.utils;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * @program: m3u8_project
 * @description:
 * @author: Mr.D
 * @create: 2020-12-23 15:02
 */
public class HttpUtils {
    public static void main(String[] args) throws IOException {
        System.out.println(get("http://iqiyi.cdn9-okzy.com/20210124/21387_698f81a5/index.m3u8"));
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


    public static Response request(String url) throws IOException {
        OkHttpClient okHttpClient = null;
        try {
            okHttpClient = new OkHttpClient.Builder().sslSocketFactory(getSSLSocketFactory(), getX509TrustManager()).build();
            Request request = new Request.Builder().url(url).build();
            Call call = okHttpClient.newCall(request);
            return call.execute();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static X509TrustManager getX509TrustManager() {
        return new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
            }

            @Override
            public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }
        };
    }

    public static TrustManager[] getTrustManager() {
        return new TrustManager[]{
                getX509TrustManager()
        };
    }

    public static SSLSocketFactory getSSLSocketFactory() throws NoSuchAlgorithmException, KeyManagementException {
        final SSLContext sslContext = SSLContext.getInstance("SSL");
        sslContext.init(null, getTrustManager(), new java.security.SecureRandom());
        return sslContext.getSocketFactory();
    }


}
