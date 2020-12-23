package com.example.myapplication;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;

import androidx.annotation.RequiresApi;

public class FilterThread implements Runnable{
    int start = -1;
    int end = 0;
    int height = -1;
    int[] dest;
    int type;

    //int MASK_ALPHA = Integer.parseInt("FF000000", 16);
    //int MASK_RED = Integer.parseInt("00FF0000", 16);
    //int MASK_GREEN = Integer.parseInt("0000FF00", 16);
    //int MASK_BLUE = Integer.parseInt("000000FF", 16);

    FilterThread(int start, int end, int height, int[] dest, int type){
        this.start = start;
        this.end = end;
        this.dest = dest;
        this.type = type;
        this.height = height;
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    public void run() {
        //int[] source = dest.clone();
        if(type == 1)
            for(int i = start; i < end; ++i)
                for(int j = 0; j < height; ++j){
                    int tmp = dest[i * height + j];
                    dest[i * height + j] = 0xffffff ^ tmp | 0xff << 24 & tmp;
                }
    }
}
