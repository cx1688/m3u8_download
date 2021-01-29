package com.dfm.utils;

import org.apache.commons.crypto.cipher.CryptoCipher;
import org.apache.commons.crypto.cipher.CryptoCipherFactory;
import org.apache.commons.crypto.utils.Utils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.Security;
import java.util.Arrays;
import java.util.Properties;

/**
 * @program: m3u8_project
 * @description:
 * @author: Mr.D
 * @create: 2020-12-24 16:06
 */
public class AESUtils {

    public static SecretKey loadSecretKey(String key) throws UnsupportedEncodingException {
        return new SecretKeySpec(key.getBytes("ASCII"), "AES");
    }
    public static byte[] decrypt(byte[] source, SecretKey key, String iv) throws Exception {
        Security.addProvider(new BouncyCastleProvider());
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");//"算法/模式/补码方式"
        //使用CBC模式，需要一个向量iv，可增加加密算法的强度
        IvParameterSpec ips = new IvParameterSpec(iv.getBytes("UTF-8"));
        cipher.init(Cipher.DECRYPT_MODE, key, ips);
        return cipher.doFinal(source);
    }

    /**
     * 初始化
     *
     * @param key
     */
    public static void init(String key) {
        try {

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 解密
     * @param input
     * @return
     * @throws Exception
     */
    public static byte[] decode(String key,byte[] input) throws Exception {
        SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "AES");
        IvParameterSpec ivParameterSpec = new IvParameterSpec(key.getBytes(StandardCharsets.UTF_8));
        Properties properties = new Properties();
        properties.setProperty(CryptoCipherFactory.CLASSES_KEY, CryptoCipherFactory.CipherProvider.JCE.getClassName());
        CryptoCipher cipher = Utils.getCipherInstance("AES/CBC/PKCS5Padding", properties);
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);
        return getBytes(input, cipher);
    }


    /**
     *
     * @param input
     * @param cipher
     * @return
     * @throws ShortBufferException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     */
    private static byte[] getBytes(byte[] input, CryptoCipher cipher) throws ShortBufferException, IllegalBlockSizeException, BadPaddingException {
        //初始化输出流的大小
        byte[] output = new byte[input.length];
        //更新数据
        int updateBytes = cipher.update(input, 0, input.length, output, 0);
        //最后解密
        int finalBytes = cipher.doFinal(input, 0, 0, output, updateBytes);
        //返回解密或加密的字节数组，这样做返回的数据才是正确的，
        //如果直接返回output，数据的大小是你定义的那个new byte[input.length << 2];
        //这里为什么没有给output的赋值操作呢，因为output本身就是数组，是对象，
        //对象放在哪，放在堆内存，既然是放在内存中，那么我在另一个对象的方法中给对其赋值，
        //即使没有最后返回给我，我也是能取值。因为对象是我传过去的。当然只要知道这个内存的地址，通过技术手段也可以读取到它的数据
        return Arrays.copyOf(output, updateBytes + finalBytes);
    }
}
