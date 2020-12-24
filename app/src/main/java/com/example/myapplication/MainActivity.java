package com.example.myapplication;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {
    int type = 1;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Guschin Lab");
        setSupportActionBar(toolbar);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add("Roberts");
        menu.add("Previtta");
        menu.add("Sobel");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getTitle().equals("Roberts")) {
            toolbar.setTitle("Roberts");
            type = 1;
        }
        if(item.getTitle().equals("Previtta")){
            toolbar.setTitle("Previtta");
            type = 2;
        }
        if(item.getTitle().equals("Sobel")){
            toolbar.setTitle("Sobel");
            type = 3;
        }

        return super.onOptionsItemSelected(item);
    }
}