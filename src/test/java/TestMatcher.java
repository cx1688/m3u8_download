import com.dfm.beans.M3u8Info;
import com.dfm.beans.SegmentFileInfo;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @program: m3u8_project
 * @description:
 * @author: Mr.D
 * @create: 2020-12-23 17:06
 */
public class TestMatcher {
    public static void main(String[] args) throws IOException {
/*        byte[] bytes = Files.readAllBytes(new File("D:\\dfm\\spring-source\\m3u8_project\\src\\test\\java\\index.m3u8").toPath());
        String content = new String(bytes, "UTF-8");
        String[] split = content.split("\n");
        List<SegmentFileInfo>segmentFileInfos = new LinkedList<>();
        for (int i = 0; i < split.length; i++) {
            String key = null;
            Matcher regex = regex("(?=[/|http]).*[.ts|mp4]", split[i]);
            if(regex.find()){
                String url = regex.group(0);
                String method = null;
                String iv = null;
                double time = 0;
                Matcher mMethod = regex("(?<=#EXT-X-KEY:METHOD=).*?(?=,IV)", split[i - 2]);
                Matcher ivMatcher = regex("(?<=IV=).*", split[i - 2]);
                Matcher timeMatcher = regex("(?<=#EXTINF:).*?(?=,)", split[i - 1]);
                if(mMethod.find()){
                    method=mMethod.group();
                }
                if(ivMatcher.find()){
                    iv=ivMatcher.group();
                }
                if(timeMatcher.find()){
                    time = Double.parseDouble(timeMatcher.group(0));
                }
                segmentFileInfos.add(new SegmentFileInfo(method, iv, key, url, time));
            }
            System.out.println(segmentFileInfos);
        }*/
//        System.out.println(new File(TestMatcher.class.getClass().getResource("/").getPath()).getParent());
//        System.out.println("index.m3u8".contains("."));
//        歌者:
//        2020-11-01T06:43:01Z和2020-04-16T09:56:55.526Z这样的时间字符串

        System.out.println(LocalDateTime.parse("2020-04-16T09:56:55.526", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));


    }
    static Matcher regex(String regex,String content){
        return Pattern.compile(regex).matcher(content);
    }

}
