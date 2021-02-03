package com.dfm.utils;

import com.dfm.beans.M3u8Info;
import com.dfm.beans.SegmentFileInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

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
        M3u8Info m3u8Info = getINSTANCE().resolveByCommon("http://video2.posh-hotels.com:8091/20200614/wanz-953/index.m3u8");
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

    public String subStr(String content, String start, String end) {
        return StringUtils.isBlank(content) ? "" : content.substring(content.indexOf(start) + start.length(), StringUtils.isBlank(end) ? content.length() : content.indexOf(end));
    }

    public M3u8Info resolveByCommon(String m3u8Url) {
        M3u8Info m3u8Info = new M3u8Info();
        String content = null;
        try {
            content = HttpUtils.get(m3u8Url);
            String[] strs = content.split("\n");
            List<SegmentFileInfo> segmentFileInfos = new LinkedList<>();
            String tempMethod = null, tempKey = null;
            for (int i = 0; i < strs.length; i++) {

                String key = null;
//            Matcher regex = regex("(?=[/|http]).*[.ts|mp4]", split[i]);
                Matcher regex = regex("^(\\S).*(.ts|ts)$", strs[i]);
                Matcher m3u8 = regex("^\\S.*(.m3u8)$", strs[i]);
                Matcher keyRegex = regex("(?<=URI=).*", strs[i]);
                if (keyRegex.find()) {
                    key = keyRegex.group().replace("\"", "");
                    tempKey = HttpUtils.get(valueOf(m3u8Url, false) + key);
                } else {
                    key = tempKey;
                }
                if (m3u8.find()) {
                    segmentFileInfos.add(new SegmentFileInfo(null, null, null, m3u8.group(0), 0));
                }
                if (lookup(strs[i], ".ts") || lookup(strs[i], ".mp4") || lookup(strs[i], "ts")) {
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
                    segmentFileInfos.add(new SegmentFileInfo(method, iv, key, url, time));
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
                if (segmentFileInfos.get(0).getUrl().contains(".m3u8")) {
                    return resolveByCommon(repleaceUrl(m3u8Info.getBaseUrl() + segmentFileInfos.get(0).getUrl()));
                }
            }
            m3u8Info.setSegmentFileInfos(segmentFileInfos);
        } catch (Exception e) {
            log.info("解析M3u8出错{}", e.getMessage());
        }

        return m3u8Info;
    }
}
