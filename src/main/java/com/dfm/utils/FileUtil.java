package com.dfm.utils;

import java.io.*;

public class FileUtil {
    public static String readStrByFile(File file) throws FileNotFoundException {
        FileInputStream fileInputStream = new FileInputStream(file);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
        StringBuilder sb = new StringBuilder();
        bufferedReader.lines().forEach(sb::append);
        return sb.toString().replaceAll(" ", "");
    }
    public static String readStrByFile(String file) throws FileNotFoundException {
        FileInputStream fileInputStream = new FileInputStream(file);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
        StringBuilder sb = new StringBuilder();
        bufferedReader.lines().forEach(sb::append);
        return sb.toString().replaceAll(" ", "");
    }
}
