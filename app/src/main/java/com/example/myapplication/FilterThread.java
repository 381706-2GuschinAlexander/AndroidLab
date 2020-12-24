package com.example.myapplication;

import android.os.Build;

import androidx.annotation.RequiresApi;

public class FilterThread implements Runnable{
    int start = -1;
    int end = 0;
    int width = -1;
    int[] dest;
    int type;
    int[] source;

    int WHITE_PIXEL = 0xffffffff;
    int BLACK_PIXEL = 0xff000000;

    FilterThread(int start, int end, int width, int[] dest, int type, int[] source){
        this.start = start;
        this.end = end;
        this.dest = dest;
        this.type = type;
        this.width = width;
        this.source = source;
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    public void run() {
        if(type == 1)
            for(int i = start; i < end; ++i) {
                for (int j = 0; j < width - 1; ++j) {
                    int Gx = source[(i + 1) * width + j + 1] - source[i * width + j];
                    int Gy = source[i * width + j + 1] - source[(i + 1) * width + j];

                    dest[i * width + j] = (((int) Math.sqrt(Gx * Gx + Gy * Gy + 1) * 255) / 361 > 70 ? WHITE_PIXEL : BLACK_PIXEL);
                }
            }
        if(type == 2)
            for(int i = start; i < end; ++i)
                for(int j = 1; j < width-1; ++j){
                    int Gx = source[(i + 1) * width + j + 1] + source[(i) * width + j + 1] + source[(i - 1) * width + j + 1]
                            - source[(i + 1) * width + j - 1] -  source[(i) * width + j - 1] - source[(i - 1) * width + j - 1];
                    int Gy = source[(i + 1) * width + j - 1] + source[(i + 1) * width + j] + source[(i + 1) * width + j + 1]
                            - source[(i - 1) * width + j - 1] -  source[(i - 1) * width + j] - source[(i - 1) * width + j +1];

                    dest[i * width + j] = (((int)Math.sqrt(Gx * Gx + Gy * Gy) * 255)/ 2165 > 30 ? WHITE_PIXEL : BLACK_PIXEL);
                }

        if(type == 3)
            for(int i = start; i < end; ++i)
                for(int j = 1; j < width-1; ++j){
                    int Gx = source[(i + 1) * width + j + 1] + 2 * source[(i) * width + j + 1] + source[(i - 1) * width + j + 1]
                            - source[(i + 1) * width + j - 1] -  2 * source[(i) * width + j - 1] - source[(i - 1) * width + j - 1];
                    int Gy = source[(i + 1) * width + j - 1] + 2 * source[(i + 1) * width + j] + source[(i + 1) * width + j + 1]
                            - source[(i - 1) * width + j - 1] -  2 * source[(i - 1) * width + j] - source[(i - 1) * width + j +1];

                    dest[i * width + j] = (((int)Math.sqrt(Gx * Gx + Gy * Gy) * 255)/ 2885 > 26 ? WHITE_PIXEL : BLACK_PIXEL);
                }
    }
}
