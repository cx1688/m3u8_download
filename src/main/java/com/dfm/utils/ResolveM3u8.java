package com.dfm.utils;

import com.dfm.beans.M3u8Info;
import com.dfm.beans.SegmentFileInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import java.util.Base64;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @program: m3u8_project
 * @description:
 * @author: Mr.D
 * @create: 2020-12-23 15:01
 */
@Slf4j
public class ResolveM3u8 implements Resolve {
    private static final ResolveM3u8 INSTANCE = new ResolveM3u8();

    private ResolveM3u8() {
    }

    public static void main(String[] args) {
//        M3u8Info m3u8Info = getINSTANCE().resolveByCommon("https://videozm.whqhyg.com:8091/20210302/QBmmzlTY/index.m3u8");
//        M3u8Info m3u8Info = getINSTANCE().resolveByCommon("https://1252524126.vod2.myqcloud.com/9764a7a5vodtransgzp1252524126/149439bf5285890812979196458/drm/v.f100230.m3u8");
        M3u8Info m3u8Info = getINSTANCE().resolveByCommon("https://1258712167.vod2.myqcloud.com/fb8e6c92vodtranscq1258712167/b0e36c075285890815726052351/drm/voddrm.token.dWluPTM5NDk0MjY5ODt2b2RfdHlwZT0wO2NpZD0yMzE1MTY7dGVybV9pZD0xMDI3NTQwMzU7cGxza2V5PTAwMDQwMDAwYmEwYzc2ZTMyOTFjYzE5Yzg3ZDg5NjBlNjI3MDJiN2EzNmJlMTc2ZDljMDAwNWY5NjY4OGNkNGQ2Nzk0YTM3OTY2YjZiNGQ0ZTUzODRmMmI7cHNrZXk9.master_playlist.m3u8?t=60815a5e&exper=0&us=7467073381750618698&sign=c666ea93b7f8084a31468106825a30d3");
        System.out.println(m3u8Info);
    }

    public static final ResolveM3u8 getINSTANCE() {
        return INSTANCE;
    }

    public Matcher regex(String regex, String content) {
        return Pattern.compile(regex).matcher(content);
    }

    public boolean lookup(String content, String regx) {
        return StringUtils.isBlank(content) ? false : content.contains(regx);
    }

    /**
     * 截取
     *
     * @param content
     * @param start
     * @param end
     * @return
     */
    private String subStr(String content, String start, String end) {
        return StringUtils.isBlank(content) ? "" : content.substring(content.indexOf(start) + start.length(), StringUtils.isBlank(end) ? content.length() : content.indexOf(end));
    }

    /**
     * 截取
     *
     * @param content
     * @param start
     * @return
     */
    private String subStr(String content, String start) {
        return StringUtils.isBlank(content) ? "" : content.substring(content.indexOf(start) + start.length());
    }

    /**
     * 截取
     *
     * @param content
     * @return
     */
    private String subStr(String content) {
        return StringUtils.isBlank(content) ? "" : content.substring(0);
    }

    /**
     * 解析
     *
     * @param m3u8Url
     * @return
     */
    public M3u8Info resolveByCommon(String m3u8Url) {
        M3u8Info m3u8Info = new M3u8Info();
        String content = null;
        try {
            content = HttpUtils.get(m3u8Url);
            String[] strs = content.split("\n");
            List<SegmentFileInfo> segmentFileInfos = new LinkedList<>();
            String tempMethod = null;
            byte[] tempKey = null;
            String keyUrl = null;
            for (int i = 0; i < strs.length; i++) {
                if (StringUtils.isBlank(keyUrl)) {
                    if (lookup(strs[i], "URI=")) {
                        keyUrl = subStr(strs[i], "URI=").replaceAll("\"", "");
                        m3u8Info.setHasKey(true);
                    }
                }
                //查找m3u8位置
                if (lookup(strs[i], ".m3u8")) {
                    SegmentFileInfo segmentFileInfo = new SegmentFileInfo(null, null, null, subStr(strs[i]), 0);
                    segmentFileInfo.setM3u8(true);
                    try {
                        if (lookup(strs[i - 1], "RESOLUTION=")) {
                            segmentFileInfo.setResolution(subStr(strs[i - 1], "RESOLUTION="));
                        }
                    } catch (Exception e) {
                        log.error("下标越界就不管了");
                    }
                    segmentFileInfos.add(segmentFileInfo);
                }
                if (lookup(strs[i], ".ts") || lookup(strs[i], ".mp4")) {
                    String url = strs[i];
                    String method = null;
                    String iv = null;
                    double time = 0;
                    if (lookup(strs[i - 2], "#EXT-X-KEY:METHOD=")) {
                        method = subStr(strs[i - 2], "#EXT-X-KEY:METHOD=", ",");
                        tempMethod = method;
                    } else {
                        method = tempMethod;
                    }
                    if (lookup(strs[i - 2], "IV=")) {
                        iv = subStr(strs[i - 2], "IV=", "");
                        iv = iv.replace("0x", "");
                        iv = iv.substring(0, 16);
                    }
                    if (lookup(strs[i - 1], "#EXTINF:")) {
                        time = Double.parseDouble(subStr(strs[i - 1], "#EXTINF:", null).replace(",", ""));
                    }
                    segmentFileInfos.add(new SegmentFileInfo(method, iv, null, url, time));
                }
            }
            if (segmentFileInfos.size() > 0) {
                String baseUrl = valueOf(m3u8Url, true);
                if (HttpUtils.isSuccess(repleaceUrl(baseUrl + segmentFileInfos.get(0).getUrl()))) {
                    m3u8Info.setBaseUrl(baseUrl);
                }
                baseUrl = valueOf(m3u8Url, false);
                if (HttpUtils.isSuccess(repleaceUrl(baseUrl + segmentFileInfos.get(0).getUrl()))) {
                    m3u8Info.setBaseUrl(baseUrl);
                }
                if (segmentFileInfos.size() > 1) {
                    segmentFileInfos.removeIf(t -> {
                        if (t.isM3u8()) {
                            String[] str = t.getResolution().split("x");
                            if (str.length == 2) {
                                if (Integer.parseInt(str[0]) <1400 ) {
                                    return true;
                                }
                            }
                        }
                        return false;
                    });
                }
                if (segmentFileInfos.get(0).getUrl().contains(".m3u8")) {
                    return resolveByCommon(repleaceUrl(m3u8Info.getBaseUrl() + segmentFileInfos.get(0).getUrl()));
                }
            }
            try {
                /**
                 * 加密密钥直接采用字节流
                 */
                if (StringUtils.isNotBlank(keyUrl))
                    if (keyUrl.indexOf("http") > -1) {
                        byte[] bytes = HttpUtils.getBytes(keyUrl);
                        tempKey = bytes;
                    } else {
                        tempKey = HttpUtils.getBytes((m3u8Info.getBaseUrl() + keyUrl));
                    }
                if (m3u8Info.isHasKey() && tempKey != null) {
                    byte[] finalTempKey = tempKey;
                    segmentFileInfos.stream().forEach(segmentFileInfo -> segmentFileInfo.setKey(finalTempKey));
                }
            } catch (Exception e) {
                e.printStackTrace();
                log.error("解析异常：{}", e);
            }
            m3u8Info.setSegmentFileInfos(segmentFileInfos);

        } catch (Exception e) {
            e.printStackTrace();
            log.info("解析M3u8出错{}", e.getMessage());
        }
        if (m3u8Info.getSegmentFileInfos() == null) {
            JOptionPane.showMessageDialog(null, "解析失败");
        }
        return m3u8Info;
    }
}
