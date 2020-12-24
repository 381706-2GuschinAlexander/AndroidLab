package com.example.myapplication;

import android.graphics.Bitmap;
import android.os.Build;

import androidx.annotation.RequiresApi;

public class ThreadManage {
    Bitmap source;
    Bitmap dest;

    private Object key = new Object();

    public int[] ARGB(int color){
        int[] res= {color & 0xff000000 >> 24, color & 0x00ff0000 >> 16, color & 0x0000ff00 >> 8, color & 0x000000ff};
        return res;
    }

    public int I(int color){
        int[] argb = ARGB(color);
        return (argb[1] + argb[2] + argb[3])/3;
    }

    public int grayPixel(int I){
        return 0xff000000 | I << 16 | I << 8 | I;
    }

    ThreadManage(Bitmap source, Bitmap dest){
        this.source = source;
        this.dest = dest;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    void filter(int type) throws InterruptedException {
        int height = source.getHeight();
        int width = source.getWidth();
        int[] intArray = new int[height * width];
        source.getPixels(intArray, 0, width, 0, 0,  width, height);


        int[] sourceArray =  intArray.clone();
        for(int i =0; i < height * width; ++i)
            sourceArray[i] = I(sourceArray[i]);

        int k = 4;
        int start[] = {1, height/k, height/k * 2, height/k * 3};
        int end[] = {height/k, height/k * 2, height/k * 3, height/k * 4 - 1};
        Thread threads[] = new Thread[k];
        for(int i = 0; i < k; ++i)
            threads[i] = new Thread(new FilterThread(start[i],end[i], width, intArray, type, sourceArray));

        for(int i = 0; i < k; ++i)
            threads[i].start();

        for(int i = 0; i < k; ++i)
            threads[i].join();


        dest.setPixels(intArray, 0, width, 0, 0,width,height);
    }
}
