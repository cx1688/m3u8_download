package com.dfm.utils;

import ws.schild.jave.Encoder;
import ws.schild.jave.EncoderException;
import ws.schild.jave.MultimediaObject;
import ws.schild.jave.encode.AudioAttributes;
import ws.schild.jave.encode.EncodingAttributes;
import ws.schild.jave.encode.VideoAttributes;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @program: test-demo
 * @description:
 * @author: Mr.D
 * @create: 2020-12-25 12:30
 */
public class EncodeVideo {
    public static void batchProcess(String path) {
        File file = new File(path);
        List<File> allFile = getAllFile(file);
        allFile.sort(File::compareTo);
        allFile.stream()
                .filter(t -> ".mp4".contains(t.getName().substring(t.getName().lastIndexOf("."))))
                .filter(File::isFile)
                .forEach(t -> {
                    encodeCopy(t, new File(t.getParent() + File.separator + t.getName().substring(0, t.getName().lastIndexOf(".")) + "_output" + t.getName().substring(t.getName().lastIndexOf("."))));
                });
    }

    public static List<File> getAllFile(File file) {
        File[] files = file.listFiles();
        List<File> direcotrys = new ArrayList<>();
        List<File> fileList = new ArrayList<>();
        for (File file1 : files) {
            if (file1.isDirectory()) {
                direcotrys.add(file1);
            } else {
                fileList.add(file1);
            }
        }
        if (direcotrys.size() > 0) {
            for (File direcotry : direcotrys) {
                fileList.addAll(getAllFile(direcotry));
            }
        }
        return fileList;
    }

    /**
     * 流复制处理
     *
     * @param file
     * @param target
     * @return
     */
    public static void encodeCopy(File file, File target) {
        VideoAttributes videoAttributes = new VideoAttributes();
        videoAttributes.setCodec(VideoAttributes.DIRECT_STREAM_COPY);
        AudioAttributes audioAttributes = new AudioAttributes();
        audioAttributes.setCodec(AudioAttributes.DIRECT_STREAM_COPY);
        Encoder encoder = new Encoder();
        EncodingAttributes encodingAttributes = new EncodingAttributes();
        encodingAttributes.setVideoAttributes(videoAttributes);
        encodingAttributes.setAudioAttributes(audioAttributes);
        MultimediaObject multimediaObject = new MultimediaObject(file);
        try {
            encoder.encode(multimediaObject, target, encodingAttributes);
        } catch (EncoderException e) {
            e.printStackTrace();
        } finally {
            file.delete();
        }
    }


}
