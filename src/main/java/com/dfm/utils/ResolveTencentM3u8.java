//package com.dfm.utils;
//
//import com.dfm.beans.M3u8Info;
//import java.nio.charset.StandardCharsets;
//import java.util.Arrays;
//
///**
// * @program: m3u8_project
// * @description:
// * @author: Mr.D
// * @create: 2020-12-28 12:36
// */
//public class ResolveTencentM3u8 implements Resolve{
//    public static void main(String[] args) throws Exception {
//        String url ="https://1258712167.vod2.myqcloud.com/fb8e6c92vodtranscq1258712167/e56a5ca45285890803620207752/drm/voddrm.token.dWluPTM5NDk0MjY5ODt2b2RfdHlwZT0wO2NpZD0yOTEwMjE7dGVybV9pZD0xMDAzNDQ3NTU7cGxza2V5PTAwMDQwMDAwZDA5NzA2ODQwMjVlNDY3YzI2MDExNzg1MzBjNDNjYmIyN2VlNGI4NGE1ZWRjY2YwNzE4NjQ2NzMyMmNlOWY3YzVjNDkxZjJmODBlY2E5YmU7cHNrZXk9NjlRc24qWkZDUlN2ZC1VUTdZVE14WkRmcWZ3dkhBeTVQcFFQQ25JRGpFd18=.master_playlist.m3u8?t=6010ea28&exper=0&us=1628611776638912478&sign=078cc20ed7c7e9de3df670389fa7ff2e";
//        String key = HttpUtils.get("https://ke.qq.com/cgi-bin/qcloud/get_dk?edk=CiA7aUZQhLvQrlBN%2BlL3%2FdEYU4GYPADremxOfp0iPZRgXRCO08TAChiaoOvUBCokOTMyNDg4YmItOWZjYS00MzFiLWJiYjItNjFmMDhjYjNlYmM3&fileId=5285890803620207752&keySource=VodBuildInKMS&token=dWluPTM5NDk0MjY5ODt2b2RfdHlwZT0wO2NpZD0yOTEwMjE7dGVybV9pZD0xMDAzNDQ3NTU7cGxza2V5PTAwMDQwMDAwZDA5NzA2ODQwMjVlNDY3YzI2MDExNzg1MzBjNDNjYmIyN2VlNGI4NGE1ZWRjY2YwNzE4NjQ2NzMyMmNlOWY3YzVjNDkxZjJmODBlY2E5YmU7cHNrZXk9NjlRc24qWkZDUlN2ZC1VUTdZVE14WkRmcWZ3dkhBeTVQcFFQQ25JRGpFd18%3D");
//        byte[] bytes = HttpUtils.getBytes(url);
//        System.out.println(Arrays.toString(key.getBytes(StandardCharsets.UTF_8)));
////        String  iv ="00000000000000000000000000000000".substring(0, 16);
////        Security.addProvider(new BouncyCastleProvider());
////        AlgorithmParameters aes = AlgorithmParameters.getInstance("AES");
////        aes.init(new IvParameterSpec(iv.getBytes()));
////
////        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
////        SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), "AES");
////
////        cipher.init(Cipher.DECRYPT_MODE, keySpec,aes);
////        byte[] bytes1 = cipher.doFinal(bytes);
//
////        Files.write(new File("D:\\1.ts").toPath(), bytes1, StandardOpenOption.WRITE);
//    }
//
//    @Override
//    public M3u8Info resolve(String m3u8Url) {
//        try {
//            byte[] content = HttpUtils.getBytes(m3u8Url);
//            System.out.println(new String(content));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//}
