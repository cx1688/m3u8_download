package com.dfm.beans;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

public class ResponseInfo {
    private int code;
    private String msg;
    private InputStream inputStream;
    private byte[] bytes;

    public ResponseInfo() {
    }

    public ResponseInfo(int code, String msg, InputStream inputStream) {
        try {
            this.code = code;
            this.msg = msg;
            this.inputStream = inputStream;
            this.bytes = inputStream != null ? readBytes() : null;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private byte[] readBytes() throws IOException {
        if (inputStream != null) {
            byte[] bs = new byte[inputStream.available()];
            inputStream.read(bs);
            return bs;
        }
        return null;
    }

    public byte[] getBytes() throws IOException {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    @Override
    public String toString() {
        return "ResponseInfo{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", bytes=" + Arrays.toString(bytes) +
                '}';
    }
}
