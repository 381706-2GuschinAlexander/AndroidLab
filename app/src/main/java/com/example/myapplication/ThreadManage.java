package com.example.myapplication;

import android.graphics.Bitmap;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

public class ThreadManage {
    Bitmap source;
    Bitmap dest;

    ThreadManage(Bitmap source, Bitmap dest){
        this.source = source;
        this.dest = dest;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    Bitmap filter(int type) throws InterruptedException {
        int width = source.getWidth();
        int height = source.getHeight();
        //int start[] = {0, width/4, width/4 * 2, width/4 * 3};
        //int end[] = {width/4, width/4 * 2, width/4 * 3, width/4 * 4 + width%4};
        int[] intArray = new int[width * height];
        source.getPixels(intArray, 0, width, 0, 0, width, height);
        int start[] = {0, width/8, width/8 * 2, width/8 * 3, width/8 * 4, width/8 * 5, width/8 * 6, width/8 * 7};
        int end[] = {width/8, width/8 * 2, width/8 * 3, width/8 * 4, width/8 * 5, width/8 * 6, width/8 * 7, width};
        Thread threads[] = new Thread[8];
        for(int i = 0; i < 8; ++i)
            threads[i] = new Thread(new FilterThread(start[i],end[i], height, intArray, type));

        for(int i = 0; i < 8; ++i)
            threads[i].start();

        for(int i = 0; i < 8; ++i)
            threads[i].join();
        dest.setPixels(intArray, 0, width, 0, 0, width, height);
        //dest.copyPixelsFromBuffer(IntBuffer.wrap(intArray));

        return dest;
    }
}
