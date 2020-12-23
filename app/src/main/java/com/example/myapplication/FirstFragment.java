package com.example.myapplication;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.graphics.Bitmap;
import android.widget.ImageView;
import android.content.Intent;
import android.net.Uri;
import android.graphics.Color;

import androidx.navigation.fragment.NavHostFragment;

public class FirstFragment extends Fragment {
    Integer READ_IMG = 1;
    int request = 0;
    ImageView img;
    Bitmap bitmap;


    @RequiresApi(api = Build.VERSION_CODES.Q)
    private void invert() throws InterruptedException {
        Bitmap mutableBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);
        /*
        for(int i = 0; i < bitmap.getWidth(); ++i)
            for(int j = 0; j < bitmap.getHeight(); ++j){
                Color color = bitmap.getColor(i,j);
                mutableBitmap.setPixel(i,j, Color.argb((float)1.0,1 - color.red(),1- color.green(),1- color.blue()));
            }*/
        new ThreadManage(bitmap, mutableBitmap).filter(1);
        img.setImageBitmap(mutableBitmap);
        bitmap =  null;
        bitmap =  mutableBitmap;
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == READ_IMG){
            Uri selectedImageURI = data.getData();
            img.setImageURI(selectedImageURI);
            BitmapDrawable drawable = (BitmapDrawable) img.getDrawable();
            bitmap = drawable.getBitmap();
        }
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        img = (ImageView)view.findViewById(R.id.imageViewTest);

        view.findViewById(R.id.button_first).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent data = new Intent();
                data.setType("image/*");
                data.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(data, "Select picture"), READ_IMG );
            }
        });

        view.findViewById(R.id.button_second).setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.Q)
            @Override
            public void onClick(View view) {
                try {
                    invert();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}