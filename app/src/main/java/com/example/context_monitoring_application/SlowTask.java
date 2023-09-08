package com.example.context_monitoring_application;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.MediaMetadataRetriever;
import android.os.Looper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Handler;
import java.util.logging.LogRecord;


public class SlowTask
{
    protected static String calculateHeartRate(String... params) throws IOException {
        Bitmap m_bitmap = null;
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        List<Bitmap> frameList = new ArrayList<>();
        try {
            retriever.setDataSource(params[0]);
            String duration = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_FRAME_COUNT);
            int aduration = Integer.parseInt(duration);
            int i = 10;
            while (i < aduration)
            {
                Bitmap bitmap = retriever.getFrameAtIndex(i);
                frameList.add(bitmap);
                i += 5;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            retriever.release();
            long redBucket = 0;
            long pixelCount = 0;
            List<Long> a = new ArrayList<>();
            for (Bitmap bitmap : frameList) {
                redBucket = 0;
                for (int y = 550; y < 650; y++) {
                    for (int x = 550; x < 650; x++) {
                        int c = bitmap.getPixel(x, y);
                        pixelCount++;
                        redBucket += Color.red(c) + Color.blue(c) + Color.green(c);
                    }
                }
                a.add(redBucket);
            }
            List<Long> b = new ArrayList<>();
            for (int i = 0; i < a.size() - 5; i++) {
                long temp = (a.get(i) + a.get(i + 1) + a.get(i + 2) + a.get(i + 3) + a.get(i + 4)) / 4;
                b.add(temp);
            }
            long x = b.get(0);
            int count = 0;
            for (int i = 1; i < b.size() - 1; i++) {
                long p = b.get(i);
                if ((p - x) > 3500) {
                    count = count + 1;
                }
                x = b.get(i);
            }
            int rate = (int) ((count / 45.0) * 60);
            return String.valueOf(rate / 2);
        }
    }



}
