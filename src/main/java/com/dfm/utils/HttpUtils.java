package com.dfm.utils;

import okhttp3.*;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.Nullable;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.Base64;
import java.util.concurrent.TimeUnit;

/**
 * @program: m3u8_project
 * @description:
 * @author: Mr.D
 * @create: 2020-12-23 15:02
 */
public class HttpUtils {
    public static void main(String[] args) throws IOException {
//        byte [] s = HttpUtils.getBytes("https://app.xiaoe-tech.com/get_video_key.php?edk=CiD22A6pSuOPbefa64MBh36rWJK2m02SLTjvtWfjMhJS6BCO08TAChiaoOvUBCokYjRhNjFiNTgtMmVhNy00OWYxLTgwZGMtZTE0NTIyODc5YWIy&fileId=5285890812979196458&keySource=VodBuildInKMS");
        byte [] s = HttpUtils.getBytes("https://app.xiaoe-tech.com/get_video_key.php?edk=CiD22A6pSuOPbefa64MBh36rWJK2m02SLTjvtWfjMhJS6BCO08TAChiaoOvUBCokYjRhNjFiNTgtMmVhNy00OWYxLTgwZGMtZTE0NTIyODc5YWIy&fileId=5285890812979196458&keySource=VodBuildInKMS");
        System.out.println(Arrays.toString(s));
        int [] b = new int[s.length];
        for (int i = 0; i < s.length; i++) {
            if(s[i]<0){
               b[i] = s[i]%0xFF;
            }else{
                b[i] = s[i];
            }
        }
        System.out.println(-45&0xFF);
        System.out.println(Arrays.toString(b));
        System.out.println(hexToStr(Arrays.toString(s).replaceAll("\\[","").replaceAll("]","")));
        System.out.println(hexToStr(Arrays.toString(b).replaceAll("\\[","").replaceAll("]","")));
        System.out.println(Base64.getEncoder().encodeToString(s));
    }
    static String hexToStr(String key) {
        return getString(key);
    }

    @Nullable
    public static String getString(String key) {
        if (StringUtils.isBlank(key)) {
            return null;
        }
        boolean flag = false;
        if (key.indexOf("0x") != -1 || key.indexOf("0X") != -1) {
            key = key.replace("0x", "").replace("0X", "");
            flag = true;
        }
        String[] bys = key.split(",");
        byte[] bytes = new byte[bys.length];
        int a = 0;
        for (int i = 0; i < bys.length; i++) {
            if (flag) {
                a = Integer.parseInt(bys[i].trim(), 16);
            } else {
                a = Integer.parseInt(bys[i].trim());
            }
            bytes[i] = (byte) a;
        }
        return bys.length == 1 ? key : new String(bytes);
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
            okHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(15, TimeUnit.SECONDS)
                    .readTimeout(15,TimeUnit.SECONDS)
                    .writeTimeout(15,TimeUnit.SECONDS)
                    .followSslRedirects(true)
                    .sslSocketFactory(getSSLSocketFactory(), getX509TrustManager()).build();

            Request request = new Request.Builder()
                    .addHeader("accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9")
                    .addHeader("user-agent","Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/88.0.4324.96 Safari/537.36")
                    .url(url).build();
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
